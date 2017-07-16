package me.kayoz.bedwars.events;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
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
import org.bukkit.event.inventory.InventoryInteractEvent;
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
    public void onInventorySelect(InventoryClickEvent e){

        if (e.getWhoClicked().getType() != EntityType.PLAYER
                || e.getSlotType() == InventoryType.SlotType.OUTSIDE
                || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack item = e.getCurrentItem();

        if(inv.getName().contains(ChatUtils.format("&eGenerator: "))){

            if(item.getType() == Material.DIAMOND_BLOCK && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtils.format("&b&lDiamond Generator"))){
                e.setCancelled(true);
                p.getOpenInventory().close();
                int i = p.getInventory().firstEmpty();

                Map map = MapManager.getMap(ChatColor.stripColor(inv.getName().replace("Generator: ", "")));

                if(map == null){
                    p.sendMessage(ChatUtils.formatWithPrefix("The map could not be found. Please report this to the developer."));
                    return;
                }

                if(i == -1){
                    p.sendMessage(ChatUtils.formatWithPrefix("&cThere is not an empty slot in your inventory."));
                    return;
                } else {
                    ItemStack gen = ItemBuilder.build(Material.DIAMOND_BLOCK, 1, "&b&lDiamond Generator",
                            Arrays.asList("&7Place this item on the ground", "&7where you want this type of generator."));

                    if(genMap.containsKey(p)){
                        p.sendMessage(ChatUtils.formatWithPrefix("&cYou have not placed the previous generator."));
                        return;
                    }

                    p.getInventory().setItem(i, gen);

                    genMap.put(p, map);

                    Bukkit.getScheduler().runTaskLater(BedWarsPlugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            if(p != null && genMap.containsKey(p)){

                                genMap.remove(p);

                                p.sendMessage(ChatUtils.formatWithPrefix("&cWe have removed the Generator from your inventory."));

                                p.getInventory().remove(gen);

                            }
                        }
                    }, 20 * 20);

                    p.sendMessage(ChatUtils.formatWithPrefix("&eYou have been given a &b&lDiamond Generator&e. Place the item where you would like a generator to be. You have 20 seconds to place it."));
                }

            }

        }

    }
    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e){
        Location loc = e.getBlock().getLocation();
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if(block.getType() == Material.DIAMOND_BLOCK && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.format("&b&lDiamond Generator"))){
            e.setCancelled(true);

            Map map = genMap.get(p);

            Generator gen = new Generator(String.valueOf(map.getGens().size()), loc, Material.DIAMOND);

            map.addGenerator(gen);

            p.sendMessage(ChatUtils.formatWithPrefix("&eA generator has been placed at: &6X:&e" + loc.getX() + " &6Y:&e" + loc.getY() + " &6Z:&e" + + loc.getZ() + " for map " + map.getName()));

            Files files = new Files();
            files.createFile("maps/" + map.getName() + "/gens", gen.getName());
            YamlConfiguration genConfig = files.getConfig("maps/" + map.getName() + "/gens", gen.getName());

            for(java.util.Map.Entry<String, Object> o : gen.serialize().entrySet()){
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
