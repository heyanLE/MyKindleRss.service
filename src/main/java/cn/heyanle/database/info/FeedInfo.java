package cn.heyanle.database.info;

public class FeedInfo {

    private         int         id;
    private         String      name;
    private         String      describe;
    private         String      value;
    private         int         type;
    private         String      from;
    private         String      classN;

    public FeedInfo setId(int id) {
        this.id = id;
        return this;
    }

    public FeedInfo setName(String name) {
        this.name = name;
        return this;
    }

    public FeedInfo setDescribe(String describe) {
        this.describe = describe;
        return this;
    }

    public FeedInfo setValue(String value) {
        this.value = value;
        return this;
    }

    public FeedInfo setType(int type) {
        this.type = type;
        return this;
    }

    public FeedInfo setFrom(String from) {
        this.from = from;
        return this;
    }

    public FeedInfo setClassN(String classN) {
        this.classN = classN;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public String getClassN() {
        return classN;
    }

    public static FeedInfo newEmpty(){
        return new FeedInfo();
    }


}
