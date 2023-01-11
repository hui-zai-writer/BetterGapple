package com.thunderstudio;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterGoldenApple extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("BetterGapple is loading...");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new OnEatGoldenApple(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
