package com.timedust.onyxItems.items;

import com.timedust.onyxItems.items.utils.lore.LoreBuilder;
import com.timedust.onyxItems.items.utils.rarity.Rarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public class SimpleItem extends AbstractItem {

    private final List<Component> description;

    public SimpleItem(String id, Component displayName, Material material, Rarity rarity, List<Component> description) {
        super(id, displayName, material, rarity);
        this.description = description;
    }

    public SimpleItem(String id, Component displayName, Material material, List<Component> description) {
        this(id, displayName, material, Rarity.COMMON, description);
    }

    @Override
    protected void addLore(LoreBuilder lb) {
        if (description != null && !description.isEmpty()) {
            lb.description(description);
        }
    }

    @Override
    protected void applyExtra(ItemBuilder builder) {}
}
