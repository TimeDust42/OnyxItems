package com.timedust.onyxItems.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class TeleportUtils {

    public static final Map<UUID, Long> noFall = new HashMap<>();

    public static void teleport(Player player, double distance, boolean safeFall) {
        Location start = player.getEyeLocation();
        Vector direction = start.getDirection().normalize();

        Location lastSafe = player.getLocation().clone();

        double step = 0.3;

        for (double d = 0; d <= distance; d += step) {
            Location check = start.clone().add(direction.clone().multiply(d));

            Block feetBlock = check.getBlock();
            Block headBlock = check.clone().add(0, 1, 0).getBlock();

            if (isSafe(feetBlock) && isSafe(headBlock)) {
                lastSafe = check.clone();
            } else {
                break;
            }
        }

        player.teleport(lastSafe);
        player.setFallDistance(0);

        if (safeFall) {
            applySafeFall(player);
        }
    }

    private static boolean isSafe(Block block) {
        if (!block.isPassable()) return false;

        Material type = block.getType();

        return switch (type) {
            case LAVA, FIRE, SOUL_FIRE, CACTUS, SWEET_BERRY_BUSH -> false;
            default -> true;
        };
    }

    private static void applySafeFall(Player player) {
        noFall.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
