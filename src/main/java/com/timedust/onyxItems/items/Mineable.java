package com.timedust.onyxItems.items;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public interface Mineable {
    void onMine(Player player, Block block, BlockFace face);
}
