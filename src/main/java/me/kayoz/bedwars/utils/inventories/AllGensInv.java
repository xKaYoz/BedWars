package me.kayoz.bedwars.utils.inventories;

import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.maps.Map;
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

public class AllGensInv {

    public static void create(Player p, Map map){

        Inventory inv = Bukkit.createInventory(null, 27, ChatUtils.format("&6Generators"));

        for(Generator gen : map.getGens()){

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

            ItemStack item = ItemBuilder.build(block, 1, ChatUtils.format("&6" + gen.getName()), Arrays.asList(
                    "&7Click for more options."));

            inv.addItem(item);
        }

        p.openInventory(inv);

    }
}
