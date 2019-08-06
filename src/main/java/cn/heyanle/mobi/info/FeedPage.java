package cn.heyanle.mobi.info;

import java.util.ArrayList;
import java.util.List;

public class FeedPage {

    public int pageNum;

    public String title;

    public int num;

    public List<EntryPage> body = new ArrayList<>();

    public void addEntry(EntryPage page){
        body.add(page);
    }

}
