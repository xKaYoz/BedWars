package me.kayoz.bedwars.objects;

import org.bukkit.*;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaYoz on 7/19/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Spawn implements Serializable {

    //Location of the spawn
    Location loc;
    //Color that the spawn belongs to
    Color color;
    //Color as RGB, used for ease for creating armor.
    int colorRGB;
    //Used to fetch the spawn easier.
    String name;

    public Spawn(String name, Color color, Location loc) {
        this.name = name;
        this.loc = loc;
        this.color = color;
        this.colorRGB = color.asRGB();
    }

    public static Spawn deserialize(String name, java.util.Map<String, Object> data) {
        World world = Bukkit.getWorld((String) data.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("Unknown Spawn World");
        }
        Location loc = new Location(world, NumberConversions.toDouble(data.get("x")), NumberConversions.toDouble(data.get("y")), NumberConversions.toDouble(data.get("z")),
                NumberConversions.toFloat(data.get("yaw")), NumberConversions.toFloat(data.get("pitch")));

        return new Spawn(name, Color.fromRGB((Integer) data.get("Color")), loc);
    }

    @Utility
    public java.util.Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("world", loc.getWorld().getName());
        data.put("x", loc.getX());
        data.put("y", loc.getY());
        data.put("z", loc.getZ());
        data.put("pitch", loc.getPitch());
        data.put("yaw", loc.getYaw());
        data.put("Color", color.asRGB());

        return data;
    }

    public Location getLoc() {
        return loc;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getColorRGB() {
        return colorRGB;
    }

    public void setColorRGB(int colorRGB) {
        this.colorRGB = colorRGB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}