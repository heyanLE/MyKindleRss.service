package cn.heyanle.job;

import cn.heyanle.rss.Rss;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FeedListJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Rss.runByPool(new Runnable() {
            @Override
            public void run() {
                Rss.refreshFeedList();
            }
        });
    }

}
