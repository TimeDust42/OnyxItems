package com.timedust.onyxItems.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;

public class TextUtils {

    public static Component removeItalic(Component component) {
        return component.decoration(TextDecoration.ITALIC, false);
    }

}
