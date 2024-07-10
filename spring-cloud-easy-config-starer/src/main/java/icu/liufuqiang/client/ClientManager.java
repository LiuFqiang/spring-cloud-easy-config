package icu.liufuqiang.client;

import icu.liufuqiang.JdbcConfigProperties;
import icu.liufuqiang.config.JdbcDataSource;

import javax.sql.DataSource;

/**
 * @author liufuqiang
 * @Date 2024-07-09 17:50:06
 */
public class ClientManager {

    private static DataSource dataSource;


    public static DataSource getDataSource(JdbcConfigProperties properties) {
        if (dataSource == null) {
            JdbcDataSource jdbcDataSource = new JdbcDataSource(properties);
            dataSource = jdbcDataSource.build();
        }
        return dataSource;
    }


}
