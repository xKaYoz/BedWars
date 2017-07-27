package me.kayoz.bedwars.events;

import me.kayoz.bedwars.commands.subcommands.BedSubCommand;
import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.spawns.Spawn;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by KaYoz on 7/25/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class AddBedEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){

        Location loc = e.getBlock().getLocation();
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if((block.getType() == Material.BED || block.getType() == Material.BED_BLOCK) && p.getItemInHand().hasItemMeta()
                && ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName()).contains("'s Bed")){

            Map map = BedSubCommand.getBedMap().get(p);

            BlockState state = block.getState();

            String colorString = ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName().replace("'s Bed", ""));

            Color color = ColorManager.getColor(colorString);

            for(Spawn spawn : map.getSpawns()){

                if(spawn.getColorRGB() == color.asRGB()){

                    e.setCancelled(true);

                    spawn.setBed(state);

                    Chat.sendPrefixMessage(p, "&eYou have placed a bed for " + ColorManager.getChatColor(color.asRGB()) + colorString + "&e.");

                    p.getInventory().remove(p.getItemInHand());

                    BedSubCommand.getBedMap().remove(p);

                }

            }



        }

    }

}
