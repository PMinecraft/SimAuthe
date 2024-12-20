package com.simauthe.command;

import com.simauthe.SimAuthe;
import com.simauthe.database.DataBaseOperate;
import com.simauthe.locale.Locale;
import com.simauthe.locale.LocaleEntry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("simauthe.command.register"))
        {
            sender.sendMessage(Locale.getEntry(LocaleEntry.not_permission));
            return true;
        }

        if (args.length != 2)
        {
            sender.sendMessage(Locale.getEntry(LocaleEntry.command_error));
            return true;
        }

        if (SimAuthe.getPlguinConfig().getBoolean("password_strength_check.enable"))
        {
            if (!isValidPassword(args[0]))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SimAuthe.getPlguinConfig().getString("password_strength_check.show_information")));
                return true;
            }
        }

        if (!args[0].equals(args[1]))
        {
            sender.sendMessage(Locale.getEntry(LocaleEntry.second_password_inconsistency));
            return true;
        }

        try
        {
            DataBaseOperate.insert((Player) sender, args[0]);

            sender.sendMessage(DataBaseOperate.query((Player) sender));
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }



        return true;
    }

    private boolean isValidPassword(String password)
    {
        if (SimAuthe.getPlguinConfig().getBoolean("password_strength_check.regex.enable"))
        {
            String regex = SimAuthe.getPlguinConfig().getString("password_strength_check.regex.regex_string");
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }

        if (password.length() < SimAuthe.getPlguinConfig().getInt("password_strength_check.password_strength.min_length"))
        {
            return false;
        }

        if (!password.matches(".*[A-Z].*") && SimAuthe.getPlguinConfig().getBoolean("password_strength_check.password_strength.require_uppercase"))
        {
            return false;
        }

        if (!password.matches(".*[a-z].*") && SimAuthe.getPlguinConfig().getBoolean("password_strength_check.password_strength.require_lowercase"))
        {
            return false;
        }

        if (!password.matches(".*\\d.*") && SimAuthe.getPlguinConfig().getBoolean("password_strength_check.password_strength.require_number"))
        {
            return false;
        }

        return password.matches(".*[!@#$%^&*()_+={}\\[\\]:;\"'<>?,./|`~\\-].*") && SimAuthe.getPlguinConfig().getBoolean("password_strength_check.password_strength.require_special");
    }
}
