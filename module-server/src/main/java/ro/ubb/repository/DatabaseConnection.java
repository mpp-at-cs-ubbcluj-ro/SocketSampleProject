package ro.ubb.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private final static Logger LOG = LogManager.getLogger(DatabaseConnection.class);
    private Connection connection;
    private final Properties databaseProperties;

    public DatabaseConnection(Properties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        return createConnection();
    }

    private synchronized Connection createConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            LOG.info("createConnection - Creating a new database connection");
            connection = DriverManager.getConnection(databaseProperties.getProperty("database.url"));
            return connection;
        } catch (SQLException e) {
            LOG.error("createConnection - Creation failed - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
