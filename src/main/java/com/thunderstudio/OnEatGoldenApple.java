// TODO: 添加注释 | Add reference
package com.thunderstudio;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class OnEatGoldenApple implements Listener {

    //Config Variables
    private FileConfiguration config = Bukkit.spigot().getConfig(); //File Config variable
    private PotionEffect regeneration,absorption;
    // After loading configure, Pot effects will save here.

    //Load Configures
    private Boolean LoadConfig() {
        // Absorption Level
        int absorption_lvl;
        // Regeneration Time
        int regen_t;
        // Regeneration Level
        int regen_lvl;
        // Absorption Time
        int absorption_t;
        try {
            ConfigurationSection regen_c = config.getConfigurationSection("GoldenApple.regeneration");
            ConfigurationSection absorption_c = config.getConfigurationSection("GoldenApple.absorption");
            assert regen_c != null;
            regen_t = regen_c.getInt("time");
            regen_lvl = regen_c.getInt("amplifier");
            assert absorption_c != null;
            absorption_t = absorption_c.getInt("time");
            absorption_lvl = absorption_c.getInt("amplifier");
        } catch (Exception e){
            e.printStackTrace();
            Bukkit.getLogger().warning("Configure Getting failed!"); // Output if failed
            Bukkit.getLogger().warning("Please check files of BetterGapple plugin");
            return false;
        }
        regeneration = new PotionEffect(PotionEffectType.REGENERATION, regen_t, regen_lvl);
        absorption = new PotionEffect(PotionEffectType.ABSORPTION, absorption_t, absorption_lvl);
        Bukkit.getLogger().info("Loading Configure Complete."); // Output Configures
        Bukkit.getLogger().info("Configures:");
        Bukkit.getLogger().info("Regeneration Level : " + regen_lvl);
        Bukkit.getLogger().info("Regeneration Time : " + regen_t);
        Bukkit.getLogger().info("Absorption Level : " + absorption_lvl);
        Bukkit.getLogger().info("Absorption Time : " + absorption_t);
        return true;
    }

    // Constructor Method
    public OnEatGoldenApple(){
        Bukkit.getLogger().info("Registering Event...");
        Bukkit.getLogger().info("Getting Configure...");
        Boolean flag = LoadConfig(); // Load Configs
        if (!flag){
            Bukkit.getLogger().warning("Event Listener Constructing failed.");
        }
    }

    private void ReloadConfig(){
        Bukkit.getLogger().info("Reloading configure...");
        config = Bukkit.spigot().getConfig();
        LoadConfig();
    }

    @EventHandler
    public void PlayerEatGoldenAppleEvent(PlayerItemConsumeEvent evt){
        if (evt.isCancelled())
            return;

        if (evt.getItem().getType() == Material.GOLDEN_APPLE){
            Collection<PotionEffect> pots = evt.getPlayer().getActivePotionEffects();
            PotionEffect player_regen = null;
            PotionEffect player_absorption = null;
            PotionEffect final_regeneration;
            PotionEffect final_absorption;
            for (PotionEffect pot: pots
                 ) {
                if (pot.getType() == PotionEffectType.REGENERATION){
                    player_regen = pot;
                } else if (pot.getType() == PotionEffectType.ABSORPTION) {
                    player_absorption = pot;
                }
            }
            if (player_regen == null){
                final_regeneration = regeneration;
            } else {
                final_regeneration = new PotionEffect(PotionEffectType.REGENERATION,
                        player_regen.getDuration() + regeneration.getDuration(),
                        player_regen.getAmplifier() + regeneration.getAmplifier());
            }
            if (player_absorption == null){
                final_absorption = absorption;
            } else {
                final_absorption = new PotionEffect(PotionEffectType.ABSORPTION,
                        player_absorption.getDuration() + absorption.getDuration(),
                        player_absorption.getAmplifier() + absorption.getAmplifier());
            }
            int i = 0;
            while (!evt.getPlayer().addPotionEffect(final_regeneration) && i <= 5){
                i++;
            }
            i = 0;
            while (!evt.getPlayer().addPotionEffect(final_absorption) && i <= 5){
                i++;
            }
        }
    }
}
