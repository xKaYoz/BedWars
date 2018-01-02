package me.kayoz.bedwars.events.shops;

import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.Chat;
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
 * Created by KaYoz on 8/1/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class ArmorShop implements Listener {

    public static void openShop(Player p) {

        Inventory inv = Bukkit.createInventory(null, 63, Chat.format("&6&lArmor"));

        String canBuy;
        int iron = ShopEvents.getItem(Material.IRON_INGOT, p);
        int gold = ShopEvents.getItem(Material.GOLD_INGOT, p);

        if (iron >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack ihelmet = ItemBuilder.build(Material.IRON_HELMET, 1, "&eIron Helmet", Arrays.asList("&eWill Recieve:", "  &71 Iron Helmet", "", "&7Cost: &65 Gold", "", canBuy));
        ItemStack iboots = ItemBuilder.build(Material.IRON_BOOTS, 1, "&eIron Boots", Arrays.asList("&eWill Recieve:", "  &71 Iron Boots", "", "&7Cost: &65 Gold", "", canBuy));
        ItemStack ichestplate = ItemBuilder.build(Material.IRON_CHESTPLATE, 1, "&eIron Chestplate", Arrays.asList("&eWill Recieve:", "  &71 Iron Chestplate", "", "&7Cost: &610 Gold", "", canBuy));
        ItemStack ileggings = ItemBuilder.build(Material.IRON_LEGGINGS, 1, "&eIron Leggings", Arrays.asList("&eWill Recieve:", "  &71 Iron Leggings", "", "&7Cost: &610 Gold", "", canBuy));


        if (iron >= 15) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Iron to purchase this.");
        }

        ItemStack chelmet = ItemBuilder.build(Material.CHAINMAIL_HELMET, 1, "&eChain Helmet", Arrays.asList("&eWill Recieve:", "  &71 Chain Helmet", "", "&7Cost: &65 Iron", "", canBuy));
        ItemStack cboots = ItemBuilder.build(Material.CHAINMAIL_BOOTS, 1, "&eChain Boots", Arrays.asList("&eWill Recieve:", "  &71 Chain Boots", "", "&7Cost: &65 Iron", "", canBuy));
        ItemStack cchestplate = ItemBuilder.build(Material.CHAINMAIL_CHESTPLATE, 1, "&eChain Chestplate", Arrays.asList("&eWill Recieve:", "  &71 Chain Chestplate", "", "&7Cost: &610 Iron", "", canBuy));
        ItemStack cleggings = ItemBuilder.build(Material.CHAINMAIL_LEGGINGS, 1, "&eChain Leggings", Arrays.asList("&eWill Recieve:", "  &71 Chain Leggings", "", "&7Cost: &610 Iron", "", canBuy));

        if (gold >= 5) {
            canBuy = Chat.format("&aYou can purchase this item.");
        } else {
            canBuy = Chat.format("&cYou do not have enough Gold to purchase this.");
        }

        ItemStack dhelmet = ItemBuilder.build(Material.DIAMOND_HELMET, 1, "&eDiamond Helmet", Arrays.asList("&eWill Recieve:", "  &71 Diamond Helmet", "", "&7Cost: &65 Emerald", "", canBuy));
        ItemStack dboots = ItemBuilder.build(Material.DIAMOND_BOOTS, 1, "&eDiamond Boots", Arrays.asList("&eWill Recieve:", "  &71 Diamond Boots", "", "&7Cost: &65 Emerald", "", canBuy));
        ItemStack dchestplate = ItemBuilder.build(Material.DIAMOND_CHESTPLATE, 1, "&eDiamond Chestplate", Arrays.asList("&eWill Recieve:", "  &71 Diamond Chestplate", "", "&7Cost: &610 Emerald", "", canBuy));
        ItemStack dleggings = ItemBuilder.build(Material.DIAMOND_LEGGINGS, 1, "&eDiamond Leggings", Arrays.asList("&eWill Recieve:", "  &71 Diamond Leggings", "", "&7Cost: &610 Emerald", "", canBuy));

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

        if (inv.getName().equals(Chat.format("&6&lArmor"))) {

            int iron = ShopEvents.getItem(Material.IRON_INGOT, p);
            int gold = ShopEvents.getItem(Material.GOLD_INGOT, p);
            int emerald = ShopEvents.getItem(Material.EMERALD, p);

            if (item.getType() == Material.CHAINMAIL_HELMET) {

                e.setCancelled(true);

                if (iron >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 5) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Chain Helmet&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");
                }

            } else if (item.getType() == Material.CHAINMAIL_BOOTS) {

                e.setCancelled(true);

                if (iron >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 5) == 0) {

                        p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

                        Chat.sendColoredMessage(p, "&eYou have purchased &6Chain Boots&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");
                }

            } else if (item.getType() == Material.CHAINMAIL_LEGGINGS) {

                e.setCancelled(true);

                if (iron >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 10) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_LEGGINGS));

                        Chat.sendColoredMessage(p, "&eYou have purchased &6Chain Leggings&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");
                }

            } else if (item.getType() == Material.CHAINMAIL_CHESTPLATE) {

                e.setCancelled(true);

                if (iron >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.IRON_INGOT, 10) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_CHESTPLATE));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Chain Chestplate&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Iron to purchase this.");
                }

            } else if (item.getType() == Material.IRON_HELMET) {

                e.setCancelled(true);

                if (gold >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 5) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Iron Helmet&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");
                }

            } else if (item.getType() == Material.IRON_BOOTS) {

                e.setCancelled(true);

                if (gold >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 5) == 0) {

                        p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

                        Chat.sendColoredMessage(p, "&eYou have purchased &6Iron Boots&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");
                }

            } else if (item.getType() == Material.IRON_LEGGINGS) {

                e.setCancelled(true);

                if (gold >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 10) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.IRON_LEGGINGS));

                        Chat.sendColoredMessage(p, "&eYou have purchased &6Iron Leggings&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");
                }

            } else if (item.getType() == Material.IRON_CHESTPLATE) {

                e.setCancelled(true);

                if (gold >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.GOLD_INGOT, 10) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.IRON_CHESTPLATE));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Iron Chestplate&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Gold to purchase this.");
                }

            }  else if (item.getType() == Material.DIAMOND_HELMET) {

                e.setCancelled(true);

                if (emerald >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.EMERALD, 5) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Diamond Helmet&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Emeralds to purchase this.");
                }

            } else if (item.getType() == Material.DIAMOND_BOOTS) {

                e.setCancelled(true);

                if (emerald >= 5) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.EMERALD, 5) == 0) {

                        p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                        Chat.sendColoredMessage(p, "&eYou have purchased &6Diamond Boots&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Emeralds to purchase this.");
                }

            } else if (item.getType() == Material.DIAMOND_LEGGINGS) {

                e.setCancelled(true);

                if (emerald >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.EMERALD, 10) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_LEGGINGS));

                        Chat.sendColoredMessage(p, "&eYou have purchased &6Diamond Leggings&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Emeralds to purchase this.");
                }

            } else if (item.getType() == Material.DIAMOND_CHESTPLATE) {

                e.setCancelled(true);

                if (emerald >= 10) {

                    int next = p.getInventory().firstEmpty();

                    if (next == -1) {
                        Chat.sendColoredMessage(p, "&cYou do not have an open slot in your inventory.");
                        return;
                    }

                    if (ShopEvents.removeItems(p.getInventory(), Material.EMERALD, 10) == 0) {

                        p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_CHESTPLATE));

                        Chat.sendColoredMessage(p, "&eYou have purchased a &6Diamond Chestplate&e.");

                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                        p.updateInventory();
                    } else {
                        Chat.sendColoredMessage(p, "&cError");
                    }

                } else {
                    Chat.sendColoredMessage(p, "&cYou do not have enough Emeralds to purchase this.");
                }

            } else if (item.getType() == Material.BARRIER) {

                e.setCancelled(true);

                p.getOpenInventory().close();

                ShopEvents.openMain(p);
            }
        }
    }
}
