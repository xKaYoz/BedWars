package me.kayoz.bedwars.utils.generators;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Utility;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Generator implements Serializable{
    @Getter @Setter
    private World world;
    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;
    @Getter @Setter
    private double z;
    @Getter @Setter
    private Material drop;

    public Generator(World world, double x, double y, double z, Material type){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.drop = type;
    }
    @Utility
    public Map<String, Object> serialize(){
        Map<String, Object> data = new HashMap<>();

        data.put("world", this.world.getName());
        data.put("x", this.x);
        data.put("y", this.y);
        data.put("z", this.z);
        data.put("Material", this.drop.toString());

        return data;
    }

    public static Generator deserialize(Map<String, Object> args){
        World world = Bukkit.getWorld((String) args.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        }
        return new Generator(world, NumberConversions.toDouble(args.get("x")),
                NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")), Material.getMaterial((String) args.get("Material")));
    }

}
