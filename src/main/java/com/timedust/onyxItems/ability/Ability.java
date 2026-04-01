package com.timedust.onyxItems.ability;

import com.timedust.onyxItems.items.AbstractItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public interface Ability {
   boolean execute(Player player, AbstractItem item, ConfigurationSection section);
}
