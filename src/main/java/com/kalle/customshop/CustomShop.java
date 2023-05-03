package com.kalle.customshop;

import com.kalle.customshop.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomShop extends JavaPlugin {

    private static CustomShop plugin;
    private final String pluginTag = ChatColor.DARK_PURPLE + "[CustomShop] " + ChatColor.RESET;

    @Override
    public void onDisable() {
        VaultAPI.getInstance().getLog().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        getServer().getConsoleSender().sendMessage(pluginTag + ChatColor.RED + "plugin has been disabled!");
    }

    @Override
    public void onEnable() {
        plugin = this;
        if (!VaultAPI.getInstance().setupEconomy()) {
            VaultAPI.getInstance().getLog().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        VaultAPI.getInstance().setupPermissions();
        VaultAPI.getInstance().setupChat();
        Configuration.getInstance().setup();
        registerCommands(); //register commands
        getServer().getPluginManager().registerEvents(new Events(), this); //register events
        getServer().getConsoleSender().sendMessage(pluginTag + ChatColor.GREEN + "plugin has been enabled!");
    }

    public static CustomShop getPlugin() {
        return plugin;
    }

    public String getPluginTag() {
        return pluginTag;
    }

    private void registerCommands() {
        getCommand("ccategory").setExecutor(new CreateCategoryCommand());
        getCommand("dcategory").setExecutor(new DeleteCategoryCommand());
        getCommand("coffer").setExecutor(new CreateOfferCommand());
        getCommand("doffer").setExecutor(new DeleteOfferCommand());
        getCommand("cshop").setExecutor(new ShopCommand());
    }
}
