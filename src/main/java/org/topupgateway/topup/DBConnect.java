package org.topupgateway.topup;

import org.topupgateway.annotation.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnect {
    protected ResourceBundle prop = ResourceBundle.getBundle("config");
    protected Connection connection;
    public DBConnect() throws SQLException {
        connection = DriverManager.getConnection(
            prop.getString("dbUrl"),
            prop.getString("dbUser"),
            prop.getString("dbPass")
        );
    }
}
