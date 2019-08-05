package cn.heyanle;

import cn.heyanle.rss.Rss;
import cn.heyanle.utils.ImageUtils;
import com.rometools.rome.io.FeedException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
       // ImgIO.saveImgNetByUrl("http://paper.people.com.cn/rmrb/res/2019-08/03/01/rmrb2019080301p23_b.jpg","E:\\j.jpg");

        Rss.refreshFeedList();

        System.out.println("1");

        Thread.sleep(10000);
        Rss.parseFeed();



    }
}
