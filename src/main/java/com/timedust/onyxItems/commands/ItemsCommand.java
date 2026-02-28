package com.timedust.onyxItems.commands;

import com.timedust.onyxItems.items.ItemsManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ItemsCommand implements CommandExecutor, TabCompleter {

    private final ItemsManager manager;

    public ItemsCommand(ItemsManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command only for players!");
            return true;
        }

        switch (args[0]) {
            case "give" -> {
                String itemId = args[1];

                ItemStack item = manager.getItem(itemId);

                if (item == null) {
                    sender.sendMessage("Предмет " + itemId + " не найден!");
                    return true;
                }

                player.getInventory().addItem(item);

                player.sendMessage(Component.text("Вы получили ")
                        .append(item.displayName()));
                return true;
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("give");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            return manager.getRegisteredIds().stream().sorted().toList();
        }

        return List.of();
    }
}
