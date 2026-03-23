package com.timedust.onyxItems.items;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemFactory {

    private static final Map<String, AbstractItem> registry = new HashMap<>();

    public static void registerItem(AbstractItem item) {
        registry.put(item.id(), item);
    }

    public static AbstractItem getByID(String id) {
        return registry.get(id);
    }

    public static List<AbstractItem> getItems() {
        return new ArrayList<>(registry.values());
    }

    public static boolean hasItem(String id) {
        return registry.containsKey(id);
    }

    public static ItemStack createItem(String id) {
        AbstractItem item = registry.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found: " + id);
        }
        return item.getItem();
    }

    public static void clearItems() {
        registry.clear();
    }
}
