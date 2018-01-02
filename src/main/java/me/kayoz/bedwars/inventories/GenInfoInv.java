package me.kayoz.bedwars.inventories;

import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.objects.Generator;
import me.kayoz.bedwars.objects.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by KaYoz on 7/18/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class GenInfoInv {

    public static void create(Player p, Map map, Generator gen){

        Inventory inv = Bukkit.createInventory(null, 45, Chat.format("&6" + gen.getName() + " Info"));

        Material block;

        if(gen.getDrop() == Material.DIAMOND){
            block = Material.DIAMOND_BLOCK;
        } else if(gen.getDrop() == Material.EMERALD){
            block = Material.EMERALD_BLOCK;
        } else if(gen.getDrop() == Material.IRON_INGOT){
            block = Material.IRON_BLOCK;
        } else {
            block = Material.GOLD_BLOCK;
        }

        ItemStack generator = ItemBuilder.build(block, 1, "&6" + gen.getName(), Arrays.asList(
                "&7Information about the generator &e" + gen.getName() + "&7."
        ));

        ItemStack genMap = ItemBuilder.build(Material.EMPTY_MAP, 1, "&6Map " + map.getName(), Arrays.asList(
                "&7Left click for more info",
                "&7Right click to teleport. &8(&4&lIn Development&8)"
        ));

        ItemStack location = ItemBuilder.build(Material.COMPASS, 1, "&6Location", Arrays.asList(
                "&eWorld&8: &7" + gen.getLoc().getWorld().getName(),
                "&eX&8: &7" + gen.getLoc().getX(),
                "&eY&8: &7" + gen.getLoc().getY(),
                "&eZ&8: &7" + gen.getLoc().getZ(),
                "&7Click to teleport."
        ));

        inv.setItem(13, generator);
        inv.setItem(30, genMap);
        inv.setItem(32, location);

        p.openInventory(inv);

    }
}
