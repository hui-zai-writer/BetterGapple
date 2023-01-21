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

public class OnEatGoldenApple implements Listener {

    //Config Variables
    private PotionEffect regeneration,absorption;
    // After loading configure, Pot effects will save here.

    //Load Configures
    private Boolean LoadConfig(FileConfiguration config) {
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
            regen_t = (20 * regen_c.getInt("time"));
            regen_lvl = regen_c.getInt("amplifier");
            assert absorption_c != null;
            absorption_t = (20 * absorption_c.getInt("time"));
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

    //TODO: Fix Code of duration calculation
    // Pot Calc duration
    /*private int DurationCalc(int duration_1,int amplifier_1,int duration_2,int amplifier_2){
        double rate_between = amplifier_1 > amplifier_2 ? 1 + (amplifier_1 - 1) * 0.5 :
                1 + (amplifier_2 - 1) * 0.5;
        return (int) (amplifier_1 > amplifier_2 ? (duration_2 / rate_between + duration_1):
                (duration_1 / rate_between + duration_2));
    }*/

    // Constructor Method
    public OnEatGoldenApple(FileConfiguration c){
        Bukkit.getLogger().info("Registering Event...");
        Bukkit.getLogger().info("Getting Configure...");
        Boolean flag = LoadConfig(c); // Load Configs
        if (!flag){
            Bukkit.getLogger().warning("Event Listener Constructing failed.");
        }
    }

    @EventHandler
    private Boolean ReloadConfig(ConfigChangeEvent evt){
        Bukkit.getLogger().info("Reloading configure...");
        return LoadConfig(evt.getEvtConfig());
    }

    @EventHandler
    public void PlayerEatGoldenAppleEvent(PlayerItemConsumeEvent evt){
        if (evt.isCancelled())
            return;

        if (evt.getItem().getType() == Material.GOLDEN_APPLE){
            int i = 0;
            while (!evt.getPlayer().addPotionEffect(regeneration) && i <= 5){
                i++;
            }
            i = 0;
            while (!evt.getPlayer().addPotionEffect(absorption) && i <= 5){
                i++;
            }
        }
    }
}
