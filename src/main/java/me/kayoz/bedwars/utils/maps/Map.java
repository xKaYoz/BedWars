package me.kayoz.bedwars.utils.maps;

import lombok.Getter;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.spawns.Spawn;
import org.bukkit.Location;
import org.bukkit.Utility;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Map implements Serializable{

    @Getter
    private String name;
    @Getter
    private ArrayList<Generator> gens = new ArrayList<>();
    @Getter
    private ArrayList<Spawn> spawns = new ArrayList<>();
    @Getter
    private String creator;

    public Map(String p, String name){
        this.creator = p;
        this.name = name;
        MapManager.register(this);
    }

    /*
     * Used to load in Maps from the configuration.
     */
    public Map(String p, String name, ArrayList<Generator> gens, ArrayList<Spawn> spawns){
        this.creator = p;
        this.name = name;
        this.gens = gens;
        this.spawns = spawns;
        MapManager.register(this);
    }

    public void addSpawn(Spawn spawn){
        spawns.add(spawn);
    }

    public void addGenerator(Generator gen){
        gens.add(gen);
    }

    @Utility
    public java.util.Map<String, Object> serialize(){
        java.util.Map<String, Object> data = new HashMap<String, Object>();
        data.put("creator", creator);
        data.put("name", this.getName());
        ArrayList<String> ds = new ArrayList<>();
        for(Generator gen : this.gens){
            ds.add(gen.serialize().toString());
        }
        ArrayList<String> sp = new ArrayList<>();
        for(Spawn spawn : this.spawns){
            sp.add(spawn.serialize().toString());
        }
        data.put("gens", ds);
        data.put("spawns", sp);
        return data;
    }

    public static Map deserialize(java.util.Map<String, Object> args){
        return new Map(
                args.get("creator").toString(),
                args.get("name").toString(),
                (ArrayList<Generator>) args.get("gens"),
                (ArrayList<Spawn>) args.get("spawns"));
    }

}
