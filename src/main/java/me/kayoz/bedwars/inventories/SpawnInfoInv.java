package me.kayoz.bedwars.inventories;

import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.objects.Spawn;
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

public class SpawnInfoInv {

    public static void create(Player p, Map map, Spawn spawn) {
        Inventory inv = Bukkit.createInventory(null, 45, Chat.format("&6" + spawn.getName() + " Info"));

        ItemStack spawnItem = ItemBuilder.build(Material.BEACON, 1, "&6" + spawn.getName(), Arrays.asList(
                "&7Information about the spawn point &6" + spawn.getName()
        ));

        ItemStack location = ItemBuilder.build(Material.COMPASS, 1, "&6Location", Arrays.asList(
                "&eWorld&8: &7" + spawn.getLoc().getWorld().getName(),
                "&eX&8: &7" + spawn.getLoc().getX(),
                "&eY&8: &7" + spawn.getLoc().getY(),
                "&eZ&8: &7" + spawn.getLoc().getZ(),
                "&7Click to teleport. &8(&4&lIn Development&8)"
        ));

        ItemStack mapItem = ItemBuilder.build(Material.EMPTY_MAP, 1, "&6Map " + map.getName(), Arrays.asList(
                "&7Left click for more info",
                "&7Right click to teleport. &8(&4&lIn Development&8)"
        ));

        inv.setItem(13, spawnItem);
        inv.setItem(30, mapItem);
        inv.setItem(32, location);

        p.openInventory(inv);

    }
}
