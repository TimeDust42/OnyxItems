package com.timedust.onyxItems.items.utils.lore;

import com.timedust.onyxItems.items.utils.rarity.Rarity;
import com.timedust.onyxItems.utils.ItemEnchant;
import com.timedust.onyxItems.utils.ItemAbility;
import com.timedust.onyxItems.utils.ItemAttribute;
import com.timedust.onyxItems.utils.enums.MouseButtons;
import com.timedust.onyxItems.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;

public class LoreBuilder {

    private List<Component> stats = new ArrayList<>();
    private List<Component> enchants = new ArrayList<>();
    private List<Component> description = new ArrayList<>();
    private List<Component> abilities = new ArrayList<>();
    private Component rarityComponent;


    public LoreBuilder() {}

    // --- Характеристики ---
    public LoreBuilder attributes(List<ItemAttribute> statList) {
        if (statList == null) return this;
        statList.forEach(s -> {
            Component stat = s.name().color(NamedTextColor.GRAY)
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(s.getFormattedValue()).color(s.valueColor()));
            stats.add(stat);
        });
        return this;
    }

    // --- Зачарования ---
    public LoreBuilder enchantments(List<ItemEnchant> enchantments) {
        if (enchantments == null ||enchantments.isEmpty()) return this;

        enchantments.forEach((enchant) -> {
            enchants.add(enchant.text().color(NamedTextColor.BLUE));
        });
        return this;
    }

    // --- Описание ---
    public LoreBuilder description(List<? extends Component> lines) {
        if (lines != null) {
            lines.forEach(line -> description.add(TextUtils.removeItalic(line)));
        }
        return this;
    }

    public LoreBuilder description(Component line) {
        if (line != null) description.add(TextUtils.removeItalic(line));
        return this;
    }

    // --- Способности ---
    public LoreBuilder ability(ItemAbility ability) {
        Component header = TextUtils.removeItalic(Component.text("Ability: ").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));

        if (ability.name() == null) return this;
        header = header.append(ability.name());

        if (ability.button() != MouseButtons.NONE) {
            header = header.append(TextUtils.removeItalic(Component.text(" ")));
            header = header.append(TextUtils.removeItalic(ability.button().getName().color(NamedTextColor.YELLOW)));
        }
        abilities.add(header);

        if (ability.description() != null) {
            Component description = ability.description();
            abilities.add(description);
        }

        if (ability.cooldown() != 0) {
            Component cooldown = Component.text("Cooldown: ").color(NamedTextColor.GRAY);
            cooldown = cooldown.append(Component.text(ability.cooldown()).color(NamedTextColor.GRAY));
            abilities.add(cooldown);
        }

        return this;
    }

    // --- Редкость ---
    public LoreBuilder rarity(Rarity rarity) {
        if (rarity != null) {
            this.rarityComponent = TextUtils.removeItalic(rarity.displayName());
        }
        return this;
    }

    // --- Билд ---
    public List<Component> build() {
        List<Component> lore = new ArrayList<>();

        if (!stats.isEmpty()) {
            lore.addAll(stats);
        }
        if (!enchants.isEmpty()) {
            if (!lore.isEmpty()) lore.add(Component.empty());
            lore.addAll(enchants);
        }
        if (!abilities.isEmpty()) {
            if (!lore.isEmpty()) lore.add(Component.empty());
            lore.addAll(abilities);
        }
        if (!description.isEmpty()) {
            if (!lore.isEmpty()) lore.add(Component.empty());
            lore.addAll(description);
        }
        if (rarityComponent != null) {
            if (!lore.isEmpty()) lore.add(Component.empty());
            lore.add(rarityComponent);
        }

        return lore;
    }
}
