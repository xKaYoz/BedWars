package me.kayoz.bedwars.utils;

import lombok.Getter;
import org.bukkit.ChatColor;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public enum Color {

    WHITE("Cloud", 15, ChatColor.WHITE),
    RED("Rose", 1, ChatColor.RED),
    PURPLE("Royal", 5, ChatColor.DARK_PURPLE),
    AQUA("Aqua", 12, ChatColor.AQUA),
    PINK("Pink", 9, ChatColor.LIGHT_PURPLE),
    ORANGE("Amber", 8, ChatColor.GOLD),
    GREEN("Forest", 2, ChatColor.GREEN),
    YELLOW("Dandelion", 11, ChatColor.YELLOW);

    @Getter
    private String name;
    @Getter
    private int id;
    @Getter
    private ChatColor color;

    Color(String name, int id, ChatColor color){
        this.name = name;
        this.id = id;
        this.color = color;
    }

}
