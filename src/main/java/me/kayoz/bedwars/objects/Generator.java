package me.kayoz.bedwars.objects;

import me.kayoz.bedwars.BedWarsPlugin;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Generator implements Serializable {
    //Location of the Generator
    private Location loc;

    //What item will drop when the generator's cooldown is finished.
    private Material drop;

    //A way to sort out the generators.
    private String name;

    //Determines the level of the generator. This is for us to be able to upgrade it.
    private int level = 1;

    //Timer
    private int timerID;
    private long time;
    private int base;

    //Generator object
    public Generator(String name, Location loc, Material drop) {
        this.name = name;
        this.loc = loc;
        this.drop = drop;
    }

    //Loads a generator from a file
    @Utility
    public static Generator deserialize(String id, Map<String, Object> data) {
        World world = Bukkit.getWorld((String) data.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        }
        Location loc = new Location(world, NumberConversions.toDouble(data.get("x")), NumberConversions.toDouble(data.get("y")), NumberConversions.toDouble(data.get("z")));
        return new Generator(id, loc, Material.getMaterial((String) data.get("Material")));
    }

    public void start() {
        if (getDrop() == Material.IRON_INGOT) {
            base = 3;
        } else if (getDrop() == Material.GOLD_INGOT) {
            base = 7;
        } else if (getDrop() == Material.DIAMOND) {
            base = 30;
        } else if (getDrop() == Material.EMERALD) {
            base = 60;
        } else {
            base = 0;
        }

        time = base / getLevel();

        timerID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(BedWarsPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {

                loc.getWorld().dropItemNaturally(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()), new ItemStack(getDrop()));

            }
        }, 0, time * 20);
    }

    public Location getLoc() {
        return loc;
    }

    public Material getDrop() {
        return drop;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getTimerID() {
        return timerID;
    }

    public long getTime() {
        return time;
    }

    public int getBase() {
        return base;
    }

    //Allows a generator to be saved to a file.
    @Utility
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("world", loc.getWorld().getName());
        data.put("x", loc.getX());
        data.put("y", loc.getY());
        data.put("z", loc.getZ());
        data.put("Material", this.drop.toString());

        return data;
    }

}
