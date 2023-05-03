package com.kalle.customshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private static List<Category> categories = new ArrayList<>();

    private String name;
    private ItemStack icon;
    private List<Offer> offers;

    public Category(String name, Material icon, List<Offer> offers) throws IllegalArgumentException {
        this.name = name;
        this.icon = new ItemStack(icon);
        ItemMeta meta = this.icon.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Left/Right click to");
        lore.add(ChatColor.DARK_GRAY + "select this category!");
        meta.setLore(lore);
        this.icon.setItemMeta(meta);
        this.offers = offers;
        categories.add(this);
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static Category getCategory(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) return category;
        }
        return null;
    }

    public static void resetCategories() {
        categories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public List<Offer> getOffers() {
        return offers;
    }

}
