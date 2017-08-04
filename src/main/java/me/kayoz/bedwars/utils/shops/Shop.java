package me.kayoz.bedwars.utils.shops;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Utility;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/28/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Shop implements Serializable {

    @Getter
    @Setter
    private World world;
    @Getter
    @Setter
    private double x;
    @Getter
    @Setter
    private double y;
    @Getter
    @Setter
    private double z;

    public Shop(Location location) {

        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();

    }

    public Shop(World world, double x, double y, double z) {

        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Utility
    public static Shop deserialize(java.util.Map<String, Object> args) {

        World world = Bukkit.getWorld((String) args.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        }

        return new Shop(
                new Location(world, NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z"))));
    }

    @Utility
    public java.util.Map<String, Object> serialize() {
        java.util.Map<String, Object> data = new HashMap<String, Object>();
        data.put("world", world.getName());
        data.put("x", x);
        data.put("y", y);
        data.put("z", z);
        return data;
    }

}
