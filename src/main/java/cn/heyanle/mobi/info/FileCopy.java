package cn.heyanle.mobi.info;

import java.io.File;
import java.nio.file.Files;

public class FileCopy {

    public String srcPath;
    public String destPath;

    public void run(){

        try {
            if (!new File(srcPath).exists()){
                return;
            }
            new File(destPath).getParentFile().mkdirs();

            if (new File(destPath).exists()){
                new File(destPath).delete();
            }
            System.out.println("文件复制：" + destPath);
            Files.copy((new File(srcPath)).toPath(), (new File(destPath)).toPath());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
