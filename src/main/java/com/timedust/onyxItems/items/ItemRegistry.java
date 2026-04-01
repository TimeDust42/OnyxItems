package com.timedust.onyxItems.items;

import com.timedust.onyxItems.OnyxItems;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemRegistry {

    private final Map<String, AbstractItem> items = new HashMap<>();
    private final File itemFolder;

    public ItemRegistry(File dataFolder) {
        this.itemFolder = new File(dataFolder, "items");
    }

    public void load() {
        items.clear();

        if (!itemFolder.exists()) return;

        File[] files = itemFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (String key : config.getKeys(false)) {
                if (config.isConfigurationSection(key)) {
                    CustomItem customItem = new CustomItem(Objects.requireNonNull(config.getConfigurationSection(key)));
                    items.put(key, customItem);
                }
            }
        }
        OnyxItems.getInstance().getLogger().info("Loaded " + items.size() + " custom items from configs.");
    }

    public AbstractItem getItem(String id) {
        return items.get(id);
    }

    public boolean containsItem(String id) {
        return items.containsKey(id);
    }

    public Collection<AbstractItem> getAllItems() {
        return items.values();
    }
}
