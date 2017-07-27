package me.kayoz.bedwars.utils.inventories;

import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
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

public class MapInfoInv {

    public static void create(Player p, Map map) {
        Inventory inv = Bukkit.createInventory(null, 45, Chat.format("&6" + map.getName() + " Info"));

        int ironGens = 0;
        int goldGens = 0;
        int diamondGens = 0;
        int emeraldGens = 0;

        for (Generator gen : map.getGens()) {
            if (gen.getDrop() == Material.IRON_INGOT) {
                ironGens++;
            } else if (gen.getDrop() == Material.GOLD_INGOT) {
                goldGens++;
            } else if (gen.getDrop() == Material.DIAMOND) {
                diamondGens++;
            } else if (gen.getDrop() == Material.EMERALD) {
                emeraldGens++;
            }
        }

        ItemStack mapItem = ItemBuilder.build(Material.EMPTY_MAP, 1, "&6" + map.getName(), Arrays.asList("&7Information about the map " + map.getName()));
        ItemStack gens = ItemBuilder.build(Material.DIAMOND, 1, "&6Generators", Arrays.asList(
                "&f&lIron Generators&8: &7" + ironGens,
                "&6&lGold Generators&8: &7" + goldGens,
                "&b&lDiamond Generators&8: &7" + diamondGens,
                "&a&lEmerald Generators&8: &7" + emeraldGens));

        ItemStack createdBy = ItemBuilder.build(Material.BOOK, 1, "&6Created By " + map.getCreator(), Arrays.asList(
                ""
        ));

        ItemStack spawn = ItemBuilder.build(Material.BEACON, 1, "&6Spawn Points", Arrays.asList(
                "&7There are &6" + map.getSpawns().size() + " &7spawn points."
        ));

        ItemStack location = ItemBuilder.build(Material.COMPASS, 1, "&6Location", Arrays.asList("" +
                "&7Click to Teleport. &7(&4&lIn Development&7)"));

        inv.setItem(13, mapItem);
        inv.setItem(28, gens);
        inv.setItem(30, spawn);
        inv.setItem(32, createdBy);
        inv.setItem(34, location);

        p.openInventory(inv);
    }

}
