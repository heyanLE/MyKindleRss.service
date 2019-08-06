package cn.heyanle.mobi.info;

import cn.heyanle.mobi.info.FeedPage;
import cn.heyanle.rss.IO;

import java.io.File;

public class Toc {

    String text = "";

    String ArticleDemo = "<navPoint class=\"article\" id=\"<!-----id--->\" playOrder=\"<!-----playOrder--->\">\n" +
            "      <navLabel>\n" +
            "        <text><!---text----></text>\n" +
            "      </navLabel>\n" +
            "      <content src=\"<!-----src--->\"/>\n" +
            "    </navPoint>";

    String SessionDemo = "<navPoint class=\"section\" id=\"<!-----id--->\" playOrder=\"<!-----playOrder--->\">\n" +
            "      <navLabel>\n" +
            "        <text><!---text----></text>\n" +
            "      </navLabel>\n" +
            "      <content src=\"<!-----src--->\"/>\n" +
            "               <!-----article--->" +
            "    </navPoint>";

    Book book ;

    String nav = "";


    public void parse(){

        int i = 1;

        for (FeedPage page :book.body){

            String t = SessionDemo.replace("<!-----id--->","num_"+i)
                    .replace("<!-----playOrder--->",i+"")
                    .replace("<!---text---->",page.title+" ("+page.body.size()+")");

            if (page.body.size() > 0){
                t = t.replace("<!-----src--->","index_"+page.body.get(0).pageNum+".html");
            }else{
                t = t.replace("<!-----src--->","index_"+page.pageNum+".html");
            }



            i++;
            String l = "";

            for (EntryPage entryPage : page.body){
                l += ArticleDemo.replace("<!-----id--->","num_"+i)
                        .replace("<!-----playOrder--->",i+"")
                        .replace("<!---text---->",entryPage.title)
                        .replace("<!-----src--->","index_"+entryPage.pageNum+".html");

                i++;
            }

            t = t.replace("<!-----article--->",l);

            nav+= t;


        }

        text = text.replace("<!----navMap---->",nav).replace("<!----title---->",book.name);

    }

    public String toString(){
        return text;
    }

    public static Toc of(Book book){
        Toc t = new Toc();
        String p = t.getClass().getResource("/mobi/nav-contents.ncx").getPath();
        t.text = IO.read(new File(p));
        t.book = book;
        return t;
    }

}
