package com.timedust.onyxItems;

import com.timedust.onyxItems.ability.AbilityRegistry;
import com.timedust.onyxItems.commands.OnyxItemsCommand;
import com.timedust.onyxItems.gui.ItemListGUI;
import com.timedust.onyxItems.items.ItemRegistry;
import com.timedust.onyxItems.listeners.AbilityListener;
import com.timedust.onyxItems.listeners.EntityDamageListener;
import com.timedust.onyxItems.listeners.InventoryListener;
import com.timedust.onyxItems.listeners.PlayerInteractListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class OnyxItems extends JavaPlugin {

    private static OnyxItems plugin;
    private ItemRegistry itemRegistry;
    private ItemListGUI itemListGUI;

    @Override
    public void onEnable() {
        // Initialize
        plugin = this;

        File itemsFolder = new File(getDataFolder(), "items");
        if (!itemsFolder.exists()) {
            itemsFolder.mkdirs();
        }
        itemRegistry = new ItemRegistry(getDataFolder());
        itemRegistry.load();

        itemListGUI = new ItemListGUI(itemRegistry);

        AbilityRegistry.registerAll();

        // Events
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(itemRegistry), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);

        // Commands
        registerCommand("onyxitems", new OnyxItemsCommand(itemRegistry, itemListGUI));
    }

    @Override
    public void onDisable() {
    }

    public static OnyxItems getInstance() {
        return plugin;
    }

    private void registerCommand(String commandName, Object command) {
        PluginCommand cmd = getCommand(commandName);

        if (cmd == null) {
            this.getLogger().warning("Could not find command " + commandName);
            return;
        }

        if (command instanceof CommandExecutor exec) cmd.setExecutor(exec);
        if (command instanceof TabCompleter tab) cmd.setTabCompleter(tab);
    }
}
