package am.home.chat.utils.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariDataSource hikariDataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Settings.getInstance().getString("db.url"));
        config.setDriverClassName(Settings.getInstance().getString("db.driver"));
        config.setUsername(Settings.getInstance().getString("db.user"));
        config.setPassword(Settings.getInstance().getString("db.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(5000);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(3);
        config.setMaxLifetime(300000);
        config.setIdleTimeout(150000);

        hikariDataSource = new HikariDataSource(config);
    }

    private DataSource() {

    }

    public static Connection getConnection() throws SQLException {
        Connection connection = hikariDataSource.getConnection();

        return connection;
    }
}
