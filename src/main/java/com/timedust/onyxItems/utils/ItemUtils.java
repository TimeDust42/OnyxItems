package com.timedust.onyxItems.utils;

import com.timedust.onyxItems.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ItemUtils {

    public static void damageItem(ItemStack tool) {
        if (tool == null || !tool.hasItemMeta()) return;

        ItemMeta meta = tool.getItemMeta();
        if (meta.isUnbreakable() || !(meta instanceof Damageable damageable)) return;

        int unbreakingLevel = meta.getEnchantLevel(Enchantment.UNBREAKING);

        double chanceToDamage = 1.0 / (unbreakingLevel + 1);

        if (ThreadLocalRandom.current().nextDouble() < chanceToDamage) {
            int newDamage = damageable.getDamage() + 1;

            if (newDamage >= tool.getType().getMaxDurability()) {
                tool.setAmount(0);
            } else {
                damageable.setDamage(newDamage);
                tool.setItemMeta(damageable);
            }
        }
    }

    public static Component itemInfo(CustomItem item) {
        String id = item.id();
        String material = item.material().toString();
        Component displayName = item.displayName();
        Component rarity = item.rarity().displayName();

        var meta = item.getItem().getItemMeta();
        int cmd = meta.hasCustomModelData() ? meta.getCustomModelData() : 0;
        boolean unbreakable = meta.isUnbreakable();
        int maxStack = item.getItem().getMaxStackSize();
        String flagsString = item.getItem().getItemFlags().isEmpty()
                ? "<dark_gray>отсутствуют</dark_gray>"
                : item.getItem().getItemFlags().stream()
                .map(flag -> "<dark_gray>   -</dark_gray> <aqua>" + flag.name() + "</aqua>")
                .collect(Collectors.joining("<br>"));
        Map<Enchantment, Integer> enchants = item.getItem().getEnchantments();
        String enchantsString = enchants.isEmpty()
                ? "<dark_gray>   отсутствуют</dark_gray>"
                : enchants.entrySet().stream()
                .map(entry -> {
                    // Получаем ключ (например, "sharpness") и уровень
                    String name = entry.getKey().getKey().getKey();
                    int level = entry.getValue();
                    return "<dark_gray>   -</dark_gray> <gold>" + name + "</gold> <yellow>" + level + "</yellow>";
                })
                .collect(Collectors.joining("<br>"));

        String template = """
            <gray>Инфо о предмете:</gray>
            <dark_gray>»</dark_gray> <yellow>ID:</yellow> <white><id></white>
            <dark_gray>»</dark_gray> <yellow>Название:</yellow> <display_name>
            <dark_gray>»</dark_gray> <yellow>Редкость:</yellow> <rarity>
            <dark_gray>»</dark_gray> <yellow>Материал:</yellow> <white><material></white>
            <dark_gray>»</dark_gray> <yellow>ModelData:</yellow> <white><cmd></white>
            <dark_gray>»</dark_gray> <yellow>Неразрушим:</yellow> <white><unbreakable></white>
            <dark_gray>»</dark_gray> <yellow>Стак:</yellow> <white><max_stack></white>
            <dark_gray>»</dark_gray> <yellow>Флаги:</yellow>
            <flags>
            <dark_gray>»</dark_gray> <yellow>Зачарования:</yellow>
            <enchants>
            """;

        return MiniMessage.miniMessage().deserialize(template,
                Placeholder.unparsed("id", id),
                Placeholder.component("display_name", displayName),
                Placeholder.component("rarity", rarity),
                Placeholder.unparsed("material", material),
                Placeholder.unparsed("cmd", String.valueOf(cmd)),
                Placeholder.unparsed("unbreakable", String.valueOf(unbreakable)),
                Placeholder.unparsed("max_stack", String.valueOf(maxStack)),
                Placeholder.parsed("flags", flagsString),
                Placeholder.parsed("enchants", enchantsString)
        );
    }
}
