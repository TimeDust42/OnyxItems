package com.timedust.onyxItems.commands;

import com.timedust.onyxItems.Keys;
import com.timedust.onyxItems.gui.ItemListGUI;
import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.CustomItem;
import com.timedust.onyxItems.items.ItemRegistry;
import com.timedust.onyxItems.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OnyxItemsCommand implements CommandExecutor, TabCompleter {

    private final ItemRegistry itemRegistry;
    private final ItemListGUI itemListGUI;

    public OnyxItemsCommand(ItemRegistry itemRegistry, ItemListGUI itemListGUI) {
        this.itemRegistry = itemRegistry;
        this.itemListGUI = itemListGUI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can execute this command!",  NamedTextColor.RED));
            return true;
        }
        if (args.length == 0) {
            player.sendMessage(Component.text("Usage: /onyxitems <arg>", NamedTextColor.RED));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("items")) {
                itemListGUI.openGUI(player);
                return true;
            }
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() == Material.AIR) return true;

                String id = item.getItemMeta().getPersistentDataContainer().get(Keys.ITEM_ID_KEY, PersistentDataType.STRING);
                if (id == null) return true;

                AbstractItem customItem = itemRegistry.getItem(id);
                if (customItem == null) return true;

                if (!(customItem instanceof CustomItem)) return true;

                Component message = ItemUtils.itemInfo((CustomItem) customItem);
                player.sendMessage(message);
                return true;
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            return List.of("items", "info");
        }
        return List.of();
    }
}
