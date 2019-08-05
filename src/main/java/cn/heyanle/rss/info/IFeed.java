package cn.heyanle.rss.info;

import cn.heyanle.database.info.FeedInfo;
import cn.heyanle.rss.IO;
import cn.heyanle.rss.ImgLoadMaster;
import cn.heyanle.rss.Rss;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class IFeed {

    public static final String RSS_PATH = "E:\\MyKindleRssPath/Entry";

    private String url ;
    private int id ;

    private String basePath;

    private List<IEntry> entries = new ArrayList<>();

    /**
     * 从Url加载IEntry
     */
    public void parse() throws Exception{

        System.out.println("Feed开始加载：" + url);

        URL url = new URL(this.url);
        XmlReader reader = new XmlReader(url);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(reader);

        List<SyndEntry> entries = feed.getEntries();

        JSONArray array = new JSONArray();

        int cout = 0;

        int imgId = 0;

        for(SyndEntry entry : entries){

            cout ++;

            if (cout > 16){
                break;
            }

            JSONObject object = new JSONObject();

            IEntry i = IEntry.empty().title(entry.getTitle()).link(entry.getLink());

            if (entry.getPublishedDate() != null){
                i.pubDate(entry.getPublishedDate().toString());
            }else if(entry.getUpdatedDate() != null){
                i.pubDate(entry.getUpdatedDate().toString());
            }else{
                i.pubDate(new Date().toString());
            }

            List<SyndContent> contents = entry.getContents();
            String content;
            if (contents.size() == 0){
                content = entry.getDescription().getValue();
            }else{
                content = contents.get(0).getValue();
            }

            Document doc = Jsoup.parse(content);
            Elements es =  doc.select("img");

            object.put("ImgStart",imgId);

            for(Element e : es){

                String realSrc = e.attr("src");
                String src;

                if (! realSrc.endsWith(".jpg") && ! realSrc.endsWith(".png")
                && ! realSrc.endsWith(".JPG") && ! realSrc.endsWith(".PNG")){
                    break;
                }

                if (realSrc.indexOf("http") == 0){
                    src = realSrc;
                }else{

                    if (realSrc.startsWith("/")){// ex： /img/ijfailefja.png
                        String[] ss = this.url.split("/");
                        src = ss[0] + "/" + ss[1] + "/" + ss[2] + realSrc;
                    }else{
                        src = this.url + realSrc;
                    }

                }

                content = content.replace(realSrc,"img/"+imgId+".jpg");

                IImgLoader iImgLoader = new IImgLoader(src,basePath + "/img/"+imgId+((realSrc.endsWith(".png") || realSrc.endsWith(".PNG"))?".png":".jpg"));

                ImgLoadMaster.add(iImgLoader);

                imgId++;

            }

            i.content(content);

            object.put("Headline",i.title);
            object.put("Link",i.link);
            object.put("Content",content);
            object.put("ImgEnd",id);

            array.add(object);

            this.entries.add(i);
        }

        String j = array.toJSONString();
        IO.write(new File(basePath+"/Entry.json"),j);

    }

    public static IFeed of (FeedInfo info){
        IFeed i = new IFeed();
        i.url = info.getValue();
        i.id = info.getId();
        i.basePath = RSS_PATH + "/" + i.id;
        return i;
    }

}
