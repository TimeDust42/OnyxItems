package com.timedust.onyxItems.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MenuHolder implements InventoryHolder {
    private final Consumer<InventoryClickEvent> clickHandler;

    public MenuHolder(Consumer<InventoryClickEvent> clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void handle(InventoryClickEvent event) {
        clickHandler.accept(event);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
