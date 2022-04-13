package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabaseConnection {

    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://localhost.database.windows.net:1433;"
                        + "database=card2cart;"
                        + "user=root@localhost;"
                        + "password=Iphonex2020;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(connectionUrl);) {
            // Code here.
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
