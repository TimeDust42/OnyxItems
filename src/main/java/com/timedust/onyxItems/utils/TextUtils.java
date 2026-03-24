package com.timedust.onyxItems.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;

public class TextUtils {

    public static Component removeItalic(Component component) {
        return component.decoration(TextDecoration.ITALIC, false);
    }

    public static String toRoman(int number) {
        if (number < 1 || number > 3999) return String.valueOf(number);
        StringBuilder sb = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanLetters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                sb.append(romanLetters[i]);
            }
        }
        return sb.toString();
    }
}
