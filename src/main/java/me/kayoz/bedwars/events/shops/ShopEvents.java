package me.kayoz.bedwars.events.shops;

import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.users.User;
import me.kayoz.bedwars.utils.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by KaYoz on 7/27/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class ShopEvents implements Listener {

    public static void openMain(Player player) {

        Inventory inv = Bukkit.createInventory(null, 27, Chat.format("&6&lShop"));

        User u = UserManager.getInstance().getUser(player);

        ItemStack blocks = ItemBuilder.build(Material.WOOL, 1, ColorManager.getColorID(u.getTeam().getColorRGB()), "&eBlocks", Arrays.asList("&7Purchase Blocks"));
        ItemStack weapons = ItemBuilder.build(Material.DIAMOND_SWORD, 1, "&eSwords", Arrays.asList("&7Purchase Swords"));
        ItemStack ranged = ItemBuilder.build(Material.BOW, 1, "&eRanged", Arrays.asList("&7Purchase Ranged Items"));
        ItemStack food = ItemBuilder.build(Material.COOKED_BEEF, 1, "&eFood", Arrays.asList("&7Purchase Food"));
        ItemStack armor = ItemBuilder.build(Material.DIAMOND_CHESTPLATE, 1, "&eArmor", Arrays.asList("&7Purchase Armor"));

        inv.setItem(11, blocks);
        inv.setItem(12, weapons);
        inv.setItem(13, ranged);
        inv.setItem(14, food);
        inv.setItem(15, armor);

        player.openInventory(inv);

    }

    public static int removeItems(Inventory inventory, Material type, int amount) {

        if (type == null || inventory == null)
            return -1;
        if (amount <= 0)
            return -1;

        if (amount == Integer.MAX_VALUE) {
            inventory.remove(type);
            return 0;
        }

        inventory.removeItem(new ItemStack(type, amount));
        return 0;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (e.getEntity().getType() == EntityType.VILLAGER && e.getDamager() instanceof Player) {

            e.setCancelled(true);

            Player p = (Player) e.getDamager();

            openMain(p);

        }

    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (e.getRightClicked().getType() == EntityType.VILLAGER) {

            e.setCancelled(true);

            openMain(p);

        }

    }

    @EventHandler
    public void onSelect(InventoryClickEvent e) {

        if (e.getWhoClicked().getType() != EntityType.PLAYER
                || e.getSlotType() == InventoryType.SlotType.OUTSIDE
                || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        User u = UserManager.getInstance().getUser(p);

        if (inv.getName().equals(Chat.format("&6&lShop"))) {

            e.setCancelled(true);

            if (item.getType() == Material.WOOL) {
                p.getOpenInventory().close();
                BlockShop.openShop(p);
            } else if (item.getType() == Material.DIAMOND_SWORD) {
                p.getOpenInventory().close();
                SwordShop.openShop(p);
            } else if (item.getType() == Material.BOW) {
                p.getOpenInventory().close();
                RangedShop.openShop(p);
            } else if (item.getType() == Material.COOKED_BEEF) {
                p.getOpenInventory().close();
                FoodShop.openShop(p);
            } else if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                p.getOpenInventory().close();
                ArmorShop.openShop(p);
            }

        }
    }

}
