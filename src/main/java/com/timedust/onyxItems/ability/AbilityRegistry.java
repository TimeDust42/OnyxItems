package com.timedust.onyxItems.ability;

import com.timedust.onyxItems.OnyxItems;
import com.timedust.onyxItems.ability.ablitityList.BlinkAbility;

import java.util.HashMap;
import java.util.Map;

public class AbilityRegistry {

    private static final Map<String, Ability> ABILITIES = new HashMap<>();

    public static void registerAbility(String id, Ability ability) {
        ABILITIES.put(id, ability);
    }

    public static Ability getById(String id) {
        return ABILITIES.get(id);
    }

    public static Map<String, Ability> getAbilities() {
        return ABILITIES;
    }

    public static void clear() {
        ABILITIES.clear();
    }

    public static void registerAll() {
        registerAbility("BLINK", new BlinkAbility());

        OnyxItems.getInstance().getLogger().info("Loaded " + ABILITIES.size() + " custom abilities from configs.");
    }
}
