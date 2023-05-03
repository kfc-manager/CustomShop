package com.kalle.customshop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private static Configuration instance;

    private CustomShop plugin = CustomShop.getPlugin();

    private FileConfiguration config;
    private File file;

    private Configuration() {

    }

    public static Configuration getInstance() {
        if (instance == null) instance = new Configuration();
        return instance;
    }

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        file = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginTag() + ChatColor.GREEN + "config.yml file has been created.");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginTag() + ChatColor.RED + "CONFIG ERROR: Couldn't create config.yml file. Plugin gets disabled.");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }
        save();
    }

    private void save() {
        try {
            config.save(file);
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginTag() + ChatColor.RED + "CONFIG ERROR: Couldn't save config.yml file. Plugin gets disabled.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
        if (!loadData()) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.getPluginTag() + ChatColor.RED + "CONFIG ERROR: config.yml file is corrupted. Plugin gets disabled.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public boolean createCategory(String name, Material icon) {
        for (String key : config.getKeys(false)) {
            if (key.equals(name)) return false;
        }
        config.set(name + ".Icon", icon.toString());
        save();
        return true;
    }

    public boolean deleteCategory(String name) {
        boolean exists = false;
        for (String key : config.getKeys(false)) {
            if (key.equals(name)) {
                exists = true;
                break;
            }
        }
        if (!exists) return false;
        config.set(name, null);
        save();
        return true;
    }

    public boolean createOffer(Category category, String name, Material item, double price) {
        for (String key : config.getKeys(false)) {
            if (config.getConfigurationSection(key + ".Offers") == null) continue;
            for (String subKey : config.getConfigurationSection(key + ".Offers").getKeys(false)) {
                if (subKey.equals(name)) return false;
            }
        }
        config.set(category.getName() + ".Offers." + name + ".Item", item.toString());
        config.set(category.getName() + ".Offers." + name + ".Price", price);
        save();
        return true;
    }

    public boolean deleteOffer(String name) {
        String categoryName = null;
        for (String key : config.getKeys(false)) {
            if (config.getConfigurationSection(key + ".Offers") == null) continue;
            for (String offerKey : config.getConfigurationSection(key + ".Offers").getKeys(false)) {
                if (offerKey.equals(name)) {
                    categoryName = key;
                    break;
                }
            }
        }
        if (categoryName == null) return false;
        config.set(categoryName + ".Offers." + name, null);
        save();
        return true;
    }

    public boolean loadData() {
        Category.resetCategories();
        for (String key : config.getKeys(false)) {
            List<Offer> offers = new ArrayList<>();
            if (config.getConfigurationSection(key + ".Offers") != null) {
                for (String offerKey : config.getConfigurationSection(key + ".Offers").getKeys(false)) {
                    String itemString = config.getString(key + ".Offers." + offerKey + ".Item");
                    double price = config.getDouble(key + ".Offers." + offerKey + ".Price", -1.0);
                    if (itemString == null) return false;
                    if (price < 0.0) return false;
                    Material item;
                    try {
                        item = Material.valueOf(itemString);
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                    offers.add(new Offer(offerKey, item, price));
                }
            }
            Material icon;
            try {
                String iconString = config.getString(key + ".Icon");
                icon = Material.valueOf(iconString);
            } catch (IllegalArgumentException e) {
                return false;
            }
            Category category = new Category(key, icon, offers);
        }
        //players inventory must be closed on reload, otherwise icons might not be tracked properly
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.closeInventory();
        }
        return true;
    }

}
