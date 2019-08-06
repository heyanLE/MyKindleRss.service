package cn.heyanle.rss.info;

import cn.heyanle.utils.ImageUtils;

import java.io.File;

public class IImgLoader {

    String url;
    final String destPath;

    public void load(){


        File f = new File(destPath);
        f.getParentFile().mkdirs();

        if (f.exists()){
            f.delete();
        }

        ImageUtils.imgDownloadNet(url,destPath);
        if (! new File(destPath).exists()){
            ImageUtils.imgDownloadNet(url,destPath);
            if (! new File(destPath).exists()) {
                ImageUtils.imgDownloadNet(url,destPath);
            }
        }



        if (destPath.endsWith(".png")){
            ImageUtils.imgKindleAdaption(destPath,destPath,"png");
        }else{
            ImageUtils.imgKindleAdaption(destPath,destPath,"jpg");
        }


    }

    public IImgLoader(String url,String destPath){
        this.url = url;
        this.destPath = destPath;
    }

}
