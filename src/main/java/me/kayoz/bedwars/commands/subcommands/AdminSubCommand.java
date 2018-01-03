package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.managers.MapManager;
import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.Files;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by KaYoz on 7/27/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class AdminSubCommand extends SubCommand {

    @Getter
    private String name = "admin";
    @Getter
    private String permission = "bedwars.admin.lobby";

    public AdminSubCommand() {
        super("admin", "bedwars.admin");
    }

    public static void check(CommandSender sender) {

        boolean allowed = true;
        ArrayList<String> errors = new ArrayList<>();

        Files files = new Files();
        YamlConfiguration config = files.getConfig("config");

        Chat.sendPrefixMessage(sender, "&eDetermining if the server matches the criteria for Admin Mode to be enabled...");

        if (files.getFile("lobby") == null) {
            allowed = false;
            errors.add("&cA lobby has not yet been setup.");
            errors.add(Chat.createLine("&8"));
        }

        if (MapManager.getMaps().size() == 0) {
            allowed = false;
            errors.add("&cA map has not yet been setup.");
            errors.add(Chat.createLine("&8"));
        } else {

            for (Map map : MapManager.getMaps()) {

                if (map.getSpawns().size() == 0) {
                    allowed = false;
                    errors.add("&cSpawns have not been setup for the map " + map.getName() + ".");
                    errors.add(Chat.createLine("&8"));
                } else if (map.getSpawns().size() < config.getInt("Max Players")) {
                    allowed = false;
                    errors.add("&cThere are not enough spawns in map " + map.getName() + ". There are " + map.getSpawns().size() + " spawns, but there must be " + config.getInt("Max Players"));
                    errors.add(Chat.createLine("&8"));
                } else if (map.getGenerators().size() == 0) {
                    allowed = false;
                    errors.add("&cGenerators have not been setup for the map " + map.getName());
                    errors.add(Chat.createLine("&8"));
                }

            }

        }

        if (allowed) {
            Chat.sendPrefixMessage(sender, "&eThe server has &a&lPASSED&e the checks and Admin Mode has been disabled. Have fun!");
            config.set("Admin Mode", false);
            try {
                config.save(files.getFile("config"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        } else {
            Chat.sendPrefixMessage(sender, "&eThe server has &c&lFAILED&e the checks and Admin Mode will remain enabled. Please read below to see what needs to be changed!");
            Chat.sendColoredMessage(sender, Chat.createLine("&8"));
            Chat.sendColoredMessages(sender, errors);
            Chat.sendPrefixMessage(sender, "&eWhen these changes have been fixed, you can retry to see if the server passes.");
            return;
        }

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            Files files = new Files();
            YamlConfiguration config = files.getConfig("config");

            if (args.length == 2 && args[1].equalsIgnoreCase("enable")) {

                config.set("Admin Mode", true);

                Chat.sendPrefixMessage(p, "&cAdmin Mode has been enabled. Noone will be able to join the server and no game will start.");

                try {
                    config.save(files.getFile("config"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;

            } else if (args.length == 2 && args[1].equalsIgnoreCase("disable")) {

                check(p);

            } else if (args.length == 2 && args[1].equalsIgnoreCase("toggle")) {
                boolean adminMode = config.getBoolean("Admin Mode");

                if (adminMode) p.performCommand("bw adminmode disable");
                else p.performCommand("bw adminmode enable");

            } else {
                p.sendMessage(Chat.createLine("&8"));
                Chat.sendColoredMessage(p, "&6AdminMode Help &7(Page 1/1)");
                Chat.sendColoredMessage(p, "   &e/bw admin enable &8- &7Enables Admin Mode.");
                Chat.sendColoredMessage(p, "   &e/bw admin disable &8- &7Disables Admin Mode.");
                Chat.sendColoredMessage(p, "   &e/bw admin toggle &8- &7Toggles Admin Mode.");
                Chat.sendColoredMessage(p, "   &e/bw admin requirements &8- &7Informs you of what needs to be finished to disable Admin Mode.");
                p.sendMessage(Chat.createLine("&8"));
                Chat.sendColoredMessage(p, "&6Admin mode is " + config.getBoolean("Admin Mode"));
                p.sendMessage(Chat.createLine("&8"));
            }

        }

    }
}
