package cn.heyanle.mobi;

import cn.heyanle.mobi.info.FileCopy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CopyMaster {

    private static List<FileCopy> list = new ArrayList<>();

    public static void add(FileCopy copy){
        list.add(copy);
    }

    public static void remove(FileCopy copy){
        list.remove(copy);
    }

    public static void clear(){
        list.clear();
    }

    public static void run(){

        for(FileCopy copy:list){
            copy.run();
        }

        list.clear();

    }


}
