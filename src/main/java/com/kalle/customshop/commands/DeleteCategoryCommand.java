package com.kalle.customshop.commands;

import com.kalle.customshop.Configuration;
import com.kalle.customshop.CustomShop;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCategoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) {
                sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                        + ChatColor.RED + "you don't have permission to execute this command.");
                return true;
            }
        }
        if (args.length < 1) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the command requires an argument.");
            return false;
        }
        if (args.length > 1) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the command only accepts only one argument.");
            return false;
        }
        if (!Configuration.getInstance().deleteCategory(args[0])) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the category "
                    + ChatColor.GOLD + "'" + args[0] + "'"
                    + ChatColor.RED + " doesn't exist.");
            return true;
        }
        sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                + ChatColor.GREEN + "the category "
                + ChatColor.GOLD + "'" + args[0] + "'"
                + ChatColor.GREEN + " has been deleted.");
        return true;
    }

}
