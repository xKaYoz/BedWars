package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/24/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class BedSubCommand extends SubCommand {

    @Getter
    private String name = "bed";
    @Getter
    private String permission = "bedwars.admin.bed";

    @Getter
    private static HashMap<Player, Map> bedMap = new HashMap<>();

    public BedSubCommand() {
        super("bed", "bedwars.admin.bed");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(args.length != 4){
                return;
            }

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

            int i = p.getInventory().firstEmpty();

            if(i == -1){
                Chat.sendPrefixMessage(p,"&cThere is not an empty slot in your inventory." );
                return;
            }

            ItemStack bed = ItemBuilder.build(Material.BED, 1, ColorManager.getChatColor(color.asRGB()) + "&l" + colorStr + "'s Bed", Arrays.asList("&7Place this bed at the location", "&7You want &6" + args[3] + "&7's bed to spawn."));

            Chat.sendPrefixMessage(p, "&eYou have 20 seconds to place the bed before it is removed from your inventory.");

            if(bedMap.containsKey(p)){
                Chat.sendPrefixMessage(p, "&cYou have not placed the previous bed.");
                return;
            }

            p.getInventory().setItem(i, bed);

            bedMap.put(p, map);

            Bukkit.getScheduler().runTaskLater(BedWarsPlugin.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(p != null && bedMap.containsKey(p)){

                        bedMap.remove(p);

                        Chat.sendPrefixMessage(p, "&cThe bed has been removed from your inventory.");

                        p.getInventory().remove(bed);

                    }
                }
            }, 20 * 20);

        }

    }
}
