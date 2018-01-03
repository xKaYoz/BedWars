package me.kayoz.bedwars.objects;

import me.kayoz.bedwars.managers.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Utility;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Map implements Serializable {

    private String name;
    private String creator;
    private ArrayList<Generator> generators = new ArrayList<>();
    private ArrayList<Spawn> spawns = new ArrayList<>();
    private ArrayList<Shop> shops = new ArrayList<>();
    private Location loc;

    //This creates the Map object.
    public Map(String name, String creator, Location loc) {
        this.name = name;
        this.loc = loc;
        MapManager.register(this);
    }

    //Allows the map to be loaded from a file.
    @Utility
    public static Map deserialize(java.util.Map<String, Object> data) {

        World world = Bukkit.getWorld((String) data.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        }

        Location loc = new Location(world, NumberConversions.toDouble(data.get("x")), NumberConversions.toDouble(data.get("y")), NumberConversions.toDouble(data.get("z")));

        return new Map((String) data.get("name"), (String) data.get("creator"), loc);
    }

    /**
     * Start of Getters
     */

    public String getName() {
        return name;
    }

    public ArrayList<Generator> getGenerators() {
        return generators;
    }

    public ArrayList<Spawn> getSpawns() {
        return spawns;
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public Location getLoc() {
        return loc;
    }

    public void addSpawn(Spawn spawn) {
        spawns.add(spawn);
    }

    public void addGenerator(Generator gen) {
        generators.add(gen);
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }

    public int tp(Player p) {
        if (loc == null) return 1;
        if (p == null) return 2;
        p.teleport(loc);
        return 0;
    }

    /**
     * End of Getters
     */

    //Allows the map to be saved into a file.
    @Utility
    public java.util.Map<String, Object> serialize() {
        java.util.Map<String, Object> data = new HashMap<String, Object>();

        data.put("world", loc.getWorld().getName());
        data.put("x", loc.getX());
        data.put("y", loc.getY());
        data.put("z", loc.getZ());
        data.put("name", name);
        ArrayList<String> gs = new ArrayList<>();
        for (Generator gen : this.generators) {
            gs.add(gen.serialize().toString());
        }
        ArrayList<String> sp = new ArrayList<>();
        for (Spawn spawn : this.spawns) {
            sp.add(spawn.serialize().toString());
        }
        data.put("creator", creator);
        data.put("generators", gs);
        data.put("spawns", sp);
        return data;
    }

    public String getCreator() {
        return creator;
    }
}
