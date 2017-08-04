package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.inventories.AllSpawnsInv;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import me.kayoz.bedwars.utils.spawns.Spawn;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Created by KaYoz on 7/19/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class SpawnSubCommand extends SubCommand {

    @Getter
    private String name = "spawn";
    @Getter
    private String permission = "bedwars.admin.spawn";

    public SpawnSubCommand() {
        super("spawn", "bedwars.admin.spawn");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                Chat.sendColoredMessage(p, Chat.createLine("&8"));
                Chat.sendColoredMessage(p, "&6Spawn Help &7(Page 1/1)");
                Chat.sendColoredMessage(p, "  &e/bw spawn help &8- &7Displays this help menu.");
                Chat.sendColoredMessage(p, "  &e/bw spawn create <name> <color> &8- &7Create a spawn on the given map.");
                Chat.sendColoredMessage(p, "  &e/bw spawn remove <name> &8- &7Select a spawn position to remove.");
                Chat.sendColoredMessage(p, "  &e/bw spawn list <name> &8- &7A list of all the maps that have been created.");
                Chat.sendColoredMessage(p, Chat.createLine("&8"));
                return;
            } else if(args[1].equalsIgnoreCase("create") && args.length == 4){

                Map map = MapManager.getMap(args[2]);

                if(map == null){
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

                String colorStr = args[3];
                Color color = ColorManager.getColor(colorStr);

                if(color == null){
                    Chat.sendPrefixMessage(p, "&cIncorrect Color Type, please refer to this link for the colors. TODO ADD LINK");
                    return;
                }

                for(Spawn spawn : map.getSpawns()){
                    if(spawn.getColor() == color || spawn.getColorRGB() == color.asRGB()){
                        Chat.sendPrefixMessage(p, "&cThere is already a spawnpoint for the color " + colorStr.toLowerCase());
                        return;
                    }
                }

                Spawn spawn = new Spawn(String.valueOf(map.getSpawns().size()), color, map, p.getLocation());

                map.addSpawn(spawn);

                Files files = new Files();
                files.createFile("maps/" + map.getName() + "/spawns", String.valueOf(map.getSpawns().size()));
                YamlConfiguration config = files.getConfig("maps/" + map.getName() + "/spawns", String.valueOf(map.getSpawns().size()));

                for(java.util.Map.Entry<String, Object> o : spawn.serialize().entrySet()){
                    config.set(o.getKey(), o.getValue());
                }

                try {
                    config.save(files.getFile("maps/" + map.getName() + "/spawns", String.valueOf(map.getSpawns().size())));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Chat.sendPrefixMessage(p, "&eYou have created a new &6Spawn Point&e for the map &6" + map.getName() + "&e.");

            } else if(args[1].equalsIgnoreCase("remove") && args.length == 3){



            } else if(args[1].equalsIgnoreCase("list") && args.length == 3){

                Map map = MapManager.getMap(args[2]);

                if(map == null){
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

                AllSpawnsInv.create(p, map);

            }
        }

    }
}
