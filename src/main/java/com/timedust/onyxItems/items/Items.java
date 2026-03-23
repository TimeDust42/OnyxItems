package com.timedust.onyxItems.items;

import com.timedust.onyxItems.items.itemList.TeleportSword;
import com.timedust.onyxItems.items.itemList.WorldBreaker;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public class Items {

    public static final SimpleItem ANOTHER_PAPER = new SimpleItem(
            "another_paper",
            Component.text("Another Paper"),
            Material.PAPER,
            List.of());

    public static final AbstractItem TELEPORT_SWORD = new TeleportSword();
    public static final AbstractItem WORLD_BREAKER = new WorldBreaker();

    public static void registerItems() {
        ItemFactory.registerItem(ANOTHER_PAPER);
        ItemFactory.registerItem(TELEPORT_SWORD);
        ItemFactory.registerItem(WORLD_BREAKER);
    }
}
