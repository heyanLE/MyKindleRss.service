package cn.heyanle.mobi.info;

import cn.heyanle.rss.IO;

import java.io.File;

public class Table {

    String demo = "<li value=\"<!----value---->\" class=\"calibre_3\"><a href=\"<!----href---->\"><!----text----></a></li> ";

    String text = "";

    Book book;

    String li = "";

    public void parse(){

        int i = 1;

        for (FeedPage page :book.body){

            String t = demo.replace("<!----value---->",""+i)
                    .replace("<!----href---->","index_"+page.pageNum+".html")
                    .replace("<!----text---->",page.title+" ("+page.body.size()+")");

            li = li + t;

            i++;
        }

        text = text.replace("<!----TableContent---->",li).replace("<!----title---->",book.name);


    }

    public String toString(){
        return text;
    }

    public static Table of(Book book){
        Table t = new Table();
        String p = t.getClass().getResource("/mobi/index_-1.html").getPath();
        t.text = IO.read(new File(p));
        t.book = book;
        return t;
    }

}
