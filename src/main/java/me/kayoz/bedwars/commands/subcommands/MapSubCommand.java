package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.inventories.AddGenInv;
import me.kayoz.bedwars.utils.inventories.AllGensInv;
import me.kayoz.bedwars.utils.inventories.AllMapsInv;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
                p.sendMessage(ChatUtils.format("&8&l&m--------------------------------------------"));
                p.sendMessage(ChatUtils.format("&6Map Help &7(Page 1/1)"));
                p.sendMessage(ChatUtils.format("  &e/bw map help &8- &7Displays this help menu."));
                p.sendMessage(ChatUtils.format("  &e/bw map create <name> &8- &7Create a map with the given name."));
                p.sendMessage(ChatUtils.format("  &e/bw map list &8- &7A list of all the maps that have been created."));
                p.sendMessage(ChatUtils.format("&8&l&m--------------------------------------------"));
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

                    Map map = new Map(p.getName(), name);

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

                    YamlConfiguration config = files.getConfig("maps/" + name, name);

                    for(java.util.Map.Entry<String, Object> o : map.serialize().entrySet()){
                        config.set(o.getKey(), o.getValue());
                    }

                    try {
                        config.save(files.getFile("maps" + File.separator + name, name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.sendMessage(ChatUtils.formatWithPrefix("&eYou have created a new map named &6" + map.getName() + "&e."));
                    return;
                } else {
                    p.sendMessage(ChatUtils.formatWithPrefix("&cIncorrect Usage: /bw map create <name>"));
                }
            } else if(args.length == 2 && args[1].equalsIgnoreCase("list")){

                AllMapsInv.create(p);

            } else {
                p.sendMessage(ChatUtils.format("&8&l&m--------------------------------------------"));
                p.sendMessage(ChatUtils.format("&6Map Help &7(Page 1/1)"));
                p.sendMessage(ChatUtils.format("  &e/bw map help &8- &7Displays this help menu."));
                p.sendMessage(ChatUtils.format("  &e/bw map create <name> &8- &7Create a map with the given name."));
                p.sendMessage(ChatUtils.format("  &e/bw map list &8- &7A list of all the maps that have been created."));
                p.sendMessage(ChatUtils.format("&8&l&m--------------------------------------------"));
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
