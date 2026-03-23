package com.timedust.onyxItems.listeners;

import com.timedust.onyxItems.Keys;
import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.Interactable;
import com.timedust.onyxItems.items.ItemFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemInteractListener implements Listener {

    public ItemInteractListener() {}

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta()) return;
        if (!event.getAction().isRightClick()) return;

        String itemId = item.getItemMeta().getPersistentDataContainer().get(Keys.ITEM_ID_KEY, PersistentDataType.STRING);
        if (itemId == null) return;

        AbstractItem customItem = ItemFactory.getByID(itemId);
        if (customItem == null) return;

        if (customItem instanceof Interactable interactable) {
            event.setCancelled(true);
            interactable.onInteract(event.getPlayer());
        }
    }

}
