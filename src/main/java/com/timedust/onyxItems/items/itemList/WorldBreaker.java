package com.timedust.onyxItems.items.itemList;

import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.ItemBuilder;
import com.timedust.onyxItems.items.Mineable;
import com.timedust.onyxItems.items.utils.rarity.Rarity;
import com.timedust.onyxItems.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class WorldBreaker extends AbstractItem implements Mineable {

    private static final int CUSTOM_MODEL_ID = 21000;

    public WorldBreaker() {
        super(
                "world_breaker",
                Component.text("World Breaker").decoration(TextDecoration.BOLD, true),
                Material.NETHERITE_PICKAXE,
                Rarity.MYTHIC);
    }

    @Override
    protected void applyExtra(ItemBuilder builder) {
        builder.customModelData(CUSTOM_MODEL_ID);
        builder.unbreakable(true);
    }

    @Override
    public void onMine(Player player, Block block, BlockFace face) {
        ItemStack tool = player.getInventory().getItemInMainHand();

        int modX = face.getModX() == 0 ? 1 : 0;
        int modY = face.getModY() == 0 ? 1 : 0;
        int modZ = face.getModZ() == 0 ? 1 : 0;

        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a == 0 && b == 0) continue;

                int offX = (modX == 1) ? a : 0;
                int offY = (modY == 1) ? (modX == 0 ? a : b) : 0;
                int offZ = (modZ == 1) ? b : 0;

                Block relative =  block.getRelative(offX, offY, offZ);

                if (canBreak(player, relative, tool) && Tag.MINEABLE_PICKAXE.isTagged(relative.getType())) {
                    relative.breakNaturally(tool);
                    ItemUtils.damageItem(tool);
                }
            }
        }
    }

    private boolean canBreak(Player player, Block block, ItemStack tool) {
        if (block.getType().isAir() || block.getType().getHardness() < 0) return false;

        BlockBreakEvent event = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(event);

        return !event.isCancelled();
    }
}
