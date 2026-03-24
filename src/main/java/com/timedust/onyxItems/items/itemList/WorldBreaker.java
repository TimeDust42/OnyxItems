package com.timedust.onyxItems.items.itemList;

import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.ItemBuilder;
import com.timedust.onyxItems.items.Mineable;
import com.timedust.onyxItems.items.utils.lore.LoreBuilder;
import com.timedust.onyxItems.items.utils.rarity.Rarity;
import com.timedust.onyxItems.utils.Enchant;
import com.timedust.onyxItems.utils.ItemStat;
import com.timedust.onyxItems.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WorldBreaker extends AbstractItem implements Mineable {

    private static final int CUSTOM_MODEL_ID = 21000;
    private static final List<Enchant> enchants = List.of(
            new Enchant(Enchantment.EFFICIENCY, Enchantment.EFFICIENCY.getMaxLevel()),
            new Enchant(Enchantment.SILK_TOUCH, Enchantment.SILK_TOUCH.getMaxLevel())
    );

    public WorldBreaker() {
        super(
                "world_breaker",
                Component.text("World Breaker").decoration(TextDecoration.BOLD, true),
                Material.NETHERITE_PICKAXE,
                Rarity.MYTHIC);
    }

    @Override
    protected void addLore(LoreBuilder lb) {
        Component desc = Component.text("Mines blocks in a ").color(NamedTextColor.GRAY)
                .append(Component.text("3x3").color(NamedTextColor.YELLOW))
                .append(Component.text(" area").color(NamedTextColor.GRAY));

        lb
                .description(desc);
    }

    @Override
    protected void applyExtra(ItemBuilder builder) {
        builder.customModelData(CUSTOM_MODEL_ID);
        builder.hideAll();
        builder.unbreakable(true);
        builder.enchantAll(enchants);
    }

    @Override
    public void onMine(Player player, Block block, BlockFace face) {
        ItemStack tool = player.getInventory().getItemInMainHand();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (face.getModX() != 0 && x != 0) continue;
                    if (face.getModY() != 0 && y != 0) continue;
                    if (face.getModZ() != 0 && z != 0) continue;

                    if (x == 0 && y == 0 && z == 0) continue;

                    Block relative = block.getRelative(x, y, z);

                    if (canBreak(player, relative, tool) && Tag.MINEABLE_PICKAXE.isTagged(relative.getType())) {
                        relative.breakNaturally(tool);
                        ItemUtils.damageItem(tool);
                    }
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
