package me.kayoz.bedwars;

import me.kayoz.bedwars.utils.Files;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Created by KaYoz on 7/11/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Configuration {

    private static Files files = new Files();
    private static YamlConfiguration config = files.getConfig("config");

    public static int MAX_PLAYERS = config.getInt("Max Players");
    public static boolean TEAM = config.getBoolean("Teams");
    public static int PLAYERS_PER_TEAM = config.getInt("Players Per Team");
    public static int TEAMS = config.getInt("Amount of Teams");

}
