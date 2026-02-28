package com.timedust.onyxItems.items;

import com.timedust.onyxItems.OnyxItems;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.ToolComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ItemsBuilder {

    private static final NamespacedKey ITEM_ID_KEY = new NamespacedKey(OnyxItems.getInstance(), "custom_id");

    private final JavaPlugin plugin;
    private final String item_id;
    private final ItemStack item;
    private final ItemMeta meta;

    private final MiniMessage mm = MiniMessage.miniMessage();

    public ItemsBuilder(JavaPlugin plugin, String item_id, Material material) {
        this.plugin = plugin;

        this.item_id = item_id;
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemsBuilder setDisplayName(String name) {
        meta.displayName(mm.deserialize(name));
        return this;
    }

    public ItemsBuilder setLore(List<String> lore) {
        meta.lore(lore.stream()
                .map(text -> mm.deserialize(text)
                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE))
                .toList());
        return this;
    }

    public ItemsBuilder setCustomModelData(int modelData) {
        meta.setCustomModelData(modelData);
        return this;
    }

    public ItemsBuilder setMaxDurability(int durability) {
        if (meta instanceof Damageable damageable) {
            damageable.setMaxDamage(durability);
        }
        return this;
    }

    public ItemsBuilder hideAttributes() {
        NamespacedKey key = new NamespacedKey(plugin, "hide_stats");
        AttributeModifier modifier = new AttributeModifier(
                key,
                0.0,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.ANY
        );

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

        return this;
    }

    public ItemsBuilder createTool(Tag<Material> blockTag, MiningTier tier, float speed) {
        ToolComponent tool = meta.getTool();

        tool.setDefaultMiningSpeed(1.0f);
        tool.addRule(blockTag, speed, true);

        for (MiningTier higherTier : tier.getHigherTiers()) {
            tool.addRule(higherTier.getTag(), speed, false);
        }

        tool.setDamagePerBlock(1);
        meta.setTool(tool);

        return this;
    }

    public ItemsBuilder createWeapon(double damage, double speed) {
        NamespacedKey damageKey = new NamespacedKey(plugin, "attack_damage");
        NamespacedKey speedKey = new NamespacedKey(plugin, "attack_speed");

        AttributeModifier damageModifier = new AttributeModifier(
                damageKey,
                damage,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
        );

        AttributeModifier speedModifier = new AttributeModifier(
                speedKey,
                speed,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
        );

        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifier);

        return this;
    }

    @Deprecated
    public ItemsBuilder setMiningSpeed(double speed) {
        NamespacedKey key = new NamespacedKey(plugin, "block_break_speed");

        AttributeModifier modifier = new AttributeModifier(
                key,
                speed,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.MAINHAND
        );

        meta.removeAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED);
        meta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, modifier);
        return this;
    }

    @Deprecated
    public ItemsBuilder setMiningLevel(int level) {
        NamespacedKey key = new NamespacedKey(plugin, "mining_level");

        meta.getPersistentDataContainer().set(
                key,
                PersistentDataType.INTEGER,
                level
        );

        return this;
    }

    public ItemStack build() {
        meta.getPersistentDataContainer().set(
                ITEM_ID_KEY,
                PersistentDataType.STRING,
                item_id
        );
        item.setItemMeta(meta);
        return item;
    }
}
