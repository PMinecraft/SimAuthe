package com.simauthe.database;

import com.simauthe.SimAuthe;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class StatementHandle
{
    private static FileConfiguration statements;

    public static FileConfiguration getStatements()
    {
        return statements;
    }

    public void loadStatementFile()
    {
        File localeFile = new File(
                SimAuthe.getInstance().getDataFolder(), "sql_statement.yml"
        );
        if (!localeFile.exists())
        {
            SimAuthe.getInstance().saveResource("sql_statement.yml", false);
        }

        statements = YamlConfiguration.loadConfiguration(localeFile);
    }
}
