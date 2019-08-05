package cn.heyanle.database;

import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class DBHandlerUpdate {

    public interface CallBack{
        void onResult(int i);
    }

    public static void insert (String table,String key,String value,CallBack callBack){
        String sql = "INSERT INTO "+table+"("+key+") "+"value("+value+")";
        sql(sql,callBack);
    };

    public static void update (String table, String key, String value, DBHandlerQuery query, CallBack callBack){
        String sql = "UPDATE "+table+" SET "+key+"="+value+query.getSql();
        sql(sql,callBack);
    }

    public static void sql(String sql, CallBack callBack){

        DruidPooledConnection conn = null;
        Statement statement = null;

        try {

            conn = DBConnection.getInstance().getConnection();
            statement = conn.createStatement();
            int i = statement.executeUpdate(sql);
            if (callBack != null) {
                callBack.onResult(i);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBHandlerQuery.close(conn, statement);
        }

    }

}
