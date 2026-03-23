package com.timedust.onyxItems.gui;

import com.timedust.onyxItems.Keys;
import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.ItemFactory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemListGUI {

    public void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(new MenuHolder(event -> {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item != null && item.hasItemMeta()) {
                String id = item
                        .getItemMeta()
                        .getPersistentDataContainer()
                        .get(Keys.ITEM_ID_KEY, PersistentDataType.STRING);
                if (id != null) {
                    AbstractItem original = ItemFactory.getByID(id);
                    if (original != null) {
                        Component itemComponent = original.displayName().hoverEvent(item.asHoverEvent()).color(original.rarity().getColor());

                        Component message = Component.text("Вы получили предмет: ", NamedTextColor.GREEN)
                                        .append(itemComponent);

                        event.getWhoClicked().sendMessage(message);
                        event.getWhoClicked().getInventory().addItem(original.getItem());
                    }
                }
            }
        }),
                54,
                Component.text("Item List",  NamedTextColor.YELLOW));
        List<ItemStack> items = ItemFactory.getItems().stream().map(AbstractItem::getItem).toList();

        int slot = 0;
        for (ItemStack original : items) {
            ItemStack guiItem = original.clone();
            ItemMeta meta = guiItem.getItemMeta();

            if (meta != null) {
                List<Component> lore = new ArrayList<>(Objects.requireNonNullElse(meta.lore(), List.of()));

                lore.add(Component.empty());
                lore.add(Component.text("Click to collect").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
                meta.lore(lore);
                guiItem.setItemMeta(meta);
            }

            inventory.setItem(slot++, guiItem);
            if (slot >= 54) break;
        }

        player.openInventory(inventory);
    }
}
