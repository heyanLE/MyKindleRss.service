package cn.heyanle;

import cn.heyanle.job.FeedListJob;
import cn.heyanle.job.PushParseJob;
import cn.heyanle.job.RssJob;
import cn.heyanle.push.Push;
import cn.heyanle.rss.Rss;
import org.quartz.*;

import java.util.Calendar;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here
        // ImgIO.saveImgNetByUrl("http://paper.people.com.cn/rmrb/res/2019-08/03/01/rmrb2019080301p23_b.jpg","E:\\j.jpg");
/*
        Rss.refreshFeedList();

        System.out.println(1000);

        Push.push(getHou());

        System.out.println(1000);



        Mobi.of("1371735400@qq.com",10,1).start();
*/


        Rss.runByPool(new Runnable() {
            @Override
            public void run() {
                Rss.refreshFeedList();
                System.out.println(1000);
                Rss.parseFeed();
            }
        });

        System.out.println(2000);

        JobDetail jobFeedList = newJob(FeedListJob.class)
                .withIdentity("jobFeedList", "group1")
                .build();

        JobDetail jobRssGet = newJob(RssJob.class)
                .withIdentity("jobRssGet", "group1")
                .build();

        JobDetail jobPushParse = newJob(PushParseJob.class)
                .withIdentity("jobPushParse", "group1")
                .build();

        int t = getHou();

        //每一小时秒执行一次 xx:45
        Trigger triggerPushParse = newTrigger()
                .withIdentity("hour", "group1")
                .withSchedule(cronSchedule("0 45 "+(t+1)+"/1 * * ? *"))
                .forJob(jobPushParse)
                .build();

        int tt;
        if (t == 0){
            tt = 1;
        }else if(t < 5){
            tt = 5;
        }else if(t < 9){
            tt = 9;
        }else if(t < 13){
            tt = 13;
        }else if(t < 17){
            tt = 17;
        }else if(t < 21){
            tt = 21;
        }else{
            tt = 1;
        }

        System.out.println("t : "+t+" | tt: "+tt);

        //每四小时秒执行一次 xx:15
        Trigger triggerRssGet = newTrigger()
                .withIdentity("hourF", "group1")
                .withSchedule(cronSchedule("0 15 "+tt+"/4 * * ? *"))
                .forJob(jobRssGet)
                .build();



        Trigger triggerFeedList = newTrigger()
                .withIdentity("day", "group1")
                .withSchedule(cronSchedule("0 0 0 * * ? *"))
                .forJob(jobFeedList)
                .build();






        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();

        sched.start();

        sched.scheduleJob(jobFeedList,triggerFeedList);
        sched.scheduleJob(jobRssGet,triggerRssGet);
        sched.scheduleJob(jobPushParse,triggerPushParse);




    }

    public static int getHou(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
