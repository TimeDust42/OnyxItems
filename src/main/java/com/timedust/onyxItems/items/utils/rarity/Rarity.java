package com.timedust.onyxItems.items.utils.rarity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum Rarity {
    COMMON("common", NamedTextColor.WHITE, Component.text("COMMON").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true)),
    UNCOMMON("uncommon", NamedTextColor.GREEN, Component.text("UNCOMMON").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true)),
    RARE("rare", NamedTextColor.BLUE, Component.text("RARE").color(NamedTextColor.BLUE).decoration(TextDecoration.BOLD, true)),
    EPIC("epic", NamedTextColor.DARK_PURPLE, Component.text("EPIC").color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.BOLD, true)),
    LEGENDARY("legendary", NamedTextColor.GOLD, Component.text("LEGENDARY").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true)),
    MYTHIC("mythic", NamedTextColor.RED, Component.text("MYTHIC").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true));

    private final String id;
    private final NamedTextColor color;
    private final Component displayName;

    Rarity(String id, NamedTextColor color, Component displayName) {
        this.id = id;
        this.color = color;
        this.displayName = displayName;
    }

    public String id() {
        return id;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public Component displayName() {
        return displayName;
    }

    public static Rarity getById(String id) {
        for (Rarity rarity : Rarity.values()) {
            if (rarity.id().equals(id)) {
                return rarity;
            }
        }
        return null;
    }
}
