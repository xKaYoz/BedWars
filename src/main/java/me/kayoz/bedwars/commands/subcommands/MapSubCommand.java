package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.inventories.AddGenInv;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class MapSubCommand extends SubCommand {

    private String name = "map";
    private String permission = "bedwars.admin.map";

    public MapSubCommand() {
        super("map", "bedwars.admin.map");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: TODO Implement Help Command"));
                return;
            }
            if(args[1].equalsIgnoreCase("create")){
                if(args.length == 3){

                    Files files = new Files();

                    String name = args[2];

                    if(MapManager.getMap(name) != null){
                        p.sendMessage(ChatUtils.formatWithPrefix("&6There already is a map with that name."));
                        return;
                    }

                    Map map = new Map(name);

                    if(files.getFile("maps/" + name, name) == null){

                        File dir = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name);

                        if(!dir.isDirectory()){
                            dir.mkdirs();
                            System.out.println(dir.toString());
                        }

                        files.createFile("maps/" + name, name);
                        System.out.println(dir.toString());
                    }

                    File gens = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name + "/gens");

                    if(!gens.isDirectory()){
                        gens.mkdirs();
                    }
                    File spawns = new File(BedWarsPlugin.getInstance().getDataFolder() + "/maps/" + name + "/spawns");
                    if(!spawns.isDirectory()){
                        spawns.mkdirs();
                    }

                    Generator gen = new Generator("test", p.getLocation(), Material.DIAMOND);

                    files.createFile("maps/" + name + "/gens", gen.getName());

                    YamlConfiguration config = files.getConfig("maps/" + name, name);
                    YamlConfiguration genConfig = files.getConfig("maps/" + name + "/gens", gen.getName());

                    for(java.util.Map.Entry<String, Object> o : gen.serialize().entrySet()){

                        genConfig.set(o.getKey(), o.getValue());

                    }

                    config.set("Map", map.serialize());

                    try {
                        config.save(files.getFile("maps" + File.separator + name, name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        genConfig.save(files.getFile("maps/" + name + "/gens", gen.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    p.sendMessage(ChatUtils.formatWithPrefix("&eYou have created a new map named &6" + map.getName() + "&e."));

                    return;

                } else {
                    p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: TODO Implement Help Command"));
                }
            } else if(args[1].equalsIgnoreCase("generator")){

                if(args.length <= 2){
                    p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: TODO Implement Help Command"));
                    return;
                }

                if(args[2].equalsIgnoreCase("add")){

                    if(args.length == 4){

                        Map map = MapManager.getMap(args[3]);

                        if(map == null){
                            p.sendMessage(ChatUtils.formatWithPrefix("&cThere is not a map with that name!"));
                            return;
                        }

                        //TODO Open an inventory for the player to select a generator to place.

                        AddGenInv.open(p, map);

                        p.sendMessage(ChatUtils.formatWithPrefix("Currently in Development."));
                        return;
                    } else {
                        p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: /bw map generator add <mapName>"));
                        return;
                    }

                } else if(args[2].equalsIgnoreCase("list")){
                    if(args.length == 3){
                        //TODO Give the players a list of all generators and their locations.
                        p.sendMessage(ChatUtils.formatWithPrefix("Currently in Development."));
                        return;
                    } else {
                        p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: /bw map generator remove <mapName>"));
                        return;
                    }
                } else if(args[2].equalsIgnoreCase("remove")){
                    if(args.length == 4){
                        //TODO Give the player a block to remove a generator.
                        p.sendMessage(ChatUtils.formatWithPrefix("Currently in Development."));
                        return;
                    } else {
                        p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: /bw map generator remove <mapName>"));
                        return;
                    }
                }

            } else {
                p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: TODO Implement Help Command"));
            }
        } else {
            sender.sendMessage(ChatUtils.format("&cYou must be a player to execute this command."));
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }
}
