package com.timedust.onyxItems.ability.ablitityList;

import com.timedust.onyxItems.ability.Ability;
import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.utils.TeleportUtils;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BlinkAbility implements Ability {

    @Override
    public boolean execute(Player player, AbstractItem item, ConfigurationSection section) {
        double distance = section.getDouble("distance", 1.0);
        World world = player.getWorld();

        world.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        TeleportUtils.teleport(player, distance, true);

        return true;
    }
}
