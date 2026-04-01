package com.timedust.onyxItems.listeners;

import com.timedust.onyxItems.Keys;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.NETHERITE_HOE) return;

        if (item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Keys.ITEM_ID_KEY)) {
            event.setUseInteractedBlock(Event.Result.DENY);
        }
    }

}
