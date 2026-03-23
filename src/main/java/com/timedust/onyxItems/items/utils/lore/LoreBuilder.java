package com.timedust.onyxItems.items.utils.lore;

import com.timedust.onyxItems.items.utils.rarity.Rarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;

public class LoreBuilder {

    private List<Component> stats = new ArrayList<>();
    private List<Component> description = new ArrayList<>();
    private List<Component> abilities = new ArrayList<>();
    private Component rarityComponent;


    public LoreBuilder() {}

    // --- Редкость ---
    public LoreBuilder rarity(Rarity rarity) {
        if (rarity != null) {
            this.rarityComponent = removeItalic(rarity.getDisplayName());
        }
        return this;
    }

    // --- Описание ---
    public LoreBuilder description(List<? extends Component> lines) {
        if (lines != null) {
            lines.forEach(line -> description.add(removeItalic(line)));
        }
        return this;
    }

    public LoreBuilder description(Component line) {
        if (line != null) description.add(removeItalic(line));
        return this;
    }

    // --- Способности ---
    public LoreBuilder ability(String name, List<? extends Component> lines) {
        if (name == null) return this;
        abilities.add(removeItalic(Component.text("Ability: " + name, NamedTextColor.GOLD)));
        if (lines != null) {
            lines.forEach(line -> abilities.add(removeItalic(line.color(NamedTextColor.GRAY))));
        }
        return this;
    }

    public LoreBuilder ability(String name, Component line) {
        return ability(name, List.of(line));
    }

    // --- Билд ---
    public List<Component> build() {
        List<Component> lore = new ArrayList<>();

        if (!stats.isEmpty()) {
            lore.addAll(stats);
        }

        if (!description.isEmpty()) {
            if (!lore.isEmpty()) lore.add(Component.empty());
            lore.addAll(description);
        }

        if (!abilities.isEmpty()) {
            if (!lore.isEmpty()) lore.add(Component.empty());
            lore.addAll(abilities);
        }

        if (rarityComponent != null) {
            if (!lore.isEmpty()) lore.add(Component.empty());
            lore.add(rarityComponent);
        }

        return lore;
    }

    private static Component removeItalic(Component component) {
        return component.decoration(TextDecoration.ITALIC, false);
    }
}
