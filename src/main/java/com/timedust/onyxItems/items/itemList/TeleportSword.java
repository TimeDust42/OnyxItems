package com.timedust.onyxItems.items.itemList;

import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.Interactable;
import com.timedust.onyxItems.items.ItemBuilder;
import com.timedust.onyxItems.utils.MouseButtons;
import com.timedust.onyxItems.utils.TeleportUtils;
import com.timedust.onyxItems.items.utils.lore.LoreBuilder;
import com.timedust.onyxItems.items.utils.rarity.Rarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeleportSword extends AbstractItem implements Interactable {

    private static final double TELEPORT_DISTANCE = 5.0;
    private static final int CUSTOM_MODEL_ID = 21000;

    public TeleportSword() {
        super(
                "teleport_sword",
                Component.text("Teleport Sword").decoration(TextDecoration.BOLD, true),
                Material.NETHERITE_SWORD,
                Rarity.LEGENDARY
        );
    }

    @Override
    protected void addLore(LoreBuilder lb) {
        lb
                .description(
                        Component.text("Teleportation sword").color(NamedTextColor.YELLOW))
                .ability(
                        Component.text("Teleportation"),
                        MouseButtons.RIGHT,
                        Component.text("Teleports the player " + (int) TELEPORT_DISTANCE + " blocks forward"));
    }

    @Override
    protected void applyExtra(ItemBuilder builder) {
        builder.customModelData(CUSTOM_MODEL_ID);
    }

    @Override
    public void onInteract(Player player) {
        TeleportUtils.teleport(player, TELEPORT_DISTANCE, true);
    }
}
