package com.timedust.onyxItems.utils;

import net.kyori.adventure.text.Component;

public record ItemStat(Component name, double value) {

    public ItemStat(Component name, int value) {
        this(name, (double) value);
    }

    public String getFormattedValue() {
        if (value == (long) value) {
            return String.format("%d", (long) value); // Выведет "7"
        } else {
            return String.format("%.1f", value);      // Выведет "1.6"
        }
    }
}
