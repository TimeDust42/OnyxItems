package com.timedust.onyxItems;

import com.timedust.onyxItems.commands.ItemsCommand;
import com.timedust.onyxItems.items.ItemsManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class OnyxItems extends JavaPlugin {

    private static OnyxItems instance;

    private ItemsManager itemsManager;

    @Override
    public void onEnable() {
        instance = this;

        itemsManager = new ItemsManager(this);

        Objects.requireNonNull(getCommand("onyxitems")).setExecutor(new ItemsCommand(itemsManager));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static OnyxItems getInstance() {
        return instance;
    }
}
