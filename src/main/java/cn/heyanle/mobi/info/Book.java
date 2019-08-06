package cn.heyanle.mobi.info;

import cn.heyanle.push.info.ImgCopyInfo;

import java.util.ArrayList;
import java.util.List;

public class Book {

    public String name;

    public String author;

    public String email;

    public String date;

    public List<FeedPage> body = new ArrayList<>();

    public void addFeed(FeedPage feedPage){
        body.add(feedPage);
    }

}
