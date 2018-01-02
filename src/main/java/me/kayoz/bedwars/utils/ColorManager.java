package me.kayoz.bedwars.utils;

import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.objects.Spawn;
import me.kayoz.bedwars.objects.Team;
import me.kayoz.bedwars.managers.TeamManager;
import me.kayoz.bedwars.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by KaYoz on 7/23/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class ColorManager {

    private static ArrayList<Color> colors = new ArrayList<>();

    public static Color nextColor() {
        Color color = colors.get(colors.size() - 1);
        for (Team team : TeamManager.getTeams()) {
            if (team.getColor() == color) {
                nextColor();
            }
        }

        colors.remove(color);

        return color;
    }

    public static void addColors(Map map) {
        for (Spawn spawn : map.getSpawns()) {
            colors.add(spawn.getColor());
        }
    }

    public static String getColorName(int rgb) {
        if (rgb == Color.AQUA.asRGB()) {
            return "Aqua";
        } else if (rgb == Color.BLUE.asRGB()) {
            return "Blue";
        } else if (rgb == Color.ORANGE.asRGB()) {
            return "Gold";
        } else if (rgb == Color.YELLOW.asRGB()) {
            return "Yellow";
        } else if (rgb == Color.PURPLE.asRGB()) {
            return "Purple";
        } else if (rgb == Color.GREEN.asRGB()) {
            return "Green";
        } else if (rgb == Color.WHITE.asRGB()) {
            return "White";
        } else if (rgb == Color.GRAY.asRGB()) {
            return "Gray";
        } else if (rgb == Color.SILVER.asRGB()) {
            return "Silver";
        } else {
            return "";
        }
    }

    public static String getChatColor(int rgb) {
        if (rgb == Color.AQUA.asRGB()) {
            return "&b";
        } else if (rgb == Color.BLUE.asRGB()) {
            return "&9";
        } else if (rgb == Color.ORANGE.asRGB()) {
            return "&6";
        } else if (rgb == Color.YELLOW.asRGB()) {
            return "&e";
        } else if (rgb == Color.PURPLE.asRGB()) {
            return "&5";
        } else if (rgb == Color.GREEN.asRGB()) {
            return "&2";
        } else if (rgb == Color.WHITE.asRGB()) {
            return "&f";
        } else if (rgb == Color.GRAY.asRGB()) {
            return "&8";
        } else if (rgb == Color.SILVER.asRGB()) {
            return "&7";
        } else {
            return "";
        }
    }

    public static void setNameColor(Player p, Color color) {

        Scoreboard sb = p.getScoreboard();

        if (sb == null) {
            sb = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        org.bukkit.scoreboard.Team t = sb.registerNewTeam(UUID.randomUUID().toString().substring(0, 16));

        t.setPrefix(Chat.format(getChatColor(color.asRGB())));

        t.addEntry(p.getName());


        p.setScoreboard(sb);

    }

    public static void setNameColor(Player p) {

        Scoreboard sb = p.getScoreboard();

        if (sb == null) {
            sb = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        for (Team team : TeamManager.getTeams()) {

            org.bukkit.scoreboard.Team t = sb.registerNewTeam(UUID.randomUUID().toString().substring(0, 16));

            t.setPrefix(Chat.format(getChatColor(team.getColor().asRGB())));

            for (User user : team.getMembers()) {

                t.addEntry(user.getPlayer().getName());

            }

        }

        p.setScoreboard(sb);

    }

    public static Color getColor(String s) {

        if (s.equalsIgnoreCase("aqua") || s.equalsIgnoreCase("a") || s.equalsIgnoreCase("lightblue") || s.equalsIgnoreCase("light_blue")) {
            return Color.AQUA;
        } else if (s.equalsIgnoreCase("blue") || s.equals("1") || s.equalsIgnoreCase("darkblue") || s.equalsIgnoreCase("dark_blue")) {
            return Color.BLUE;
        } else if (s.equalsIgnoreCase("orange") || s.equalsIgnoreCase("gold") || s.equals("6")) {
            return Color.ORANGE;
        } else if (s.equalsIgnoreCase("yellow") || s.equalsIgnoreCase("e")) {
            return Color.YELLOW;
        } else if (s.equalsIgnoreCase("purple") || s.equals("5") || s.equalsIgnoreCase("darkpurple") || s.equalsIgnoreCase("dark_purple")) {
            return Color.PURPLE;
        } else if (s.equalsIgnoreCase("green") || s.equals("2") || s.equalsIgnoreCase("dark_green") || s.equalsIgnoreCase("darkgreen")) {
            return Color.GREEN;
        } else if (s.equalsIgnoreCase("white") || s.equalsIgnoreCase("f")) {
            return Color.WHITE;
        } else if (s.equalsIgnoreCase("gray") || s.equals("8") || s.equalsIgnoreCase("darkgray") || s.equalsIgnoreCase("darkgrey")
                || s.equalsIgnoreCase("dark_gray") || s.equalsIgnoreCase("dark_grey")) {
            return Color.GRAY;
        } else if (s.equalsIgnoreCase("silver") || s.equals("7") || s.equalsIgnoreCase("lightgray") || s.equalsIgnoreCase("lightgrey")
                || s.equalsIgnoreCase("light_gray") || s.equalsIgnoreCase("light_grey")) {
            return Color.SILVER;
        }
        return null;
    }

    public static short getColorID(int rgb) {
        if (rgb == Color.AQUA.asRGB()) {
            return DyeColor.CYAN.getData();
        } else if (rgb == Color.BLUE.asRGB()) {
            return DyeColor.BLUE.getData();
        } else if (rgb == Color.ORANGE.asRGB()) {
            return DyeColor.ORANGE.getData();
        } else if (rgb == Color.YELLOW.asRGB()) {
            return DyeColor.YELLOW.getData();
        } else if (rgb == Color.PURPLE.asRGB()) {
            return DyeColor.PURPLE.getData();
        } else if (rgb == Color.GREEN.asRGB()) {
            return DyeColor.GREEN.getData();
        } else if (rgb == Color.WHITE.asRGB()) {
            return DyeColor.WHITE.getData();
        } else if (rgb == Color.GRAY.asRGB()) {
            return DyeColor.GRAY.getData();
        } else if (rgb == Color.SILVER.asRGB()) {
            return DyeColor.SILVER.getData();
        } else {
            return 0;
        }
    }

}
