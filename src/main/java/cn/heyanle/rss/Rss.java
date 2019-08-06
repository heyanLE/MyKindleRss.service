package cn.heyanle.rss;

import cn.heyanle.database.dao.FeedDao;
import cn.heyanle.database.info.FeedInfo;
import cn.heyanle.rss.info.IFeed;

import java.io.File;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Rss {

    public volatile static List<IFeed> list = new ArrayList<>();

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void runByPool(Runnable runnable){
        cachedThreadPool.execute(runnable);
    }


    public static void refreshFeedList(){

        System.out.println("FeedList刷新");

        list.clear();
        for (FeedInfo info : FeedDao.getAllFeeds()){
            list.add(IFeed.of(info));
        }
    }

    public static void parseFeed(){

        System.out.println("Feed加载开始");

        delAllFile(IFeed.RSS_PATH);
        File f = new File(IFeed.RSS_PATH);
        f.mkdirs();



            for (IFeed feed : list){
                try {
                    feed.parse();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Rss.parseImg();
    }

    public static void parseImg(){

        System.out.println("图片加载开始");
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ImgLoadMaster.load();
                System.out.println("图片加载完成");
                System.out.println("所有Feed刷新完成");
            }
        });
    }


    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;

        if (tempList == null){
            return false;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
