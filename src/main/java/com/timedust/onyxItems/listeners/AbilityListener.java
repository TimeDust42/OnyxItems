package com.timedust.onyxItems.listeners;

import com.timedust.onyxItems.Keys;
import com.timedust.onyxItems.ability.Ability;
import com.timedust.onyxItems.ability.AbilityRegistry;
import com.timedust.onyxItems.items.AbstractItem;
import com.timedust.onyxItems.items.ItemRegistry;
import com.timedust.onyxItems.utils.CooldownManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class AbilityListener implements Listener {

    private final ItemRegistry itemRegistry;

    public AbilityListener(ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @EventHandler
    public void onAbility(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;

        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        String itemId = meta.getPersistentDataContainer().get(Keys.ITEM_ID_KEY, PersistentDataType.STRING);
        if (itemId == null) return;

        AbstractItem customItem = itemRegistry.getItem(itemId);
        if (customItem == null) return;

        ConfigurationSection abilitySettings = customItem.getAbilitySettings();
        if (abilitySettings == null) return;

        String abilityId = abilitySettings.getString("id");
        if (abilityId == null) return;

        Ability ability = AbilityRegistry.getById(abilityId);

        if (ability != null) {
            ConfigurationSection settings = abilitySettings.getConfigurationSection("settings");
            UUID uuid = event.getPlayer().getUniqueId();

            if (CooldownManager.hasCooldown(uuid, abilityId)) {
                double timeLeft = CooldownManager.getCooldown(uuid, abilityId);

                String message = String.format("§cПодождите еще %.2f сек.", timeLeft);

                event.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
                return;
            }

            boolean success = ability.execute(event.getPlayer(), customItem, settings);

            if (success) {
                double cooldown = abilitySettings.getDouble("cooldown");
                if (cooldown > 0) {
                    CooldownManager.setCooldown(uuid, abilityId, cooldown);
                }
            }
        }
    }
}
