package com.kalle.customshop.commands;

import com.kalle.customshop.CustomShop;
import com.kalle.customshop.GuiController;
import com.kalle.customshop.VaultAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "this command can only be executed by a player.");
            return true;
        }
        Player player = (Player) sender;
        VaultAPI.getInstance().getEconomy().depositPlayer(player, 100000);
        GuiController.categoryGui(player);
        return true;
    }

}
