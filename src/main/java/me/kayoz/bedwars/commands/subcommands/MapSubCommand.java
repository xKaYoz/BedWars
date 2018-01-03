package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.inventories.AllMapsInv;
import me.kayoz.bedwars.managers.MapManager;
import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.Files;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class MapSubCommand extends SubCommand {

    @Getter
    private String name = "map";
    @Getter
    private String permission = "bedwars.admin.map";

    public MapSubCommand() {
        super("map", "bedwars.admin.map");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 3 && args[1].equalsIgnoreCase("create")) {

                Files files = new Files();

                String name = args[2];

                if (MapManager.getMap(name) != null) {
                    Chat.sendPrefixMessage(p, "&6There already is a map with that name.");
                    return;
                }

                Map map = new Map(name, p.getName(), p.getLocation());

                if (files.getFile("maps/" + name, name) == null) {

                    File dir = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name);

                    if (!dir.isDirectory()) {
                        dir.mkdirs();
                        System.out.println(dir.toString());
                    }

                    files.createFile("maps/" + name, name);
                    System.out.println(dir.toString());
                }

                File gens = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name + "/gens");

                if (!gens.isDirectory()) {
                    gens.mkdirs();
                }
                File spawns = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name + "/spawns");
                if (!spawns.isDirectory()) {
                    spawns.mkdirs();
                }
                File shops = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name + "/shops");
                if (!shops.isDirectory()) {
                    shops.mkdirs();
                }

                YamlConfiguration config = files.getConfig("maps/" + name, name);

                for (java.util.Map.Entry<String, Object> o : map.serialize().entrySet()) {
                    config.set(o.getKey(), o.getValue());
                }

                try {
                    config.save(files.getFile("maps" + File.separator + name, name));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Chat.sendPrefixMessage(p, "&eYou have created a new map named &6" + map.getName() + "&e.");
                return;
            } else if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {

                String name = args[2];

                Map map = MapManager.getMap(name);

                if (map == null) {
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

                File dir = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name);
                File[] contents = dir.listFiles();
                if (contents != null) {
                    for (File f : contents) {

                        if (f.isDirectory()) {
                            File[] fc = f.listFiles();

                            if (fc == null) {
                                f.delete();
                            } else {
                                for (File g : fc) {
                                    g.delete();
                                }
                                f.delete();
                            }

                        } else {
                            f.delete();
                        }
                    }
                    dir.delete();
                } else {
                    dir.delete();
                }
                MapManager.unregister(map);

                Chat.sendPrefixMessage(p, "&eYou have deleted the map &6" + name + "&e.");

            } else if (args.length == 2 && args[1].equalsIgnoreCase("list")) {

                AllMapsInv.create(p);

            } else if (args.length == 3 && args[1].equalsIgnoreCase("tp")) {

                String name = args[2];

                Map map = MapManager.getMap(name);

                if (map == null) {
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

                p.teleport(map.getLoc());

                Chat.sendPrefixMessage(p, "&eYou have been teleported to the map &6" + map.getName() + "&e.");

            } else {
                Chat.sendColoredMessage(p, Chat.createLine("&8"));
                Chat.sendColoredMessage(p, "&6Map Help &7(Page 1/1)");
                Chat.sendColoredMessage(p, "  &e/bw map help &8- &7Displays this help menu.");
                Chat.sendColoredMessage(p, "  &e/bw map create <name> &8- &7Create a map with the given name.");
                Chat.sendColoredMessage(p, "  &e/bw map remove <name> &8- &7Delete the selected map.");
                Chat.sendColoredMessage(p, "  &e/bw map list &8- &7A list of all the maps that have been created.");
                Chat.sendColoredMessage(p, "  &e/bw map tp <map> &8- &7Teleport to the selected map.");
                Chat.sendColoredMessage(p, Chat.createLine("&8"));
            }
        } else {
            Chat.sendPrefixMessage(sender, "&cYou must be a player to execute this command.");
        }
    }
}
