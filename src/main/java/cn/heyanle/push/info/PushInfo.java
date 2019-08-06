package cn.heyanle.push.info;

import cn.heyanle.push.ImgCopyMaster;
import cn.heyanle.rss.IO;
import cn.heyanle.rss.Rss;
import cn.heyanle.rss.info.IFeed;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;

import java.io.File;

public class PushInfo {

    public static final String PUSH_PATH  = "E:\\MyKindleRssPath/Push";
    String basePath = "";



    public void parse(){

        for (IFeed feed:Rss.list){

            JSONArray arrayR = new JSONArray();

            int i = feed.getId();

            String baseFeedPath = basePath+"/"+i;
            String latestTitle = "";

            File lFile = new File(baseFeedPath + "/LatestTitle.txt");
            if (lFile.exists() && lFile.isFile()){
                latestTitle = IO.read(lFile);
            }

            File feedFile = new File(IFeed.RSS_PATH+"/"+i+"/Entry.json");
            if (feedFile.exists() && feedFile.isFile()){

                String json = IO.read(feedFile);
                JSONArray array = JSON.parseArray(json);

                for(int ii = 0 ; ii < array.size() ; ii ++){
                    try {

                        JSONObject object = array.getJSONObject(ii);
                        if (!object.getString("Headline").equals(latestTitle)){
                            arrayR.add(object);

                            int star = object.getIntValue("ImgStart");
                            int end = object.getIntValue("ImgEnd");

                            for (int iii = star ; iii <= end ; iii ++){

                                if (star == -1) {
                                    break;
                                }

                                String srcPath = IFeed.RSS_PATH+"/"+i+"/img/"+i+"_"+iii;
                                String imgName = "";
                                if (new File(srcPath+".jpg").exists()){
                                    srcPath += ".jpg";
                                    imgName = i+"_"+iii+".jpg";
                                }else{
                                    srcPath += ".png";
                                    imgName = i+"_"+iii+".png";
                                }

                                ImgCopyMaster.add(ImgCopyInfo.of(srcPath,baseFeedPath+"/img/"+imgName));

                            }

                        }else{
                            System.out.println("eq:"+latestTitle);
                            break;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                IO.write(new File(baseFeedPath+"/Entry.json"),arrayR.toJSONString());


                if (arrayR.size()>0){
                    latestTitle =  arrayR.getJSONObject(0).getString("Headline");
                }
                IO.write(lFile,latestTitle);


            }else{
                continue;
            }

        }


    }


    public static PushInfo of(int t){
        PushInfo info = new PushInfo();
        info.basePath = PUSH_PATH+"/"+t;
        return info;
    }

}
