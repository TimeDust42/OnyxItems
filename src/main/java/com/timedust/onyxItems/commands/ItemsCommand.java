package com.timedust.onyxItems.commands;

import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.ItemFactory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Component.text("Only players may use this command!",  NamedTextColor.RED));
            return true;
        }

        if (strings.length == 0) {
            commandSender.sendMessage(Component.text("No items to execute!",  NamedTextColor.RED));
            return true;
        }

        if (strings.length == 1) {
            String itemId = strings[0];
            if (ItemFactory.hasItem(itemId)) {
                var item = ItemFactory.getByID(itemId);
                player.getInventory().addItem(item.getItem());
                player.sendMessage(Component.text("You received a " + item.displayName(), NamedTextColor.GREEN));
            } else  {
                commandSender.sendMessage(Component.text("No items found!", NamedTextColor.RED));
            }
            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 1) {
            return ItemFactory.getItems().stream().map(AbstractItem::id).toList();
        }

        return List.of();
    }
}
