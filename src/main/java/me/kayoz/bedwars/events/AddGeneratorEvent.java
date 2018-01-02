package me.kayoz.bedwars.events;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.objects.Generator;
import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.managers.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/16/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class AddGeneratorEvent implements Listener {

    public HashMap<Player, Map> genMap = new HashMap<>();

    @EventHandler
    public void onInventorySelect(InventoryClickEvent e) {

        if (e.getWhoClicked().getType() != EntityType.PLAYER
                || e.getSlotType() == InventoryType.SlotType.OUTSIDE
                || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack item = e.getCurrentItem();

        if (inv.getName().contains(Chat.format("&eGenerator: "))) {

            if (item.getType() == Material.DIAMOND_BLOCK && item.getItemMeta().getDisplayName().equalsIgnoreCase(Chat.format("&b&lDiamond Generator"))) {
                e.setCancelled(true);
                p.getOpenInventory().close();
                int i = p.getInventory().firstEmpty();

                Map map = MapManager.getMap(ChatColor.stripColor(inv.getName().replace("Generator: ", "")));

                if (map == null) {
                    Chat.sendPrefixMessage(p, "The map could not be found. Please report this to the developer.");
                    return;
                }

                if (i == -1) {
                    Chat.sendPrefixMessage(p, "&cThere is not an empty slot in your inventory.");
                    return;
                } else {
                    ItemStack gen = ItemBuilder.build(Material.DIAMOND_BLOCK, 1, "&b&lDiamond Generator",
                            Arrays.asList("&7Place this item on the ground", "&7where you want this type of generator."));

                    if (genMap.containsKey(p)) {
                        Chat.sendPrefixMessage(p, "&cYou have not placed the previous generator.");
                        return;
                    }

                    p.getInventory().setItem(i, gen);

                    genMap.put(p, map);

                    Bukkit.getScheduler().runTaskLater(BedWarsPlugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            if (p != null && genMap.containsKey(p)) {

                                genMap.remove(p);

                                Chat.sendPrefixMessage(p, "&cThe generator has been removed from your inventory.");

                                p.getInventory().remove(gen);

                            }
                        }
                    }, 20 * 20);

                    Chat.sendPrefixMessage(p, "&eYou have been given a &b&lDiamond Generator&e. " +
                            "Place the item where you would like a generator to be. You have 20 seconds to place it.");
                }

            } else if (item.getType() == Material.IRON_BLOCK && item.getItemMeta().getDisplayName().equalsIgnoreCase(Chat.format("&f&lIron Generator"))) {
                e.setCancelled(true);
                p.getOpenInventory().close();
                int i = p.getInventory().firstEmpty();

                Map map = MapManager.getMap(ChatColor.stripColor(inv.getName().replace("Generator: ", "")));

                if (map == null) {
                    Chat.sendPrefixMessage(p, "The map could not be found. Please report this to the developer.");
                    return;
                }

                if (i == -1) {
                    Chat.sendPrefixMessage(p, "&cThere is not an empty slot in your inventory.");
                    return;
                } else {
                    ItemStack gen = ItemBuilder.build(Material.IRON_BLOCK, 1, "&f&lIron Generator",
                            Arrays.asList("&7Place this item on the ground", "&7where you want this type of generator."));

                    if (genMap.containsKey(p)) {
                        Chat.sendPrefixMessage(p, "&cYou have not placed the previous generator.");
                        return;
                    }

                    p.getInventory().setItem(i, gen);

                    genMap.put(p, map);

                    Bukkit.getScheduler().runTaskLater(BedWarsPlugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            if (p != null && genMap.containsKey(p)) {

                                genMap.remove(p);

                                Chat.sendPrefixMessage(p, "&cThe generator has been removed from your inventory.");

                                p.getInventory().remove(gen);

                            }
                        }
                    }, 20 * 20);

                    Chat.sendPrefixMessage(p, "&eYou have been given an &f&lIron Generator&e. " +
                            "Place the item where you would like a generator to be. You have 20 seconds to place it.");
                }
            } else if (item.getType() == Material.GOLD_BLOCK && item.getItemMeta().getDisplayName().equalsIgnoreCase(Chat.format("&6&lGold Generator"))) {
                e.setCancelled(true);
                p.getOpenInventory().close();
                int i = p.getInventory().firstEmpty();

                Map map = MapManager.getMap(ChatColor.stripColor(inv.getName().replace("Generator: ", "")));

                if (map == null) {
                    Chat.sendPrefixMessage(p, "The map could not be found. Please report this to the developer.");
                    return;
                }

                if (i == -1) {
                    Chat.sendPrefixMessage(p, "&cThere is not an empty slot in your inventory.");
                    return;
                } else {
                    ItemStack gen = ItemBuilder.build(Material.GOLD_BLOCK, 1, "&6&lGold Generator",
                            Arrays.asList("&7Place this item on the ground", "&7where you want this type of generator."));

                    if (genMap.containsKey(p)) {
                        Chat.sendPrefixMessage(p, "&cYou have not placed the previous generator.");
                        return;
                    }

                    p.getInventory().setItem(i, gen);

                    genMap.put(p, map);

                    Bukkit.getScheduler().runTaskLater(BedWarsPlugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            if (p != null && genMap.containsKey(p)) {

                                genMap.remove(p);

                                Chat.sendPrefixMessage(p, "&cThe generator has been removed from your inventory.");

                                p.getInventory().remove(gen);

                            }
                        }
                    }, 20 * 20);

                    Chat.sendPrefixMessage(p, "&eYou have been given a &6&lGold Generator&e. " +
                            "Place the item where you would like a generator to be. You have 20 seconds to place it.");
                }
            } else if (item.getType() == Material.EMERALD_BLOCK && item.getItemMeta().getDisplayName().equalsIgnoreCase(Chat.format("&a&lEmerald Generator"))) {
                e.setCancelled(true);
                p.getOpenInventory().close();
                int i = p.getInventory().firstEmpty();

                Map map = MapManager.getMap(ChatColor.stripColor(inv.getName().replace("Generator: ", "")));

                if (map == null) {
                    Chat.sendPrefixMessage(p, "The map could not be found. Please report this to the developer.");
                    return;
                }

                if (i == -1) {
                    Chat.sendPrefixMessage(p, "&cThere is not an empty slot in your inventory.");
                    return;
                } else {
                    ItemStack gen = ItemBuilder.build(Material.EMERALD_BLOCK, 1, "&a&lEmerald Generator",
                            Arrays.asList("&7Place this item on the ground", "&7where you want this type of generator."));

                    if (genMap.containsKey(p)) {
                        Chat.sendPrefixMessage(p, "&cYou have not placed the previous generator.");
                        return;
                    }

                    p.getInventory().setItem(i, gen);

                    genMap.put(p, map);

                    Bukkit.getScheduler().runTaskLater(BedWarsPlugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            if (p != null && genMap.containsKey(p)) {

                                genMap.remove(p);

                                Chat.sendPrefixMessage(p, "&cThe generator has been removed from your inventory.");

                                p.getInventory().remove(gen);

                            }
                        }
                    }, 20 * 20);

                    Chat.sendPrefixMessage(p, "&eYou have been given an &a&lEmerald Generator&e. " +
                            "Place the item where you would like a generator to be. You have 20 seconds to place it.");
                }

            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        Location loc = e.getBlock().getLocation();
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if (block.getType() == Material.DIAMOND_BLOCK && p.getItemInHand().hasItemMeta() &&
                p.getItemInHand().getItemMeta().getDisplayName().equals(Chat.format("&b&lDiamond Generator"))) {
            e.setCancelled(true);

            Map map = genMap.get(p);

            Generator gen = new Generator(String.valueOf(map.getGenerators().size()), loc, Material.DIAMOND);

            map.addGenerator(gen);

            Chat.sendPrefixMessage(p, "&eA &b&lDiamond Generator&e has been placed at: &6X:&e" + loc.getX() + " &6Y:&e" + loc.getY() + " &6Z:&e" + +loc.getZ() + " for map " + map.getName());

            Files files = new Files();
            files.createFile("maps/" + map.getName() + "/gens", gen.getName());
            YamlConfiguration genConfig = files.getConfig("maps/" + map.getName() + "/gens", gen.getName());

            for (java.util.Map.Entry<String, Object> o : gen.serialize().entrySet()) {
                genConfig.set(o.getKey(), o.getValue());
            }

            try {
                genConfig.save(files.getFile("maps/" + map.getName() + "/gens", gen.getName()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            p.getInventory().remove(p.getItemInHand());

            genMap.remove(p);

        } else if (block.getType() == Material.IRON_BLOCK && p.getItemInHand().hasItemMeta() &&
                p.getItemInHand().getItemMeta().getDisplayName().equals(Chat.format("&f&lIron Generator"))) {
            e.setCancelled(true);

            Map map = genMap.get(p);

            Generator gen = new Generator(String.valueOf(map.getGenerators().size()), loc, Material.IRON_INGOT);

            map.addGenerator(gen);

            Chat.sendPrefixMessage(p, "&eAn &f&lIron Generator&e has been placed at: &6X:&e" + loc.getX() + " &6Y:&e" + loc.getY() + " &6Z:&e" + +loc.getZ() + " for map " + map.getName());

            Files files = new Files();
            files.createFile("maps/" + map.getName() + "/gens", gen.getName());
            YamlConfiguration genConfig = files.getConfig("maps/" + map.getName() + "/gens", gen.getName());

            for (java.util.Map.Entry<String, Object> o : gen.serialize().entrySet()) {
                genConfig.set(o.getKey(), o.getValue());
            }

            try {
                genConfig.save(files.getFile("maps/" + map.getName() + "/gens", gen.getName()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            p.getInventory().remove(p.getItemInHand());

            genMap.remove(p);

        } else if (block.getType() == Material.GOLD_BLOCK && p.getItemInHand().hasItemMeta() &&
                p.getItemInHand().getItemMeta().getDisplayName().equals(Chat.format("&6&lGold Generator"))) {
            e.setCancelled(true);

            Map map = genMap.get(p);

            Generator gen = new Generator(String.valueOf(map.getGenerators().size()), loc, Material.GOLD_INGOT);

            map.addGenerator(gen);

            Chat.sendPrefixMessage(p, "&eA &6&lGold Generator&e has been placed at: &6X:&e" + loc.getX() + " &6Y:&e" + loc.getY() + " &6Z:&e" + +loc.getZ() + " for map " + map.getName());

            Files files = new Files();
            files.createFile("maps/" + map.getName() + "/gens", gen.getName());
            YamlConfiguration genConfig = files.getConfig("maps/" + map.getName() + "/gens", gen.getName());

            for (java.util.Map.Entry<String, Object> o : gen.serialize().entrySet()) {
                genConfig.set(o.getKey(), o.getValue());
            }

            try {
                genConfig.save(files.getFile("maps/" + map.getName() + "/gens", gen.getName()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            p.getInventory().remove(p.getItemInHand());

            genMap.remove(p);

        } else if (block.getType() == Material.EMERALD_BLOCK && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equals(Chat.format("&a&lEmerald Generator"))) {
            e.setCancelled(true);

            Map map = genMap.get(p);

            Generator gen = new Generator(String.valueOf(map.getGenerators().size()), loc, Material.EMERALD);

            map.addGenerator(gen);

            Chat.sendPrefixMessage(p, "&eAn &a&lEmerald Generator&e has been placed at: &6X:&e" + loc.getX() + " &6Y:&e" + loc.getY() + " &6Z:&e" + +loc.getZ() + " for map " + map.getName());

            Files files = new Files();
            files.createFile("maps/" + map.getName() + "/gens", gen.getName());
            YamlConfiguration genConfig = files.getConfig("maps/" + map.getName() + "/gens", gen.getName());

            for (java.util.Map.Entry<String, Object> o : gen.serialize().entrySet()) {
                genConfig.set(o.getKey(), o.getValue());
            }

            try {
                genConfig.save(files.getFile("maps/" + map.getName() + "/gens", gen.getName()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            p.getInventory().remove(p.getItemInHand());

            genMap.remove(p);

        }
    }
}
