package com.simauthe;

import com.simauthe.command.LoginCommand;
import com.simauthe.command.RegisterCommand;
import com.simauthe.database.DatabaseManager;
import com.simauthe.database.StatementHandle;
import com.simauthe.locale.LocaleManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class SimAuthe extends JavaPlugin
{
    private static SimAuthe instance;
    private static final LocaleManager localeManager = new LocaleManager();
    private static FileConfiguration config;
    private static final StatementHandle statementHandle = new StatementHandle();

    public static SimAuthe getInstance()
    {
        return instance;
    }

    public static FileConfiguration getPlguinConfig() {
        return config;
    }

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        instance = this;
        config = getConfig();
        localeManager.loadLocaleFile(getConfig().getString("language"));
        statementHandle.loadStatementFile();

        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());

        try
        {
            DatabaseManager.connectDatabase();
            DatabaseManager.createTable();
        } catch (ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable()
    {
        try
        {
            DatabaseManager.close();
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
