package github.wingedfish.pool.dbcp;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by lixiuhai on 2017/12/14.
 */
public class DbcpTest {


    @Test
    public void testConnection() throws Exception {

        Properties properties = new Properties();
        properties.setProperty("url","jdbc:mysql://192.168.147.87:3306/barkeeper?characterEncoding=UTF-8");
        properties.setProperty("username","jdchongzhi");
        properties.setProperty("password","jdchongzhi");
        properties.setProperty("driverClassName","com.mysql.jdbc.Driver");
        BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        dataSource.setInitialSize(5);
        Connection connection = dataSource.getConnection();
        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();

    }

}
