package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.maps.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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

                    String name = args[2];

                    ArrayList<Generator> d = new ArrayList<>();

                    d.add(new Generator(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), Material.DIAMOND));

                    Map map = new Map(name, d);

                    Files files = new Files();

                    if(files.getFile("maps", name) == null){
                        files.createFile("maps", name);
                    }

                    YamlConfiguration config = files.getConfig("maps", name);

                    config.set("Map", map.serialize());

                    try {
                        config.save(files.getFile("maps", name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    p.sendMessage(ChatUtils.formatWithPrefix("&eYou have created a new map called &6" + map.getName() + "&e."));

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
                        //TODO Open an inventory for the player to select a generator to place.
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
