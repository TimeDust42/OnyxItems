package com.timedust.onyxItems.utils;

import com.timedust.onyxItems.utils.enums.MouseButtons;
import net.kyori.adventure.text.Component;

public record ItemAbility(Component name, MouseButtons button, double cooldown, Component description) {

}
