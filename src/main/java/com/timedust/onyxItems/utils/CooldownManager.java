package com.timedust.onyxItems.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private static final Cache<UUID, Map<String, Long>> cooldowns = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    public static void setCooldown(UUID uuid, String cooldown, double durationSeconds) {
        try {
            Map<String, Long> userMap = cooldowns.get(uuid, ConcurrentHashMap::new);

            long endTime = System.currentTimeMillis() + (long)(durationSeconds * 1000);
            userMap.put(cooldown, endTime);

            cooldowns.put(uuid, userMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasCooldown(UUID uuid, String cooldown) {
        Map<String, Long> userMap = cooldowns.getIfPresent(uuid);
        if (userMap == null) return false;

        Long endTime = userMap.get(cooldown);
        if (endTime == null) return false;

        return endTime > System.currentTimeMillis();
    }

    public static double getCooldown(UUID uuid, String cooldown) {
        Map<String, Long> userMap = cooldowns.getIfPresent(uuid);
        if (userMap == null) return 0.0;

        Long endTime = userMap.get(cooldown);
        if (endTime == null) return 0.0;

        long now = System.currentTimeMillis();
        if (endTime <= now) {
            userMap.remove(cooldown);
            return 0.0;
        }

        return (endTime - now) / 1000.0;
    }

    public static void resetCooldown(UUID uuid, String cooldown) {
        Map<String, Long> userMap = cooldowns.getIfPresent(uuid);
        if (userMap != null) {
            userMap.remove(cooldown);
            if (userMap.isEmpty()) cooldowns.invalidate(uuid);
        }
    }

    public static void resetCooldowns(UUID uuid) {
        cooldowns.invalidate(uuid);
    }
}