package com.timedust.onyxItems.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class TeleportUtils {

    public static final Map<UUID, Long> noFall = new HashMap<>();

    public static void teleport(Player player, double distance, boolean safeFall) {
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().normalize();

        Location endPoint = eyeLoc.clone().add(direction.clone().multiply(distance));
        Block endBlock = endPoint.getBlock();

        Location teleportLoc;

        if (!isSafe(endBlock)) {
            // Целимся в блок — шагаем назад по лучу до безопасного места
            teleportLoc = findSafeGoingBack(eyeLoc, direction, distance);
        } else {
            // Целимся в воздух — телепортируемся туда как есть,
            // но выравниваем Y чтобы игрок стоял ровно на блоке под ногами
            teleportLoc = snapAirLocation(endPoint, eyeLoc.getYaw(), eyeLoc.getPitch());
        }

        if (teleportLoc == null) {
            teleportLoc = player.getLocation();
        }

        player.teleport(teleportLoc);
        player.setFallDistance(0);

        if (safeFall) {
            applySafeFall(player);
        }
    }

    /**
     * Целимся в блок: идём назад по лучу, ищем первое место
     * с твёрдым полом и 2 свободными блоками над ним.
     */
    private static Location findSafeGoingBack(Location eyeLoc, Vector direction, double distance) {
        double step = 0.25;

        for (double d = distance; d >= 0; d -= step) {
            Location candidate = eyeLoc.clone().add(direction.clone().multiply(d));
            Block floor = candidate.clone().add(0, -1, 0).getBlock();
            Block feet  = candidate.getBlock();
            Block head  = candidate.clone().add(0, 1, 0).getBlock();

            if (!isSafe(floor) && isSafe(feet) && isSafe(head)) {
                return snapToBlock(candidate, eyeLoc.getYaw(), eyeLoc.getPitch());
            }
        }

        return null;
    }

    /**
     * Целимся в воздух: игрок хочет быть в воздухе.
     * Выравниваем Y так, чтобы ноги были ровно на целом числе блока
     * (без подпрыгивания и без провала в блок).
     */
    private static Location snapAirLocation(Location loc, float yaw, float pitch) {
        Block feet = loc.getBlock();
        Block head = loc.clone().add(0, 1, 0).getBlock();

        // Если место безопасно — просто выравниваем Y по нижней грани блока
        if (isSafe(feet) && isSafe(head)) {
            return new Location(
                    loc.getWorld(),
                    loc.getBlockX() + 0.5,
                    loc.getBlockY(),         // ровно на нижней грани блока
                    loc.getBlockZ() + 0.5,
                    yaw,
                    pitch
            );
        }

        // Если по какой-то причине место небезопасно — ищем выше
        for (int i = 1; i <= 8; i++) {
            Location above = loc.clone().add(0, i, 0);
            if (isSafe(above.getBlock()) && isSafe(above.clone().add(0, 1, 0).getBlock())) {
                return new Location(
                        loc.getWorld(),
                        loc.getBlockX() + 0.5,
                        above.getBlockY(),
                        loc.getBlockZ() + 0.5,
                        yaw,
                        pitch
                );
            }
        }

        return null;
    }

    /**
     * Выравнивает по центру блока: X+0.5, Y=целое, Z+0.5.
     */
    private static Location snapToBlock(Location loc, float yaw, float pitch) {
        return new Location(
                loc.getWorld(),
                loc.getBlockX() + 0.5,
                loc.getBlockY(),
                loc.getBlockZ() + 0.5,
                yaw,
                pitch
        );
    }

    private static boolean isSafe(Block block) {
        if (!block.isPassable()) return false;
        return switch (block.getType()) {
            case LAVA, FIRE, SOUL_FIRE, CACTUS, SWEET_BERRY_BUSH -> false;
            default -> true;
        };
    }

    private static void applySafeFall(Player player) {
        noFall.put(player.getUniqueId(), System.currentTimeMillis());
    }
}