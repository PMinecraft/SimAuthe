package com.simauthe.database;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseOperate
{
    private static PreparedStatement stmt;
    private static String sql;

    public static void insert(Player playerName, String password) throws SQLException
    {
        sql = StatementHandle.getStatements().getString("register");
        stmt = DatabaseManager.getConnection().prepareStatement(sql);

        stmt.setString(1, playerName.getName());
        stmt.setString(2, password);
        stmt.executeUpdate();
    }

    public static String query(Player playerName) throws SQLException
    {
        sql = StatementHandle.getStatements().getString("query");
        stmt = DatabaseManager.getConnection().prepareStatement(sql);

        stmt.setString(1, playerName.getName());
        ResultSet rs = stmt.executeQuery();

        return rs.getString("password");
    }

    public static void close() throws SQLException
    {
        stmt.close();
    }
}
