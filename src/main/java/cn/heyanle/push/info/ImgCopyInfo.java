package cn.heyanle.push.info;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImgCopyInfo {

    String srcPath;
    String destPath;

    public void copy() {
        try {
            if (!new File(srcPath).exists()){
                return;
            }
            new File(destPath).getParentFile().mkdirs();

            if (new File(destPath).exists()){
                new File(destPath).delete();
            }
            System.out.println("图片复制：" + destPath);
            Files.copy((new File(srcPath)).toPath(), (new File(destPath)).toPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ImgCopyInfo of(String srcPath,String destPath){
        ImgCopyInfo imgCopyInfo = new ImgCopyInfo();
        imgCopyInfo.srcPath = srcPath;
        imgCopyInfo.destPath = destPath;
        return imgCopyInfo;
    }

}
