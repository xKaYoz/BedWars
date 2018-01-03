package me.kayoz.bedwars.events.shops;

import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by KaYoz on 8/1/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class RangedShop implements Listener {

    public static void openShop(Player p) {

        Inventory inv = Bukkit.createInventory(null, 36, Chat.format("&6&lRanged"));

        String canBuy;
        int iron = ShopEvents.getItem(Material.IRON_INGOT, p);
        int gold = ShopEvents.getItem(Material.GOLD_INGOT, p);

        if (gold >= 3) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack arrow = ItemBuilder.build(Material.ARROW, 1, "&eArrow", Arrays.asList("&eWill Recieve:", "  &74 Arrows", "", "&7Cost: &63 Gold", "", canBuy));

        if (gold >= 10) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack bow = ItemBuilder.build(Material.BOW, 1, "&eBow", Arrays.asList("&eWill Recieve:", "  &71 Bow", "", "&7Cost: &610 Gold", "", canBuy));

        if (iron >= 20) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack egg = ItemBuilder.build(Material.EGG, 1, "&eEgg", Arrays.asList("&eWill Recieve:", "  &716 Eggs", "", "&7Cost: &f20 Iron", "", canBuy));

        ItemStack back = ItemBuilder.build(Material.BARRIER, 1, "&cBack", Arrays.asList("&7Go to the previous menu."));
        inv.setItem(12, arrow);
        inv.setItem(13, bow);
        inv.setItem(14, egg);
        inv.setItem(22, back);

        p.openInventory(inv);
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

        if (inv.getName().equals(Chat.format("&6&lRanged"))) {

            int iron = ShopEvents.getItem(Material.IRON_INGOT, p);
            int gold = ShopEvents.getItem(Material.GOLD_INGOT, p);

            if (item.getType() == Material.BOW) {

                e.setCancelled(true);

                if (gold >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 10) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.BOW));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Bow&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");

                }

            } else if (item.getType() == Material.EGG) {

                e.setCancelled(true);

                if (iron >= 20) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.DIAMOND, 4) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.EGG, 16));

                        Chat.sendColoredMessage(p, "&eYou have purchased &616 Eggs&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");

                }
            } else if (item.getType() == Material.ARROW) {

                e.setCancelled(true);

                if (gold >= 3) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 3) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.ARROW, 4));

                        Chat.sendColoredMessage(p, "&eYou have purchased &64 Arrows&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");

                }
            } else if (item.getType() == Material.BARRIER) {

                e.setCancelled(true);

                p.getOpenInventory().close();

                ShopEvents.openMain(p);
            }
        }
    }

}
