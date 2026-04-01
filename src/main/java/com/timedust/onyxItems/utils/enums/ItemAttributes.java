package com.timedust.onyxItems.utils.enums;

import com.timedust.onyxItems.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum ItemAttributes {
    ATTACK_DAMAGE("attack_damage", TextUtils.removeItalic(Component.text("Урон")), NamedTextColor.RED),
    ATTACK_SPEED("attack_speed", TextUtils.removeItalic(Component.text("Скорость атаки")), NamedTextColor.BLUE),
    MOVEMENT_SPEED("movement_speed", TextUtils.removeItalic(Component.text("Скорость")), NamedTextColor.BLUE),
    HEALTH("max_health", TextUtils.removeItalic(Component.text("Здоровье")), NamedTextColor.RED);

    private final String id;
    private final Component displayName;
    private final NamedTextColor valueColor;

    ItemAttributes(String id, Component displayName, NamedTextColor valueColor) {
        this.id = id;
        this.displayName = displayName;
        this.valueColor = valueColor;
    }

    public String id() {
        return id;
    }

    public Component displayName() {
        return displayName;
    }

    public NamedTextColor valueColor() {
        return valueColor;
    }

    public static ItemAttributes fromId(String id) {
        for (ItemAttributes attr : values()) {
            if (attr.id().equals(id)) return attr;
        }
        throw new IllegalArgumentException("Unknown attribute id: " + id);
    }
}
