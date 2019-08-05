package cn.heyanle.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DBConnection {

    private static Logger log = Logger.getLogger(DBConnection.class);
    private static DruidDataSource druidDataSource = null;

    static {
        Properties properties = loadPropertiesFile();
        try {
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            log.error("获取配置失败");
        }
    }

    /**
     * 获取一个Druid连接
     * @return  Druid连接
     * @throws SQLException SQL异常
     */
    public DruidPooledConnection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }

    /**
     * @return Properties对象
     */
    private static Properties loadPropertiesFile() {
        String webRootPath = null;
        webRootPath = Objects.requireNonNull(DBConnection.class.getClassLoader().getResource("")).getPath();
        webRootPath = new File(webRootPath).getParent();
        InputStream inputStream = null;
        Properties p =null;
        try {
            inputStream = new FileInputStream(new File(webRootPath + File.separator + "/MyKindleRss/db_server.properties"));
            p = new Properties();
            p.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream){
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return p;
    }



    private DBConnection() { }

    public static DBConnection getInstance() {
        return DBConnectionFactory.INSTANCE;
    }

    private static class DBConnectionFactory {
        private final static DBConnection INSTANCE = new DBConnection();
    }

}
