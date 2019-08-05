package cn.heyanle.rss;

import cn.heyanle.rss.info.IImgLoader;

import java.util.ArrayList;
import java.util.List;

public class ImgLoadMaster {

    private static List<IImgLoader> loaders = new ArrayList<>();

    public static void add(IImgLoader loader){
        loaders.add(loader);
    }

    public static void remove(IImgLoader loader){
        loaders.remove(loader);
    }

    public static void clear(){
        loaders.clear();
    }

    public static void load(){
        for (IImgLoader loader : loaders){
            loader.load();
        }
        loaders.clear();
    }

}
