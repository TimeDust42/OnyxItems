package com.timedust.onyxItems.items;

import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MiningTier {
    STONE(1, Tag.NEEDS_STONE_TOOL),
    IRON(2, Tag.NEEDS_IRON_TOOL),
    DIAMOND(3, Tag.NEEDS_DIAMOND_TOOL);

    private final int level;
    private final Tag<Material> tag;

    MiningTier(int level, Tag<Material> tag) {
        this.level = level;
        this.tag = tag;
    }

    public List<MiningTier> getHigherTiers() {
        return Arrays.stream(MiningTier.values())
                .filter(t -> t.level > this.level)
                .collect(Collectors.toList());
    }

    public int getLevel() {
        return level;
    }

    public Tag getTag() {
        return tag;
    }
}
