package com.kalle.customshop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiController {

    private static ItemStack placeholder = null;

    public static void categoryGui(Player player) {
        player.closeInventory();
        List<Category> categories = Category.getCategories();
        if (categories.size() < 1) {
            player.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "shop can't be opened because no categories are created yet.");
            return;
        }
        int rows = Math.ceilDiv(categories.size(), 9) * 9;
        Inventory gui = Bukkit.createInventory(null, rows, "Server Shop");
        for (int i = 0 ; i < gui.getSize() ; i++) {
            try {
                gui.setItem(i, categories.get(i).getIcon());
            } catch (IndexOutOfBoundsException e) {
                gui.setItem(i, getPlaceholder());
            }
        }
        player.openInventory(gui);
    }

    public static void offerGui(Category category, Player player) {
        List<Offer> offers = category.getOffers();
        if (offers.size() < 1) {
            player.sendMessage(CustomShop.getPlugin().getPluginTag()
                    + ChatColor.RED + "category can't be opened because no offers have been added to this category yet.");
            player.closeInventory();
            return;
        }
        int rows = Math.ceilDiv((offers.size()), 9) * 9;
        Inventory gui = Bukkit.createInventory(null, rows,
                category.getName() + " [Balance: "
                        + VaultAPI.getInstance().getEconomy().getBalance(player) + "]");
        for (int i = 0 ; i < gui.getSize() ; i++) {
            try {
                gui.setItem(i, offers.get(i).getIcon());
            } catch (IndexOutOfBoundsException e) {
                gui.setItem(i, getPlaceholder());
            }
        }
        player.openInventory(gui);
    }

    public static ItemStack getPlaceholder() {
        if (placeholder == null) {
            placeholder = new ItemStack(Material.STAINED_GLASS_PANE);
            ItemMeta meta = placeholder.getItemMeta();
            meta.setDisplayName(" ");
            List<String> lore = new ArrayList<>();
            meta.setLore(lore);
            placeholder.setItemMeta(meta);
        }
        return placeholder;
    }

}
