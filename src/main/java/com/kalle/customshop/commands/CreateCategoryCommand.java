package com.kalle.customshop.commands;

import com.kalle.customshop.Category;
import com.kalle.customshop.Configuration;
import com.kalle.customshop.CustomShop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCategoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) {
                sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                        + ChatColor.RED + "you don't have permission to execute this command.");
                return true;
            }
        }
        if (args.length < 2) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the command requires two arguments.");
            return false;
        }
        if (args.length > 2) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the command only accepts only two arguments.");
            return false;
        }
        Material icon;
        try {
            icon = Material.valueOf(args[1]);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the second argument must be a material, " +
                    "make sure you spelled correct. You can find a list of the available materials here: "
                    + ChatColor.GOLD + "https://helpch.at/docs/1.8/org/bukkit/Material.html"
                    + ChatColor.RED + "."
            );
            return false;
        }
        if (!Configuration.getInstance().createCategory(args[0], icon)) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the category name "
                    + ChatColor.GOLD + "'" + args[0] + "'"
                    + ChatColor.RED + " is already in use.");
            return true;
        }
        if (Category.getCategories().size() > 53) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the maximum of 54 categories is reached." +
                    " You have to delete one category before you're able to create a new one.");
            return true;
        }
        sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                + ChatColor.GREEN + "the category "
                + ChatColor.GOLD + "'" + args[0] + "'"
                + ChatColor.GREEN + " has been created.");
        return true;
    }

}
