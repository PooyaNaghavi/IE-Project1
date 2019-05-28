package dataLayer;


import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * do not reinvent the wheel!!!
 c
 * you can use DBCP or other libraries.
 *
 * @see <a href="https://www.baeldung.com/java-connection-pooling">A Simple Guide to Connection Pooling in Java</a>
 *
 * */
public class DBCPDBConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();
//    private final static String dbURL = "jdbc:mysql:joboonja.db";

    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:sql:3306/joboonja?useUnicode=yes&characterEncoding=UTF-8");
        ds.setUsername("root");
        ds.setPassword("pass123");
        ds.setMinIdle(1);
        ds.setMaxActive(100);
        ds.setMaxIdle(100);
    }

    public static Connection getConnection() throws SQLException {
        System.out.println(ds.getNumActive());
        return ds.getConnection();
    }

    private DBCPDBConnectionPool(){ }
}
