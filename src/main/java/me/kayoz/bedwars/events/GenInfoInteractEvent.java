package me.kayoz.bedwars.events;

import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.inventories.MapInfoInv;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by KaYoz on 7/18/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class GenInfoInteractEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getWhoClicked().getType() != EntityType.PLAYER
                || e.getSlotType() == InventoryType.SlotType.OUTSIDE
                || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();

        if (inv.getName().contains(ChatUtils.format(" Info")) &&
                (inv.getItem(13) != null && inv.getItem(13).getType() != Material.BEACON) &&
                (inv.getItem(30) != null && inv.getItem(30).getType() == Material.EMPTY_MAP) &&
                (inv.getItem(32) != null && inv.getItem(32).getType() == Material.COMPASS)) {

            String mapName = ChatColor.stripColor(inv.getItem(30).getItemMeta().getDisplayName()).replace("Map ", "");

            Map map = MapManager.getMap(mapName);

            if(item.getType() == Material.EMPTY_MAP){

                if(e.getClick() == ClickType.LEFT){
                    p.getOpenInventory().close();
                    MapInfoInv.create(p, map);
                } else {
                    e.setCancelled(true);
                }

            }
            if(item.getType() == Material.COMPASS){
                String genName = ChatColor.stripColor(inv.getName()).replace(" Info", "");
                for(Generator gen : map.getGens()){

                    if(gen.getName().equalsIgnoreCase(genName)){
                        p.teleport(new Location(gen.getWorld(), gen.getX(), gen.getY(), gen.getZ()));
                        p.sendMessage(ChatUtils.format("&eYou have been teleported to the generator " + genName));
                        p.getOpenInventory().close();
                    }

                }
                e.setCancelled(true);
            }

        }

    }
}
