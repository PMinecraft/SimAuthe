package com.simauthe.database;

import com.simauthe.SimAuthe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager
{
    private static Connection connection;
    private static Statement stmt;

    public static void connectDatabase() throws ClassNotFoundException, SQLException
    {
        String url;

        if ("sqlite".equalsIgnoreCase(SimAuthe.getPlguinConfig().getString("database.type")))
        {
            Class.forName("org.sqlite.JDBC");

            url = "jdbc:sqlite:plugins/SimAuthe/player_data.db";
            connection = DriverManager.getConnection(url);
            return;
        }

        if ("mysql".equalsIgnoreCase(SimAuthe.getPlguinConfig().getString("database.type")))
        {
            String host = SimAuthe.getPlguinConfig().getString("database.host");
            String port = SimAuthe.getPlguinConfig().getString("database.port");
            String database = SimAuthe.getPlguinConfig().getString("database.database_name");
            String username = SimAuthe.getPlguinConfig().getString("database.username");
            String password = SimAuthe.getPlguinConfig().getString("database.password");

            url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
            connection = DriverManager.getConnection(url, username, password);
        }
    }

    public static void createTable() throws SQLException
    {
        String sql = StatementHandle.getStatements().getString("create_table");
        stmt = connection.createStatement();
        stmt.execute(sql);
    }

    public static Connection getConnection()
    {
        return connection;
    }

    public static void close() throws SQLException
    {
        connection.close();
        DataBaseOperate.close();
    }
}
