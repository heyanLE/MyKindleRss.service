package cn.heyanle.rss.info;

public class IEntry {

    String title;
    String content;
    String pubDate;
    String link;

    public IEntry title(String title){
        this.title = title;
        return this;
    }

    public IEntry content(String content){
        this.content = content;
        return this;
    }

    public IEntry pubDate(String pubDate){
        this.pubDate = pubDate;
        return this;
    }

    public IEntry link(String link){
        this.link = link;
        return this;
    }

    public static IEntry empty(){
        return new IEntry();
    }


}
