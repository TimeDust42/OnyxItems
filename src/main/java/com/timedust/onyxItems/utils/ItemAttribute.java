package com.timedust.onyxItems.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.attribute.AttributeModifier;

public record ItemAttribute(Component name, double value, AttributeModifier.Operation operation, NamedTextColor valueColor) {

    public ItemAttribute(Component name, int value, AttributeModifier.Operation operation, NamedTextColor valueColor) {
        this(name, (double) value, operation, valueColor);
    }

    public String getFormattedValue() {
        if (value == (long) value) {
            switch (operation) {
                case ADD_NUMBER -> {
                    return String.format("+%d", (long) value);
                }
                case ADD_SCALAR ->  {
                    return String.format("+%d%%", (long) value * 100);
                }
                default -> throw new IllegalStateException("Unhandled operation: " + operation);
            }
        } else {
            switch (operation) {
                case ADD_NUMBER -> {
                    return String.format("+%.1f", value);
                }
                case ADD_SCALAR ->  {
                    return String.format("+%.1f%%", value * 100);
                }
                default -> throw new IllegalStateException("Unhandled operation: " + operation);
            }
        }
    }
}
