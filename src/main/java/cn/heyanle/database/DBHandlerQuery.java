package cn.heyanle.database;

import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandlerQuery {

    public interface ResultAble{
        void onResult(ResultSet rs) throws SQLException;
    }

    private String sql = "";

    public DBHandlerQuery select(String select){
        sql += (" SELECT " + select);
        return this;
    }

    public DBHandlerQuery from(String from){
        sql += (" FROM " + from);
        return this;
    }

    public DBHandlerQuery where(String where){
        sql += (" WHERE " + where);
        return this;
    }

    public DBHandlerQuery and(String and){
        sql += (" AND " + and);
        return this;
    }

    public DBHandlerQuery or(String or){
        sql += (" OR " + or);
        return this;
    }

    public String sql(ResultAble ra){
        sql(sql,ra);
        return sql;
    }

    public String getSql(){
        return this.sql;
    }

    public static DBHandlerQuery newHandler(){
        return new DBHandlerQuery();
    }

    public static void sql(String sql , ResultAble ra){

        DruidPooledConnection conn = null;
        Statement statement = null;

        try {

            conn = DBConnection.getInstance().getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ra.onResult(rs);
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(conn, statement);
        }

    }

    static void close(DruidPooledConnection conn, Statement statement) {
        if (conn != null){
            try {
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (statement != null){
            try {
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
