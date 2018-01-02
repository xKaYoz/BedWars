package me.kayoz.bedwars.events.shops;

import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.objects.Team;
import me.kayoz.bedwars.objects.User;
import me.kayoz.bedwars.managers.UserManager;
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

public class BlockShop implements Listener {

    public static void openShop(Player player) {

        Inventory inv = Bukkit.createInventory(null, 36, Chat.format("&6&lBlocks"));

        User u = UserManager.getUser(player);

        String canBuy;
        int iron = 0;
        int gold = 0;
        int emerald = 0;

        for (ItemStack item : player.getInventory().getContents()) {

            if (item != null && item.getType() == Material.IRON_INGOT) {
                iron += item.getAmount();
            }
            if (item != null && item.getType() == Material.GOLD_INGOT) {
                gold += item.getAmount();
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

        ItemStack wool = ItemBuilder.build(Material.WOOL, 1, ColorManager.getColorID(u.getTeam().getColorRGB()), "&eWool", Arrays.asList("&eWill Recieve:", "  &78 Wool", "", "&7Cost: &f5 Iron", "", canBuy));

        if (iron >= 20) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack cobble = ItemBuilder.build(Material.COBBLESTONE, 1, "&eCobblestone", Arrays.asList("&eWill Recieve:", "  &712 Cobblestone", "", "&7Cost: &f20 Iron", "", canBuy));

        if (gold >= 4) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack wood = ItemBuilder.build(Material.WOOD, 1, "&eWood", Arrays.asList("&eWill Recieve:", "  &78 Wood", "", "&7Cost: &64 Gold", "", canBuy));

        if (gold >= 10) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack sand = ItemBuilder.build(Material.SAND, 1, "&eSand", Arrays.asList("&eWill Recieve:", "  &712 Sand", "", "&7Cost: &610 Gold", "", canBuy));

        if (emerald >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Emerald to purchase this.");
        }

        ItemStack obsidian = ItemBuilder.build(Material.OBSIDIAN, 1, "&eObsidian", Arrays.asList("&eWill Recieve:", "  &75 Obsidian", "", "&7Cost: &25 Emerald", "", canBuy));

        ItemStack back = ItemBuilder.build(Material.BARRIER, 1, "&cBack", Arrays.asList("&7Go to the previous menu."));

        inv.setItem(11, wool);
        inv.setItem(12, cobble);
        inv.setItem(13, wood);
        inv.setItem(14, sand);
        inv.setItem(15, obsidian);
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
        User u = UserManager.getUser(p);

        if (inv.getName().equals(Chat.format("&6&lBlocks"))) {

            int iron = 0;
            int gold = 0;
            int diamond = 0;
            int emerald = 0;

            for (ItemStack t : p.getInventory().getContents()) {

                if (t != null && t.getType() == Material.IRON_INGOT) {
                    iron += t.getAmount();
                }
                if (t != null && t.getType() == Material.DIAMOND) {
                    diamond += t.getAmount();
                }
                if (t != null && t.getType() == Material.GOLD_INGOT) {
                    gold += t.getAmount();
                }
                if (t != null && t.getType() == Material.EMERALD) {
                    emerald += t.getAmount();
                }

            }

            if (item.getType() == Material.WOOL) {

                e.setCancelled(true);

                if (iron >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 5) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.WOOL, 8, ColorManager.getColorID(u.getTeam().getColorRGB())));

                        Chat.sendColoredMessage(p, "&eYou have purchased &616 Wool&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");

                }

            } else if (item.getType() == Material.COBBLESTONE) {

                e.setCancelled(true);

                if (iron >= 20) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 20) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 12));

                        Chat.sendColoredMessage(p, "&eYou have purchased &612 Cobblestone&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {

                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");

                }

            } else if (item.getType() == Material.WOOD) {

                e.setCancelled(true);

                if (gold >= 4) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 4) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.WOOD, 8));

                        Chat.sendColoredMessage(p, "&eYou have purchased &68 Wood&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");
                }

            } else if (item.getType() == Material.SAND) {

                e.setCancelled(true);

                if (gold >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 10) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.SAND, 12));

                        Chat.sendColoredMessage(p, "&eYou have purchased &612 Sand&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");
                }

            } else if (item.getType() == Material.OBSIDIAN) {

                e.setCancelled(true);

                if (emerald >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.EMERALD, 5) == 0) {

                        p.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 5));

                        Chat.sendColoredMessage(p, "&eYou have purchased &65 Obsidian&e.");

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
