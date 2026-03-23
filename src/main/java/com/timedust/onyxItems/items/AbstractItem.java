package com.timedust.onyxItems.items;

import com.timedust.onyxItems.items.utils.lore.LoreBuilder;
import com.timedust.onyxItems.items.utils.rarity.Rarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractItem {

    protected final String id;
    protected final Component displayName;
    protected final Material material;
    protected final Rarity rarity;

    public AbstractItem(String id, Component displayName, Material material, Rarity rarity) {
        this.id = id;
        this.displayName = displayName;
        this.material = material;
        this.rarity = rarity != null ? rarity : Rarity.COMMON;
    }

    public AbstractItem(String id, Component displayName, Material material) {
        this(id, displayName, material, Rarity.COMMON);
    }

    /**
     * Добавления кастомных настроек в {@link ItemBuilder}
     */
    protected void applyExtra(ItemBuilder builder) {
        // Для переопределения в дочерних классах
    }

    /**
     * Добавление кастомного лора через {@link LoreBuilder}
     */
    protected void addLore(LoreBuilder lb) {
        // Для переопределения в дочерних классах
    }

    public ItemStack getItem() {
        LoreBuilder lb = new LoreBuilder()
                .rarity(rarity);

        addLore(lb);

        ItemBuilder builder = new ItemBuilder(material)
                .id(id)
                .name(displayName)
                .rarity(this.rarity)
                .lore(lb);

        applyExtra(builder);

        return builder.build();
    }

    // --- Геттеры ---
    public String id() {
        return id;
    }
    public Component displayName() {
        return displayName;
    }
    public Material material() {
        return material;
    }
    public Rarity rarity() {
        return rarity;
    }
}
