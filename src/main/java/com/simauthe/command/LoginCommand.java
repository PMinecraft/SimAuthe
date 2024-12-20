package com.simauthe.command;

import com.simauthe.locale.Locale;
import com.simauthe.locale.LocaleEntry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LoginCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("simauthe.command.login"))
        {
            sender.sendMessage(Locale.getEntry(LocaleEntry.not_permission));
            return true;
        }

        if (args.length != 1)
        {
            sender.sendMessage(Locale.getEntry(LocaleEntry.command_error));
            return true;
        }

        return true;
    }
}
