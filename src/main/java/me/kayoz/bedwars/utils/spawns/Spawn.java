package me.kayoz.bedwars.utils.spawns;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/19/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Spawn implements Serializable {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Map map;
    @Getter
    @Setter
    private Color color;
    @Getter
    @Setter
    private int colorRGB;
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
    @Getter
    @Setter
    private float pitch;
    @Getter
    @Setter
    private float yaw;

    public Spawn(String name, Color color, Map map, Location location) {
        this.name = name;
        this.map = map;
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
        this.color = color;
        this.colorRGB = color.asRGB();
    }

    public Spawn(String name, Color color, Map map, World world, double x, double y, double z, float pitch, float yaw) {
        this.name = name;
        this.map = map;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.color = color;
        this.colorRGB = color.asRGB();
    }

    public static Spawn deserialize(String name, java.util.Map<String, Object> args) {
        World world = Bukkit.getWorld((String) args.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("Unknown Spawn World");
        }
        return new Spawn(name, Color.fromRGB((Integer) args.get("Color")), MapManager.getMap(args.get("Map").toString()), world, NumberConversions.toDouble(args.get("x")),
                NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")), NumberConversions.toFloat("pitch"), NumberConversions.toFloat("yaw"));
    }

    @Utility
    public java.util.Map<String, Object> serialize() {
        java.util.Map<String, Object> data = new HashMap<>();

        data.put("world", this.world.getName());
        data.put("x", this.x);
        data.put("y", this.y);
        data.put("z", this.z);
        data.put("pitch", this.pitch);
        data.put("yaw", this.yaw);
        data.put("Map", this.map.getName());
        data.put("Color", this.getColor().asRGB());

        return data;
    }
}
