package me.kayoz.bedwars.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KaYoz on 7/7/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class ChatUtils {

    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String formatWithPrefix(String msg) {
        return format("&7[&cBedWars&7] &e" + msg);
    }

    public static List<String> format(List<String> list) {
        List<String> Format = new ArrayList();
        for (String String : list) {
            Format.add(format(String));
        }
        return Format;
    }
}
