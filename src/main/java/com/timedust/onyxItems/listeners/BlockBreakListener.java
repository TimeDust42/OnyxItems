package com.timedust.onyxItems.listeners;

import com.timedust.onyxItems.Keys;
import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.ItemFactory;
import com.timedust.onyxItems.items.Mineable;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final Set<UUID> isMining = new HashSet<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (isMining.contains(uuid)) return;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!item.hasItemMeta()) return;

        String itemId = item.getItemMeta().getPersistentDataContainer().get(Keys.ITEM_ID_KEY, PersistentDataType.STRING);
        if (itemId == null) return;

        AbstractItem customItem = ItemFactory.getByID(itemId);
        if (customItem == null) return;

        if (customItem instanceof Mineable mineable) {
            RayTraceResult ray = player.rayTraceBlocks(5);
            BlockFace face = getBlockFace(player);

            player.sendMessage("Вы ударили по грани: " + face.name());

            try {
                isMining.add(uuid);
                mineable.onMine(player, event.getBlock(), face);
            } finally {
                isMining.remove(uuid);
            }
        }
    }

    private BlockFace getBlockFace(Player player) {
        float pitch = player.getLocation().getPitch();
        float yaw = player.getLocation().getYaw();

        // Если игрок смотрит сильно вверх или вниз — это всегда горизонтальный разлом (UP/DOWN)
        if (pitch < -45) return BlockFace.DOWN;
        if (pitch > 45) return BlockFace.UP;

        // Иначе определяем по горизонтальному углу (стороны света)
        yaw = (yaw % 360 + 360) % 360; // нормализуем угол от 0 до 360
        if (yaw >= 315 || yaw < 45) return BlockFace.SOUTH;
        if (yaw >= 45 && yaw < 135) return BlockFace.WEST;
        if (yaw >= 135 && yaw < 225) return BlockFace.NORTH;
        return BlockFace.EAST;
    }
}
