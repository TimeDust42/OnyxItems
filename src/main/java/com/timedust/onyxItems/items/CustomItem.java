package com.timedust.onyxItems.items;

import com.timedust.onyxItems.OnyxItems;
import com.timedust.onyxItems.config.ConfigReader;
import com.timedust.onyxItems.items.utils.lore.LoreBuilder;
import com.timedust.onyxItems.utils.ItemEnchant;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomItem extends AbstractItem {

    private final OnyxItems plugin =  OnyxItems.getInstance();

    private final ConfigurationSection config;

    public CustomItem(ConfigurationSection section) {
        super(
                section.getName(),
                ConfigReader.parseString(section, "display-name", "Unnamed item"),
                ConfigReader.parseMaterial(section, "material"),
                ConfigReader.parseRarity(section)
        );
        this.config = section;
    }

    @Override
    protected void applyExtra(ItemBuilder builder) {
        if (config.contains("custom-model-data")) {
            builder.customModelData(config.getInt("custom-model-data"));
        }
        if (config.contains("durability")) {
            builder.durability(config.getInt("durability"));
        }
        if (config.getBoolean("unbreakable", false)) {
            builder.unbreakable(true);
        }
        if (config.getBoolean("glow", false)) {
            builder.setGlowing(true);
        }
        if (config.contains("max-stack")) {
            builder.maxStackSize(config.getInt("max-stack", 64));
        }
        if (config.contains("flags")) {
            List<ItemFlag> flags = new ArrayList<>();
            List<String> rawFlags = config.getStringList("flags");
            for (String str : rawFlags) {
                try {
                    flags.add(ItemFlag.valueOf(str.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Unknown flag in config: " + str);
                }
            }
            if (!flags.isEmpty()) builder.applyFlags(flags);
        }
        if (config.contains("attributes")) {
            List<String> rawAttributes = config.getStringList("attributes");
            if (rawAttributes.isEmpty()) return;

            for (String entry : rawAttributes) {
                String[] parts = entry.trim().split("\\s+");
                if (parts.length < 3) {
                    plugin.getLogger().warning("Invalid attribute format: " + entry);
                    continue;
                }

                try {
                    NamespacedKey attrKey = NamespacedKey.minecraft(parts[0].toLowerCase());
                    Attribute attribute = Registry.ATTRIBUTE.get(attrKey);
                    if (attribute == null) {
                        plugin.getLogger().warning("Unknown attribute: " + parts[0]);
                        continue;
                    }

                    double value = Double.parseDouble(parts[1]);
                    AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(parts[2].toUpperCase());

                    EquipmentSlotGroup slot = (parts.length > 3) ? EquipmentSlotGroup.getByName(parts[3].toLowerCase()) : null;

                    builder.addAttribute(attribute, value, operation, slot);
                } catch (Exception e) {
                    plugin.getLogger().warning("Error parsing attribute: " + entry + " (" + e.getMessage() + ")");
                }
            }
        }
        if (config.contains("enchants")) {
            ConfigurationSection enchants = config.getConfigurationSection("enchants");
            if (enchants == null || enchants.getKeys(false).isEmpty()) return;

            Registry<Enchantment> enchantmentRegistry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);

            for (String key : enchants.getKeys(false)) {
                int level = enchants.getInt(key);

                NamespacedKey nsk = NamespacedKey.fromString(key.toLowerCase());
                if (nsk == null) continue;

                Enchantment enchantment = enchantmentRegistry.get(nsk);

                if (enchantment != null) {
                    builder.enchant(new ItemEnchant(enchantment, level));
                }
            }
        }
    }

    @Override
    protected void addLore(LoreBuilder lb) {
        if (config.contains("attributes")) {
            lb.attributes(ConfigReader.parseAttributes(config));
        }
        if (config.contains("description")) {
            lb.description(ConfigReader.parseList(config, "description"));
        }
        if (config.contains("ability")) {
            lb.ability(Objects.requireNonNull(ConfigReader.parseAbility(config)));
        }
    }

    @Override
    public ConfigurationSection getAbilitySettings() {
        return config.getConfigurationSection("ability");
    }

}
