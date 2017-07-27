package me.kayoz.bedwars.events;

import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.inventories.GenInfoInv;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
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
 * Created by KaYoz on 7/18/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class GenListInteractEvent implements Listener {

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

        if (inv.getName().startsWith(Chat.format("&6Generators for "))) {

            e.setCancelled(true);
            p.getOpenInventory().close();

            String mapName = ChatColor.stripColor(inv.getName().replace("Generators for ", ""));
            String genName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

            Map map = MapManager.getMap(mapName);
            Generator generator = null;

            for (Generator gen : map.getGens()) {

                if (gen.getName().equals(genName)) {
                    generator = gen;
                    GenInfoInv.create(p, map, generator);
                }
            }

            if (generator == null) {
                Chat.sendPrefixMessage(p, "&cIt seems something went wrong with the selected generator, please try again.");
                return;
            }

            //MapInfoInv.create(p, map);

        }

    }
}
