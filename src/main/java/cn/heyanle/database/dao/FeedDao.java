package cn.heyanle.database.dao;

import cn.heyanle.database.DBHandlerQuery;
import cn.heyanle.database.info.FeedInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedDao {

    public static List<FeedInfo> getAllFeeds(){

        List<FeedInfo> infos = new ArrayList<>();

        DBHandlerQuery.newHandler().select("*").from("rss_feed").sql(new DBHandlerQuery.ResultAble() {
            @Override
            public void onResult(ResultSet rs) throws SQLException {
                while (rs.next()){
                    infos.add(getFeedByResultSet(rs));
                }
            }
        });

        return infos;

    }

    public static List<FeedInfo> getFeedsByUserId(int id){

        List<FeedInfo> infos = new ArrayList<>();

        DBHandlerQuery.newHandler().select("*").from("rss_feed_users").where("user_id="+id).sql(new DBHandlerQuery.ResultAble() {
            @Override
            public void onResult(ResultSet rs) throws SQLException {
                while (rs.next()){
                    int id = rs.getInt("rss_feed_id");
                    DBHandlerQuery.newHandler().select("*").from("rss_feed").where("id="+id).sql(new DBHandlerQuery.ResultAble() {
                        @Override
                        public void onResult(ResultSet rs) throws SQLException {

                            List<Integer> ids = new ArrayList<>();

                            while (rs.next()){
                                ids.add(rs.getInt("id"));
                            }

                            StringBuilder builder = new StringBuilder().append("(");
                            for(Integer id : ids){
                                builder.append(id).append(",");
                            }
                            if(builder.substring(builder.length() - 1).equals(",")){
                                builder.deleteCharAt(builder.length() - 1);
                            };
                            builder.append(")");
                            DBHandlerQuery.newHandler().select("*").from("rss_feed").where("id IN "+builder.toString()).sql(new DBHandlerQuery.ResultAble() {
                                @Override
                                public void onResult(ResultSet rs) throws SQLException {
                                    while (rs.next()){
                                        infos.add(getFeedByResultSet(rs));
                                    }
                                }
                            });

                        }
                    });
                }
            }
        });

        return infos;

    }

    public static FeedInfo getFeedByResultSet(ResultSet rs) throws SQLException{
        return FeedInfo.newEmpty()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setDescribe(rs.getString("describe"))
                .setValue(rs.getString("value"))
                .setType(rs.getInt("type"))
                .setFrom(rs.getString("from"))
                .setClassN(rs.getString("class"));
    }

}
