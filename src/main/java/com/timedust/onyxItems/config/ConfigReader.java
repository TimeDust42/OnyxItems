package com.timedust.onyxItems.config;

import com.timedust.onyxItems.items.utils.rarity.Rarity;
import com.timedust.onyxItems.utils.ItemAbility;
import com.timedust.onyxItems.utils.ItemAttribute;
import com.timedust.onyxItems.utils.enums.ItemAttributes;
import com.timedust.onyxItems.utils.enums.MouseButtons;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ConfigReader {

    private static final MiniMessage MM = MiniMessage.miniMessage();

    public static Component parseString(ConfigurationSection config, String path, String def) {
        String str = config.getString(path, def);
        if (str == null) {
            return null;
        }
        return MM.deserialize(str);
    }

    public static List<Component> parseList(ConfigurationSection config, String path) {
        List<String> strList = config.getStringList(path);
        return strList.stream()
                .map(MM::deserialize)
                .toList();
    }

    public static Material parseMaterial(ConfigurationSection config, String path) {
        String str = config.getString(path);
        if (str == null) return Material.STONE;

        Material mat = Material.matchMaterial(str);
        return mat != null ? mat : Material.BARRIER;
    }

    public static Rarity parseRarity(ConfigurationSection config) {
        String str = config.getString("rarity", "COMMON");
        Rarity rarity = Rarity.getById(str.toLowerCase());
        return rarity != null ? rarity : Rarity.COMMON;
    }

    public static List<ItemAttribute> parseAttributes(ConfigurationSection config) {
        return config.getStringList("attributes").stream()
                .map(rawAttr -> {
                    String[] parts = rawAttr.split(" ");
                    ItemAttributes attr = ItemAttributes.fromId(parts[0]);
                    double value = Double.parseDouble(parts[1]);
                    AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(parts[2]);
                    return new ItemAttribute(attr.displayName(), value, operation, attr.valueColor());
                })
                .toList();
    }

    public static ItemAbility parseAbility(ConfigurationSection config) {
        ConfigurationSection abilityConfig = config.getConfigurationSection("ability");
        if (abilityConfig == null) {
            return null;
        }

        Component name = parseString(abilityConfig, "display-name", "name");
        MouseButtons buttons = MouseButtons.valueOf(abilityConfig.getString("buttons", "RIGHT"));
        double cooldown = abilityConfig.getDouble("cooldown", 1.0);
        Component description = parseString(abilityConfig, "description", null);

        return new ItemAbility(name, buttons, cooldown, description);
    }
}
