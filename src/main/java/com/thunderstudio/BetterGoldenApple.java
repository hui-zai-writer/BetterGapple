package com.thunderstudio;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterGoldenApple extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("BetterGapple is loading...");
        saveDefaultConfig();
        FileConfiguration c = getConfig();
        Bukkit.getPluginManager().registerEvents(new OnEatGoldenApple(c),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("BetterGapple Unloaded");
    }
}
