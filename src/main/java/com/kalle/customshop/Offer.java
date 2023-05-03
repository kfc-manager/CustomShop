package com.kalle.customshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Offer {

    private String name;
    private ItemStack icon;
    private Material material;
    private double price;

    public Offer(String name, Material material, double price) {
        this.name = name;
        this.icon = new ItemStack(material);
        this.material = material;
        ItemMeta meta = this.icon.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "This item costs: " + price);
        lore.add(ChatColor.DARK_GRAY + "Left/Right click to buy.");
        meta.setLore(lore);
        this.icon.setItemMeta(meta);
        this.price = price;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public boolean buyOffer(Player player) {
        if (!VaultAPI.getInstance().getEconomy().has(player,price)) {
            player.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "you can't afford to purchase this offer.");
            return false;
        }
        HashMap<Integer , ItemStack> itemsLeft = player.getInventory().addItem(new ItemStack(material));
        if (!itemsLeft.isEmpty()) {
            player.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "you don't have enough space left in your inventory to purchase this item.");
            return false;
        }
        VaultAPI.getInstance().getEconomy().withdrawPlayer(player, price);
        return true;
    }

}
