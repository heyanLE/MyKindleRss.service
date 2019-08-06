package cn.heyanle.push;

import cn.heyanle.push.info.PushInfo;
import cn.heyanle.rss.Rss;

public class Push {

    public static void push(final int t){

        System.out.println("Push 资源开始加载 时间："+t);

        PushInfo.of(t).parse();

        System.out.println("Push 文章资源加成完毕 ，开始复制图片");

        imgLoad();

    }

    public static void imgLoad(){
        ImgCopyMaster.load();

        System.out.println("图片复制完毕");
        System.out.println("Push资源加载完毕");
    }

}
