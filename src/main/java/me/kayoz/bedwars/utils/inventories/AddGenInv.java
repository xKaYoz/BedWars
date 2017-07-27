package me.kayoz.bedwars.utils.inventories;

import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.maps.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by KaYoz on 7/13/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class AddGenInv {

    public static void open(Player p, Map map) {

        Inventory inv = Bukkit.createInventory(null, 9, Chat.format("&eGenerator: " + map.getName()));

        ItemStack diamond = ItemBuilder.build(Material.DIAMOND_BLOCK, 1, "&b&lDiamond Generator", Arrays.asList("&7Select this to place a diamond generator."));
        ItemStack iron = ItemBuilder.build(Material.IRON_BLOCK, 1, "&f&lIron Generator", Arrays.asList("&7Select this to place an iron generator."));
        ItemStack gold = ItemBuilder.build(Material.GOLD_BLOCK, 1, "&6&lGold Generator", Arrays.asList("&7Select this to place a gold generator."));
        ItemStack emerald = ItemBuilder.build(Material.EMERALD_BLOCK, 1, "&a&lEmerald Generator", Arrays.asList("&7Select this to place an emerald generator."));
        ItemStack spacer = ItemBuilder.build(Material.STAINED_GLASS_PANE, 1, 7, " ", Arrays.asList(""));

        inv.setItem(0, spacer);
        inv.setItem(2, spacer);
        inv.setItem(4, spacer);
        inv.setItem(6, spacer);
        inv.setItem(8, spacer);

        inv.setItem(1, diamond);
        inv.setItem(3, iron);
        inv.setItem(5, gold);
        inv.setItem(7, emerald);

        p.openInventory(inv);

    }
}
