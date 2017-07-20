package me.kayoz.bedwars.events;

import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.inventories.SpawnInfoInv;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import me.kayoz.bedwars.utils.spawns.Spawn;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by KaYoz on 7/20/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class SpawnListInteractEvent  implements Listener {

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

        if (inv.getName().startsWith(ChatUtils.format("&6Spawns for ")) && item.getType() == Material.BEACON) {

            e.setCancelled(true);
            p.getOpenInventory().close();

            String mapName = ChatColor.stripColor(inv.getName().replace("Spawns for ", ""));
            String spawnName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

            Map map = MapManager.getMap(mapName);
            Spawn spawn = null;

            for(Spawn s : map.getSpawns()){

                if(s.getName().equalsIgnoreCase(spawnName)){
                    spawn = s;
                    SpawnInfoInv.create(p, map, spawn);
                }

            }



        }

    }
}
