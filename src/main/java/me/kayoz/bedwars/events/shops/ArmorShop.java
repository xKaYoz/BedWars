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
 * Created by KaYoz on 8/1/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class ArmorShop implements Listener {

    public static void openShop(Player player) {

        Inventory inv = Bukkit.createInventory(null, 63, Chat.format("&6&lArmor"));

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

        if (iron >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack ihelmet = ItemBuilder.build(Material.IRON_HELMET, 1, "&eIron Helmet", Arrays.asList("&eWill Recieve:", "  &71 Iron Helmet", "", "&7Cost: &65 Gold", "", canBuy));
        ItemStack iboots = ItemBuilder.build(Material.IRON_BOOTS, 1, "&eIron Boots", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &65 Gold", "", canBuy));
        ItemStack ichestplate = ItemBuilder.build(Material.IRON_CHESTPLATE, 1, "&eIron Chestplate", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &610 Gold", "", canBuy));
        ItemStack ileggings = ItemBuilder.build(Material.IRON_LEGGINGS, 1, "&eIron Leggings", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &610 Gold", "", canBuy));


        if (iron >= 15) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack chelmet = ItemBuilder.build(Material.CHAINMAIL_HELMET, 1, "&eChain Helmet", Arrays.asList("&eWill Recieve:", "  &71 Iron Helmet", "", "&7Cost: &65 Iron", "", canBuy));
        ItemStack cboots = ItemBuilder.build(Material.CHAINMAIL_BOOTS, 1, "&eChain Boots", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &65 Iron", "", canBuy));
        ItemStack cchestplate = ItemBuilder.build(Material.CHAINMAIL_CHESTPLATE, 1, "&eChain Chestplate", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &610 Iron", "", canBuy));
        ItemStack cleggings = ItemBuilder.build(Material.CHAINMAIL_LEGGINGS, 1, "&eChain Leggings", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &610 Iron", "", canBuy));

        if (gold >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack dhelmet = ItemBuilder.build(Material.DIAMOND_HELMET, 1, "&eDiamond Helmet", Arrays.asList("&eWill Recieve:", "  &71 Iron Helmet", "", "&7Cost: &65 Gold", "", canBuy));
        ItemStack dboots = ItemBuilder.build(Material.DIAMOND_BOOTS, 1, "&eDiamond Boots", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &65 Gold", "", canBuy));
        ItemStack dchestplate = ItemBuilder.build(Material.DIAMOND_CHESTPLATE, 1, "&eDiamond Chestplate", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &610 Gold", "", canBuy));
        ItemStack dleggings = ItemBuilder.build(Material.DIAMOND_LEGGINGS, 1, "&eDiamond Leggings", Arrays.asList("&eWill Recieve:", "  &74 Carrots", "", "&7Cost: &610 Gold", "", canBuy));

        ItemStack back = ItemBuilder.build(Material.BARRIER, 1, "&cBack", Arrays.asList("&7Go to the previous menu."));
        //IRON
        inv.setItem(11, ihelmet);
        inv.setItem(20, ichestplate);
        inv.setItem(29, ileggings);
        inv.setItem(38, iboots);
        //CHAIN
        inv.setItem(13, chelmet);
        inv.setItem(22, cchestplate);
        inv.setItem(31, cleggings);
        inv.setItem(40, cboots);
        //DIAMOND
        inv.setItem(15, dhelmet);
        inv.setItem(24, dchestplate);
        inv.setItem(33, dleggings);
        inv.setItem(42, dboots);
        //BACK
        inv.setItem(49, back);

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

        if (inv.getName().equals(Chat.format("&6&lRanged"))) {

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

            if (item.getType() == Material.CARROT) {

                e.setCancelled(true);

                if (iron >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 5) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.CARROT, 4));

                        Chat.sendColoredMessage(p, "&eYou have purchased &64 Carrots&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");

                }

            } else if (item.getType() == Material.COOKED_BEEF) {

                e.setCancelled(true);

                if (iron >= 15) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 15) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 4));

                        Chat.sendColoredMessage(p, "&eYou have purchased &64 Steak&e.");

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

                if (gold >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 5) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Golden Apple&e.");

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
