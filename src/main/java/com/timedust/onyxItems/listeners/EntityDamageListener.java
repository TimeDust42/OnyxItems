package com.timedust.onyxItems.listeners;

import com.timedust.onyxItems.utils.TeleportUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        UUID uuid = player.getUniqueId();
        Long time = TeleportUtils.noFall.get(uuid);

        if (time == null) return;

        long now = System.currentTimeMillis();

        if (now - time < 3000) {
            event.setCancelled(true);
        }

        TeleportUtils.noFall.remove(uuid);
    }

}
