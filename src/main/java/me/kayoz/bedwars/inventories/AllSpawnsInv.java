package me.kayoz.bedwars.inventories;

import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.objects.Spawn;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by KaYoz on 7/20/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class AllSpawnsInv {

    public static void create(Player p, Map map) {
        Inventory inv = Bukkit.createInventory(null, 27, Chat.format("&6Spawns for " + map.getName()));

        for (Spawn spawn : map.getSpawns()) {

            ItemStack item = ItemBuilder.build(Material.BEACON, 1, "&6" + spawn.getName(), Arrays.asList("&7Click for more options."));

            inv.addItem(item);

        }

        p.openInventory(inv);
    }
}
