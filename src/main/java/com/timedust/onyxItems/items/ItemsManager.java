package com.timedust.onyxItems.items;

import com.timedust.onyxItems.OnyxItems;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemsManager {

    private final Map<String, ItemStack> items = new HashMap<>();
    private final OnyxItems plugin;

    public ItemsManager(OnyxItems plugin) {
        this.plugin = plugin;
        loadItems();
    }

    private void loadItems() {
        register("onyx_pickaxe", new ItemsBuilder(plugin, "onyx_pickaxe", Material.WOODEN_PICKAXE)
                .setDisplayName("<gradient:#5555ff:#ffffff>Ониксовая Кирка</gradient>")
                .setCustomModelData(22000)
                .setMaxDurability(2500)
                .createTool(Tag.MINEABLE_PICKAXE, MiningTier.DIAMOND, 12.0f)
                .hideAttributes()
                .build());
    }

    private void register(String id, ItemStack item) {
        items.put(id, item);
    }

    public ItemStack getItem(String itemId) {
        ItemStack item = items.get(itemId.toLowerCase());
        return (item != null) ? item.clone() : null;
    }

    public Collection<String> getRegisteredIds() {
        return items.keySet();
    }
}
