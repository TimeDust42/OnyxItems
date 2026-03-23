package com.timedust.onyxItems.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

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

}
