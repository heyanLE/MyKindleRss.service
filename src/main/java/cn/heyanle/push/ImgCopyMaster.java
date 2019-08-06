package cn.heyanle.push;

import cn.heyanle.push.info.ImgCopyInfo;

import java.util.ArrayList;
import java.util.List;

public class ImgCopyMaster {

    private static List<ImgCopyInfo> infoList = new ArrayList<>();

    public static void add(ImgCopyInfo info){
        infoList.add(info);
    }

    public static void remove(ImgCopyInfo info){
        infoList.remove(info);
    }

    public static void clear(){
        infoList.clear();
    }

    public static void load(){
        for (ImgCopyInfo info : infoList){
            info.copy();
        }
        clear();
    }
}
