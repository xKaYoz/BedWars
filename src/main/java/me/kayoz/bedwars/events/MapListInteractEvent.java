package me.kayoz.bedwars.events;

import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.inventories.MapInfoInv;
import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.managers.MapManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by KaYoz on 7/17/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class MapListInteractEvent implements Listener {

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

        if (inv.getName().equals(Chat.format("&6Maps"))) {

            e.setCancelled(true);
            p.getOpenInventory().close();

            String mapName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

            Map map = MapManager.getMap(mapName);

            MapInfoInv.create(p, map);

        }

    }
}
