package com.timedust.onyxItems.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;

public record Enchant(Enchantment enchantment, int level) {
    public Component text() {
        return TextUtils.removeItalic(enchantment.displayName(level));
    }
}
