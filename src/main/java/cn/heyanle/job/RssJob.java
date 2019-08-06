package cn.heyanle.job;

import cn.heyanle.rss.Rss;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RssJob  implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("自动任务：Rss Parse");

        Rss.runByPool(new Runnable() {
            @Override
            public void run() {
                Rss.parseFeed();
            }
        });


    }
}
