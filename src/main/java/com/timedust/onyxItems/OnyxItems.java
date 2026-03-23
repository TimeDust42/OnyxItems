package com.timedust.onyxItems;

import com.timedust.onyxItems.commands.ItemsCommand;
import com.timedust.onyxItems.commands.OnyxItemsCommand;
import com.timedust.onyxItems.gui.ItemListGUI;
import com.timedust.onyxItems.items.ItemFactory;
import com.timedust.onyxItems.items.Items;
import com.timedust.onyxItems.listeners.BlockBreakListener;
import com.timedust.onyxItems.listeners.EntityDamageListener;
import com.timedust.onyxItems.listeners.InventoryListener;
import com.timedust.onyxItems.listeners.ItemInteractListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class OnyxItems extends JavaPlugin {

    private static OnyxItems plugin;
    private ItemListGUI itemListGUI;

    @Override
    public void onEnable() {
        // Initialize
        plugin = this;

        Items.registerItems();

        itemListGUI = new ItemListGUI();

        // Events
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        // Commands
        registerCommand("onyxitems", new OnyxItemsCommand(itemListGUI));
        registerCommand("getitem",  new ItemsCommand());
    }

    @Override
    public void onDisable() {
        ItemFactory.clearItems();
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
