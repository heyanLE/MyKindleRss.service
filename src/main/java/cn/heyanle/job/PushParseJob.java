package cn.heyanle.job;

import cn.heyanle.Main;
import cn.heyanle.database.dao.UserDao;
import cn.heyanle.database.info.UserInfo;
import cn.heyanle.mobi.Mobi;
import cn.heyanle.push.Push;
import cn.heyanle.rss.Rss;
import org.quartz.*;

import java.util.Calendar;
import java.util.Date;

public class PushParseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {



        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTime(jobExecutionContext.getFireTime());
        int i = calendar.get(Calendar.HOUR_OF_DAY)+1;

        System.out.println("自动任务：PushParse "+i);
        Rss.runByPool(new Runnable() {
            @Override
            public void run() {

                Push.push(i);
                for (UserInfo info : UserDao.getEnableUserByPushTime(i) ){

                    System.out.println("准备用户推送资源 ： "+info.getEmail());



                    Mobi.of(info.getEmail(),info.getPushTime(),info.getId()).start();

                }

                System.out.println("自动任务：PushParse 完成");
            }
        });

    }

}
