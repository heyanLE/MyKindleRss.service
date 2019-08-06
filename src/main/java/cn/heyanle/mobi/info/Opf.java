package cn.heyanle.mobi.info;

import cn.heyanle.rss.IO;

import javax.xml.crypto.Data;
import java.io.File;

public class Opf {

    public String title = "";
    String date = "";

    String text;

    String mainDemo = "<item href=\"<!----path---->\" id=\"<!----id---->\" media-type=\"<!----type---->\"/>";
    String spinDemo = "<itemref idref=\"<!----idref---->\"/>";

    String main = "<item href=\"index_-1.html\" id=\"page0\" media-type=\"application/xhtml+xml\"/>";
    String spin = "<itemref idref=\"page0\"/>";

    Book book;

    public void parse(){

        int id = 0;

        int imageI = 0;

        for(FeedPage page : book.body){

            main += mainDemo.replace("<!----path---->","index_"+page.pageNum+".html")
                    .replace("<!----id---->","page"+(++id))
                    .replace("<!----type---->","application/xhtml+xml");

            spin += spinDemo.replace("<!----idref---->","page"+(page.pageNum+1));


        }

        for(FeedPage page : book.body){
            for (EntryPage entryPage:page.body){
                main += mainDemo.replace("<!----path---->","index_"+entryPage.pageNum+".html")
                        .replace("<!----id---->","page"+(++id))
                        .replace("<!----type---->","application/xhtml+xml");

                spin += spinDemo.replace("<!----idref---->","page"+(entryPage.pageNum+1));

                for (String path:entryPage.imgPaths){

                    String t = "image/jpeg";
                    if (path.endsWith(".png")){
                        t = "image/png";
                    }

                    main += mainDemo.replace("<!----path---->","img/"+path)
                            .replace("<!----id---->","image"+(++imageI))
                            .replace("<!----type---->",t);

                }
            }
        }

        text = text.replace("<!----Title---->",title)
                .replace("<!----Date---->",book.date)
                .replace("<!----Book ID---->",book.email+System.currentTimeMillis())
                .replace("<!----Manifest---->",main)
                .replace("<!----Spine---->",spin);

    }

    public String toString(){
        return text;
    }

    public static Opf of(Book book){
        Opf o = new Opf();
        o.book = book;
        o.title = book.name;
        o.date = book.date;
        String p = o.getClass().getResource("/mobi/content.opf").getPath();
        o.text = IO.read(new File(p));
        return o;
    }




}
