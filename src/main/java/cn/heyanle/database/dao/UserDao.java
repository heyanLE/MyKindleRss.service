package cn.heyanle.database.dao;

import cn.heyanle.database.DBHandlerQuery;
import cn.heyanle.database.DBHandlerUpdate;
import cn.heyanle.database.info.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public static List<UserInfo> getEnableUserByPushTime(int pushTime){

        List<UserInfo> infos = new ArrayList<>();

        DBHandlerQuery.newHandler().select("*").from("user").where("push_auto=1").and("balance>0")
                .and("push_time="+pushTime).sql(new DBHandlerQuery.ResultAble() {
            @Override
            public void onResult(ResultSet rs) throws SQLException{
                while (rs.next()){
                    infos.add(getUserByResultSet(rs));
                }
            }
        });

        return infos;

    }

    public static void UpdateUserBalanceById(int id,int balance){
        DBHandlerQuery query = DBHandlerQuery.newHandler().where("id="+id);
        DBHandlerUpdate.update("user", "balance", balance+"", query, null);
    }

    public static UserInfo getUserByResultSet(ResultSet rs) throws SQLException{
        return UserInfo.newEmpty()
                .setId(rs.getInt("id"))
                .setPasswordHash(rs.getString("passwordHash"))
                .setEmail(rs.getString("email"))
                .setAimEmail(rs.getString("aim_email"))
                .setPushTime(rs.getInt("push_time"))
                .setPushAuto(rs.getInt("push_auto") == 1)
                .setBalance(rs.getInt("balance"))
                .setCreateTime(rs.getTimestamp("create_time").toString())
                .setPushEmail(rs.getString("push_email"));
    }

}
