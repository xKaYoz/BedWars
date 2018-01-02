package me.kayoz.bedwars.objects;

import me.kayoz.bedwars.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Utility;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/28/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Shop implements Serializable {

    //This is the location of the shop.
    Location loc;
    //This is the shop entity
    Villager v;

    //Creates the shop object.
    public Shop(Location location) {
        this.loc = location;
    }

    //Allows a shop object to be loaded from the config file.
    @Utility
    public static Shop deserialize(java.util.Map<String, Object> args) {

        World world = Bukkit.getWorld((String) args.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        }

        return new Shop(
                new Location(world, NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z"))));
    }

    public Location getLoc() {
        return loc;
    }

    public Villager getV() {
        return v;
    }

    //Allows a shop to be saved into the config file.
    @Utility
    public java.util.Map<String, Object> serialize() {
        java.util.Map<String, Object> data = new HashMap<String, Object>();
        data.put("world", loc.getWorld().getName());
        data.put("x", loc.getX());
        data.put("y", loc.getY());
        data.put("z", loc.getZ());
        return data;
    }

    //Spawns in the shop entity.
    public void spawn() {
        this.v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        v.setAdult();
        v.setCanPickupItems(false);
        v.setRemoveWhenFarAway(false);
        v.setCustomName(Chat.format("&e&lShop"));
        v.setCustomNameVisible(true);
        v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10000));
    }

    public void despawn() {
        v.remove();
    }

}
