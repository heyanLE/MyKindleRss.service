package cn.heyanle.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class ImageUtils {


    /**
     * 在网上下载图片
     * @param urlS          连接
     * @param destPath      目标路径
     */
    public static void imgDownloadNet(String urlS,String destPath){
        System.out.println("图片下载："+urlS);
        File destFile = new File(destPath);
        InputStream is = null;
        OutputStream os = null;
        try{
            URL url = new URL(urlS);
            URLConnection con = url.openConnection();
            is = con.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            os = new FileOutputStream(destFile);
            while ((len = is.read(bs)) != -1){
                os.write(bs,0,len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null ){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (os != null ){
                try {
                    os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将图片进行Kindle压制 ： 灰度  0.2f压制
     * @param srcImagePath      源文件路径
     * @param destImagePath     目标路径
     */
    public static void imgKindleAdaption(String srcImagePath , String destImagePath ,String FormatName){
        try{
            System.out.println("图片压制："+srcImagePath);
            File srcImageFile = new File(srcImagePath);
            File destImageFile = new File(destImagePath);
            BufferedImage src = ImageIO.read(srcImageFile);
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(FormatName).next();
            ImageWriteParam imgWriteParams = new JPEGImageWriteParam(Locale.getDefault());
            imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imgWriteParams.setCompressionQuality(0.3f);
            imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
            FileOutputStream out = new FileOutputStream(destImageFile);
            imageWriter.reset();
            imageWriter.setOutput(ImageIO.createImageOutputStream(out));
            imageWriter.write(null, new IIOImage(src, null, null), imgWriteParams);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
