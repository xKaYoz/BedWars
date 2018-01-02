package me.kayoz.bedwars.events;

import me.kayoz.bedwars.game.GameManager;
import me.kayoz.bedwars.objects.Shop;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KaYoz on 7/27/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class MapResetEvents implements Listener {

    private static ArrayList<Block> blocks = new ArrayList<>();
    private static ArrayList<Item> drops = new ArrayList<>();

    public static void removeAll() {
        removeBlocks();
        removeDrops();
    }

    public static void removeBlocks() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }

    public static void removeDrops() {
        World world = GameManager.getMap().getLoc().getWorld();
        List<Entity> entList = world.getEntities();

        for (Shop s : GameManager.getMap().getShops()) {

            s.despawn();
            s.getV().setHealth(0.0);

        }

        for (Entity current : entList) {
            if (current instanceof Item) {
                current.remove();
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        blocks.add(e.getBlock());

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        blocks.remove(e.getBlock());

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {

        drops.add(e.getItemDrop());

    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {

        drops.remove(e.getItem());

    }
}
