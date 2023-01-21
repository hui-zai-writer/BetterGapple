package com.thunderstudio;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ConfigChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private FileConfiguration c;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public FileConfiguration getEvtConfig() {
        return c;
    }
}
