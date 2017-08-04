package me.kayoz.bedwars.events.shops;

import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.users.User;
import me.kayoz.bedwars.utils.users.UserManager;
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
 * Created by KaYoz on 7/30/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class SwordShop implements Listener {

    public static void openShop(Player player) {

        Inventory inv = Bukkit.createInventory(null, 36, Chat.format("&6&lSwords"));

        User u = UserManager.getInstance().getUser(player);

        String canBuy;
        int iron = 0;
        int gold = 0;
        int diamond = 0;
        int emerald = 0;

        for (ItemStack item : player.getInventory().getContents()) {

            if (item != null && item.getType() == Material.IRON_INGOT) {
                iron += item.getAmount();
            }
            if (item != null && item.getType() == Material.GOLD_INGOT) {
                gold += item.getAmount();
            }
            if (item != null && item.getType() == Material.DIAMOND) {
                diamond += item.getAmount();
            }
            if (item != null && item.getType() == Material.EMERALD) {
                emerald += item.getAmount();
            }

        }

        if (iron >= 30) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack stoneSword = ItemBuilder.build(Material.STONE_SWORD, 1, "&eStone Sword", Arrays.asList("&eWill Recieve:", "  &71 Stone Sword", "", "&7Cost: &610 Gold", "", canBuy));

        if (gold >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack ironSword = ItemBuilder.build(Material.IRON_SWORD, 1, "&eIron Sword", Arrays.asList("&eWill Recieve:", "  &71 Iron Sword", "", "&7Cost: &b4 Diamond", "", canBuy));

        if (emerald >= 4) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack diamondSword = ItemBuilder.build(Material.DIAMOND_SWORD, 1, "&eDiamond Sword", Arrays.asList("&eWill Recieve:", "  &71 Diamond Sword", "", "&7Cost: &24 Emerald", "", canBuy));

        ItemStack back = ItemBuilder.build(Material.BARRIER, 1, "&cBack", Arrays.asList("&7Go to the previous menu."));
        inv.setItem(12, stoneSword);
        inv.setItem(13, ironSword);
        inv.setItem(14, diamondSword);
        inv.setItem(22, back);

        player.openInventory(inv);
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

        if (inv.getName().equals(Chat.format("&6&lSwords"))) {

            int iron = 0;
            int gold = 0;
            int diamond = 0;
            int emerald = 0;

            for (ItemStack t : p.getInventory().getContents()) {

                if (t != null && t.getType() == Material.IRON_INGOT) {
                    iron += t.getAmount();
                }
                if (t != null && t.getType() == Material.GOLD_INGOT) {
                    gold += t.getAmount();
                }
                if (t != null && t.getType() == Material.DIAMOND) {
                    diamond += t.getAmount();
                }
                if (t != null && t.getType() == Material.EMERALD) {
                    emerald += t.getAmount();
                }

            }

            if (item.getType() == Material.STONE_SWORD) {

                e.setCancelled(true);

                if (iron >= 30) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 10) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Stone Sword&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");

                }

            } else if (item.getType() == Material.IRON_SWORD) {

                e.setCancelled(true);

                if (gold >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 5) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));

                        Chat.sendColoredMessage(p, "&eYou have purchased an &6Iron Sword&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");

                }
            } else if (item.getType() == Material.DIAMOND_SWORD) {

                e.setCancelled(true);

                if (emerald >= 4) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.EMERALD, 4) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Diamond Sword&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Emerald to purchase this.");

                }
            } else if (item.getType() == Material.BARRIER) {

                e.setCancelled(true);

                p.getOpenInventory().close();

                ShopEvents.openMain(p);
            }
        }
    }
}
