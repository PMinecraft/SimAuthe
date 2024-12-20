package com.simauthe.locale;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Locale
{
    private static final Map<LocaleEntry, String> localeEntry = new HashMap<>();

    public Locale(FileConfiguration localeConfig)
    {
        putLocaleEntry(localeConfig);
    }

    private void putLocaleEntry(FileConfiguration localeConfig)
    {
        localeEntry.put(
                LocaleEntry.command_error,
                ChatColor.translateAlternateColorCodes('&', localeConfig.getString("command_error"))
        );
        localeEntry.put(
                LocaleEntry.not_permission,
                ChatColor.translateAlternateColorCodes('&', localeConfig.getString("not_permission"))
        );
        localeEntry.put(
                LocaleEntry.second_password_inconsistency,
                ChatColor.translateAlternateColorCodes('&', localeConfig.getString("second_password_inconsistency"))
        );
    }

    public static String getEntry(LocaleEntry locale)
    {
        return localeEntry.get(locale);
    }
}
