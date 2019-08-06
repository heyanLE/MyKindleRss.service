package cn.heyanle.mobi;

import cn.heyanle.mobi.info.WriteInfo;

import java.util.ArrayList;
import java.util.List;

public class WriteMaster {

    private static List<WriteInfo> list = new ArrayList<>();

    public static void add(WriteInfo info){
        list.add(info);
    }

    public static void remove(WriteInfo info){
        list.remove(info);
    }

    public static void clear(){
        list.clear();
    }

    public static void run(){

        for(WriteInfo info:list){
            info.write();
        }

        list.clear();

    }

}
