package me.kayoz.bedwars.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KaYoz on 8/7/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public final class Chat {

    private final static int CENTER_PX = 154;

    public static void blankMessage(CommandSender target) {
        target.sendMessage(" ");
    }

    public static void sendColoredMessage(CommandSender target, String message) {
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendPrefixMessage(CommandSender target, String message) {
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6BedWars &8» &7" + message));
    }

    public static void sendColoredMessages(CommandSender target, ArrayList<String> messages) {
        for (String message : messages) {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public static void sendConsolePrefixMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6BedWars&8] &7" + message));
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7" + message));
    }

    public static void sendColoredBroadcast(String message) {
        Bukkit.broadcastMessage(Chat.format(message));
    }

    public static void sendPrefixBroadcast(String message) {
        Bukkit.broadcastMessage(Chat.formatWithPrefix(message));
    }

    public static void sendCenteredMessage(CommandSender player, String message) {
        if (message == null || message.equals(""))
            player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§' || c == '&') {
                previousCode = true;
                continue;
            } else if (previousCode == true) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else
                    isBold = false;
            } else {
                FontEnum.DefaultFontInfo dFI = FontEnum.DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = FontEnum.DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }

    public static String createLine(String color) {
        String line = "&l&m-----------------------------------------------------";
        return ChatColor.translateAlternateColorCodes('&', color + line);
    }

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String formatWithPrefix(String message) {
        return ChatColor.translateAlternateColorCodes('&', "&6BedWars &8» &7" + message);
    }

    public static List<String> format(List<String> list) {
        List<String> Format = new ArrayList();
        if (list == null) return null;
        for (String String : list) {
            Format.add(format(String));
        }
        return Format;
    }

}
