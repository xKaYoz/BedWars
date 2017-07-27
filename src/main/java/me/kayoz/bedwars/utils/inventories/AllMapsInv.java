package me.kayoz.bedwars.utils.inventories;

import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by KaYoz on 7/17/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class AllMapsInv {


    public static void create(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, Chat.format("&6Maps"));

        for (Map map : MapManager.getMaps()) {

            ItemStack item = ItemBuilder.build(Material.EMPTY_MAP, 1, "&6" + map.getName(), Arrays.asList("&7Click for more options."));

            inv.addItem(item);

        }

        p.openInventory(inv);

    }
}
