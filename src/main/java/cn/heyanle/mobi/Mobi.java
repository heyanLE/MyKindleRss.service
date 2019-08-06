package cn.heyanle.mobi;

import cn.heyanle.database.dao.FeedDao;
import cn.heyanle.database.info.FeedInfo;
import cn.heyanle.mobi.info.*;
import cn.heyanle.push.info.PushInfo;
import cn.heyanle.rss.IO;
import cn.heyanle.rss.Rss;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Mobi {

    final static String OUT_PUT_PATH = "E:\\MyKindleRssPath\\Mobi";

    final static String KINDLE_GEN_PATH = "D:\\KindleGen/kindlegen.exe";

    String basePath = "";
    int t;
    int id;
    String email;

    public static Mobi of(String email,int t,int id){
        Mobi m = new Mobi();
        m.basePath = OUT_PUT_PATH+"/"+email;
        m.t = t;
        m.id = id;
        m.email = email;
        return m;
    }

    public void start(){

        make();

        CopyMaster.run();

        WriteMaster.run();

        pack();
    }

    public void make(){

        List<FeedInfo> list = FeedDao.getFeedsByUserId(id);

        Book book = new Book();

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String s8 = formatter.format(currentTime);

        book.name = "MyKindleRss-"+ s8;
        book.author = "MyKindleRss";
        book.date = currentTime.toString();
        book.email = email;

        int page = 0;

        FeedPage feedPage = new FeedPage();
        feedPage.title = "MyKindleRss";
        feedPage.pageNum = page++;

        book.body.add(feedPage);



        for(FeedInfo info : list){
            int i = info.getId();

            String p = PushInfo.PUSH_PATH+"/"+t+"/"+i+"/Entry.json";
            String json = IO.read(new File(p));

            JSONArray array = JSON.parseArray(json);
            if (array.size() <= 0){
                continue;
            }

            FeedPage feedPagee = new FeedPage();
            feedPagee.title = info.getName();
            feedPagee.pageNum = page++;

            book.addFeed(feedPagee);


        }
        for(FeedInfo info : list){
            int i = info.getId();

            String p = PushInfo.PUSH_PATH+"/"+t+"/"+i+"/Entry.json";
            String json = IO.read(new File(p));

            JSONArray array = JSON.parseArray(json);
            if (array.size() <= 0){
                continue;
            }

            FeedPage nnn = null;

            for (FeedPage page2 : book.body ){
                if (page2.title.equals(info.getName())){

                    nnn = page2;

                }
            }
            for(int iiii = 0 ; iiii<array.size() ; iiii ++){

                JSONObject object = array.getJSONObject(iiii);

                String ss = "<p class=\"calibre_\"><span class=\"calibre1\"><span class=\"bold\"><!----title----></span></span></p>";

                String link = "</p><p class=\"calibre_\"> <a href = '<!----link---->'>点击使用Kindle浏览器查看原文</a></p><div class=\"mbp_pagebreak\" id=\"calibre_pb_12\"></div>";

                EntryPage page1 = new EntryPage();
                page1.pageNum = page++;
                page1.title = object.getString("Headline");
                page1.body = ss.replace("<!----title---->",page1.title) + object.getString("Content") + link.replace("<!----link---->",object.getString("Link"));

                if (object.getIntValue("ImgStart") != -1){

                    for (int ssss = object.getIntValue("ImgStart");ssss <= object.getIntValue("ImgEnd") ; ssss ++){

                        if (new File( PushInfo.PUSH_PATH+"/"+t+"/"+info.getId()+"/img/"+info.getId()+"_"+ssss+".jpg").exists()){
                            page1.imgPaths.add(info.getId()+"_"+ssss+".jpg");

                            FileCopy copy = new FileCopy();
                            copy.srcPath = PushInfo.PUSH_PATH+"/"+t+"/"+info.getId()+"/img/"+info.getId()+"_"+ssss+".jpg";
                            copy.destPath = basePath+"/img/"+info.getId()+"_"+ssss+".jpg";
                            CopyMaster.add(copy);

                        }

                        if (new File( PushInfo.PUSH_PATH+"/"+t+"/"+info.getId()+"/img/"+info.getId()+"_"+ssss+".png").exists()){
                            page1.imgPaths.add(info.getId()+"_"+ssss+".png");

                            FileCopy copy = new FileCopy();
                            copy.srcPath = PushInfo.PUSH_PATH+"/"+t+"/"+info.getId()+"/img/"+info.getId()+"_"+ssss+".png";
                            copy.destPath = basePath+"/img/"+info.getId()+"_"+ssss+".png";
                            CopyMaster.add(copy);
                        }


                    }

                }
                if (nnn != null){
                    nnn.addEntry(page1);
                }

            }

        }


        for(FeedPage feed : book.body){

            String feedDemo = " <li value=\"<!----value---->\" class=\"calibre_3\"><a href=\"<!----href---->\"><!----text----></a></li>";
            String feedText = IO.read(new File(getClass().getResource("/mobi/toc.html").getPath()))
                    .replace("<!----title---->",book.name).replace("<!----text---->",feed.title+" ("+feed.body.size()+")");

            String li = "";
            int iii = 1;
            for (EntryPage entryPage: feed.body){

                li += feedDemo.replace("<!----value---->",(iii++) +"")
                        .replace("<!----href---->","index_"+entryPage.pageNum+".html")
                        .replace("<!----text---->",entryPage.title);

                String pageText = IO.read(new File(getClass().getResource("/mobi/page.html").getPath()))
                        .replace("<!----title---->",book.name).replace("<!----body---->",entryPage.body);

                WriteInfo writeInfo = new WriteInfo();
                writeInfo.destPath = basePath+"/"+"index_"+entryPage.pageNum+".html";
                writeInfo.info = pageText;

                WriteMaster.add(writeInfo);

            }

            feedText = feedText.replace("<!----li---->",li);
            WriteInfo writeInfo = new WriteInfo();
            writeInfo.destPath = basePath+"/"+"index_"+feed.pageNum+".html";
            writeInfo.info = feedText;

            WriteMaster.add(writeInfo);


        }


        Opf o = Opf.of(book);
        o.parse();
        IO.write(new File(basePath+"/content.opf"),o.toString());

        Toc t = Toc.of(book);
        t.parse();
        IO.write(new File(basePath+"/nav-contents.ncx"),t.toString());

        Table table = Table.of(book);
        table.parse();
        IO.write(new File(basePath+"/index_-1.html"),table.toString());

        FileCopy copy = new FileCopy();
        copy.srcPath = o.getClass().getResource("/mobi/stylesheet.css").getPath();
        copy.destPath = basePath+"/stylesheet.css";
        CopyMaster.add(copy);

        FileCopy copy2 = new FileCopy();
        copy2.srcPath = o.getClass().getResource("/mobi/page_styles.css").getPath();
        copy2.destPath = basePath+"/page_styles.css";
        CopyMaster.add(copy2);

        FileCopy copy3 = new FileCopy();
        copy3.srcPath = o.getClass().getResource("/mobi/img/cover.jpg").getPath();
        copy3.destPath = basePath+"/img/cover.jpg";
        CopyMaster.add(copy3);


    }

    public void pack(){
        File f = new File(OUT_PUT_PATH+"/"+email+"/content.opf");
        if (f.exists()){
            exeCmd(KINDLE_GEN_PATH+" "+OUT_PUT_PATH+"/"+email+"/content.opf");

        }

    }

    public static void exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
