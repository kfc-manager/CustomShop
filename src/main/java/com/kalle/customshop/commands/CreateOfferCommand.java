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

public class CreateOfferCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) {
                sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                        + ChatColor.RED + "you don't have permission to execute this command.");
                return true;
            }
        }
        if (args.length < 4) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the command requires four arguments.");
            return false;
        }
        if (args.length > 4) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the command only accepts only four arguments.");
            return false;
        }
        Category category = Category.getCategory(args[3]);
        if (category == null) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the category "
                    + ChatColor.GOLD + "'" + args[3] + "'"
                    + ChatColor.RED + " doesn't exist.");
            return false;
        }
        double price = 0.0;
        try {
            price = Double.parseDouble(args[2]);
            if (price < 0.0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the argument "
                    + ChatColor.GOLD + "'" + args[2] + "'"
                    + ChatColor.RED + " is not a positive decimal number.");
            return false;
        }
        Material item = null;
        try {
            item = Material.valueOf(args[1]);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the second argument must be a material, " +
                    "make sure you spelled correct. You can find a list of the available materials here: "
                    + ChatColor.GOLD + "https://helpch.at/docs/1.8/org/bukkit/Material.html"
                    + ChatColor.RED + ".");
            return false;
        }
        if (!Configuration.getInstance().createOffer(category, args[0], item, price)) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the offer name "
                    + ChatColor.GOLD + "'" + args[0] + "'"
                    + ChatColor.RED + " is already in use.");
            return true;
        }
        if (category.getOffers().size() > 53) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "the maximum of 54 offers in this category is reached." +
                    " You have to delete one offer of this category before you're able to create a new one.");
            return true;
        }
        sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                + ChatColor.GREEN + "the offer "
                + ChatColor.GOLD + "'" + args[0] + "'"
                + ChatColor.GREEN + " has been created.");
        return true;
    }

}
