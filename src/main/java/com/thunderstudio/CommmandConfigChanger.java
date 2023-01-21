package com.thunderstudio;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommmandConfigChanger implements org.bukkit.command.CommandExecutor {

    FileConfiguration c;
    BetterGoldenApple pluginMainClass;

    public CommmandConfigChanger(BetterGoldenApple mainClass){
        c = mainClass.getConfig();
        pluginMainClass = mainClass;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("You are not a player");
            return true;
        }
        Player p = (Player)sender;
        if (p.hasPermission("bettergapple.configure")){
            p.sendMessage(ChatColor.RED + "You have no permission to execute the command!");
            return true;
        }
        if (args.length > 4 || args.length == 0) {
            p.sendMessage("Args are too long or too short!");
        }
        if (args[0].equalsIgnoreCase("get")){
            return getConfigures(p);
        }
        if (args[0].equalsIgnoreCase(("set"))){
            if (args.length != 4){
                p.sendMessage(ChatColor.RED + "Args incorrect!");
            }
            if (args[1].equalsIgnoreCase("regeneration")){
                pluginMainClass.getConfig().set("GoldenApple.regeneration.time",args[2]);
                pluginMainClass.getConfig().set("GoldenApple.regeneration.amplifier",args[3]);
                pluginMainClass.saveConfig();
            }
            if (args[1].equalsIgnoreCase("absorption")){
                pluginMainClass.getConfig().set("GoldenApple.absorption.time",args[2]);
                pluginMainClass.getConfig().set("GoldenApple.absorption.amplifier",args[3]);
                pluginMainClass.saveConfig();
            }
            p.sendMessage(ChatColor.AQUA + "Setting Complete.");
            getConfigures(p);
        }
        return true;
    }

    private boolean getConfigures(Player p) {
        int regen_t, regen_lvl, absorption_t, absorption_lvl;
        pluginMainClass.reloadConfig();
        c = pluginMainClass.getConfig();
        try {
            ConfigurationSection regen_c = c.getConfigurationSection("GoldenApple.regeneration");
            ConfigurationSection absorption_c = c.getConfigurationSection("GoldenApple.absorption");
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
            p.sendMessage(ChatColor.RED + "Configure getting failed!");
            return true;
        }
        p.sendMessage(ChatColor.AQUA + "Configures:");
        p.sendMessage(ChatColor.AQUA + "Regeneration Level : " + regen_lvl);
        p.sendMessage(ChatColor.AQUA + "Regeneration Time : " + regen_t);
        p.sendMessage(ChatColor.AQUA + "Absorption Level : " + absorption_lvl);
        p.sendMessage(ChatColor.AQUA + "Absorption Time : " + absorption_t);
        return true;
    }
}
