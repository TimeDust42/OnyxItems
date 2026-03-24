package com.timedust.onyxItems.utils;

import net.kyori.adventure.text.Component;

public enum MouseButtons {
    LEFT(Component.text("LEFT CLICK")),
    RIGHT(Component.text("RIGHT CLICK")),
    MIDDLE(Component.text("MIDDLE CLICK")),
    NONE(Component.empty());

    private final Component name;

    MouseButtons(Component name) {
        this.name = name;
    }

    public Component getName() {
        return name;
    }
}
