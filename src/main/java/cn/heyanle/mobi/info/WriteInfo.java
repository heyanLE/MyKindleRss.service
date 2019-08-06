package cn.heyanle.mobi.info;

import cn.heyanle.rss.IO;

import java.io.File;

public class WriteInfo {

    public String destPath;
    public String info;

    public void write(){
        IO.write(new File(destPath),info);
    }

}
