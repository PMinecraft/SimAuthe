package com.simauthe.locale;

import com.simauthe.SimAuthe;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocaleManager
{
    private static Locale locale;

    public void loadLocaleFile(String localeFileName)
    {
        File localeFile = new File(
                SimAuthe.getInstance().getDataFolder(), "locale" + File.separator + localeFileName + ".yml"
        );
        if (!localeFile.exists())
        {
            SimAuthe.getInstance().saveResource("locale" + File.separator + localeFileName + ".yml", false);
        }

        locale = new Locale(YamlConfiguration.loadConfiguration(localeFile));
    }
}
