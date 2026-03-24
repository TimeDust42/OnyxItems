package com.timedust.onyxItems.items;

import com.timedust.onyxItems.Keys;
import com.timedust.onyxItems.OnyxItems;
import com.timedust.onyxItems.items.utils.lore.LoreBuilder;
import com.timedust.onyxItems.items.utils.rarity.Rarity;
import com.timedust.onyxItems.utils.Enchant;
import com.timedust.onyxItems.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    private final List<Enchant> enchants = new ArrayList<>();

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        ItemMeta tempMeta = item.getItemMeta();
        if (tempMeta == null) {
            throw new IllegalArgumentException("Cannot get ItemMeta for material: " + material);
        }
        this.meta = tempMeta;
    }

    public ItemBuilder id(String id) {
        meta.getPersistentDataContainer().set(Keys.ITEM_ID_KEY, PersistentDataType.STRING, id);
        return this;
    }

    public ItemBuilder name(Component text) {
        meta.displayName(TextUtils.removeItalic(text));
        return this;
    }

    public ItemBuilder rarity(Rarity rarity) {
        if (rarity != null) {
            meta.getPersistentDataContainer().set(Keys.RARITY_KEY, PersistentDataType.STRING, rarity.getId());
        }
        return this;
    }

    public ItemBuilder lore(List<Component> lines) {
        List<Component> clean = lines.stream()
                .map(TextUtils::removeItalic)
                .toList();
        meta.lore(clean);
        return this;
    }

    public ItemBuilder lore(LoreBuilder loreBuilder) {
        if (loreBuilder == null) return this;
        return lore(loreBuilder.build());
    }

    public ItemBuilder customModelData(int customModelData) {
        meta.setCustomModelData(customModelData);
        return this;
    }

    public ItemBuilder maxStackSize(int maxStackSize) {
        meta.setMaxStackSize(maxStackSize);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder durability(int durability) {
        if (meta instanceof Damageable damageable) {
            damageable.setMaxDamage(durability);
        } else {
            OnyxItems.getInstance().getLogger().warning("Failed to set max durability for Item: " + meta.displayName());
        }
        return this;
    }

    public ItemBuilder enchant(Enchant enchant) {
        if (enchant == null) return this;
        meta.addEnchant(enchant.enchantment(), enchant.level(), true);
        this.enchants.add(enchant);
        return this;
    }

    public ItemBuilder enchantAll(List<Enchant> enchants) {
        if (enchants == null) return this;
        enchants.forEach(this::enchant);
        return this;
    }

    public ItemBuilder hideAll() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemStack build() {
        if (meta.getPersistentDataContainer().has(Keys.RARITY_KEY, PersistentDataType.STRING)) {
            String rarityId = meta.getPersistentDataContainer().get(Keys.RARITY_KEY, PersistentDataType.STRING);
            Rarity rarity = Rarity.getById(rarityId);
            if (rarity != null && meta.displayName() != null) {
                meta.displayName(meta.displayName().color(rarity.getColor()).decoration(TextDecoration.ITALIC, false));
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    public List<Enchant> getEnchants() {
        return enchants;
    }
}
