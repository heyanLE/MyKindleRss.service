package cn.heyanle.database.info;

public class UserInfo {

    private         int         id;
    private         String      email;
    private         String      passwordHash;
    private         String      aimEmail;
    private         boolean     pushAuto;
    private         String      pushEmail;
    private         int         balance;
    private         String      createTime;
    private         int         pushTime;

    public UserInfo setId(int id) {
        this.id = id;
        return this;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserInfo setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserInfo setAimEmail(String aimEmail) {
        this.aimEmail = aimEmail;
        return this;
    }

    public UserInfo setPushAuto(boolean pushAuto) {
        this.pushAuto = pushAuto;
        return this;
    }

    public UserInfo setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public UserInfo setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public UserInfo setPushTime(int pushTime) {
        this.pushTime = pushTime;
        return this;
    }

    public UserInfo setPushEmail(String pushEmail){
        this.pushEmail = pushEmail;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getAimEmail() {
        return aimEmail;
    }

    public boolean isPushAuto() {
        return pushAuto;
    }

    public String getPushEmail() {
        return pushEmail;
    }

    public int getBalance() {
        return balance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getPushTime() {
        return pushTime;
    }

    public static UserInfo newEmpty(){
        return new UserInfo();
    }

}
