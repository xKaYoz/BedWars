package me.kayoz.bedwars.managers;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.objects.Generator;
import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.objects.Shop;
import me.kayoz.bedwars.objects.Spawn;
import me.kayoz.bedwars.utils.Files;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by KaYoz on 12/31/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class MapManager {

    private static ArrayList<Map> maps = new ArrayList<>();
    private static boolean loaded = false;
    private static BedWarsPlugin plugin = BedWarsPlugin.getInstance();

    public static Map getMap(String name) {
        for (Map map : maps) {
            if (map.getName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }

    public static void register(Map map) {
        if (!maps.contains(map)) {
            maps.add(map);
        }
    }

    public static void unregister(Map map) {
        if (maps.contains(map)) {
            maps.remove(map);
        }
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }

    public static void loadMaps() {
        if (loaded) return;
        File f = new File(plugin.getDataFolder() + File.separator + "maps");

        if (f.listFiles() == null) {
            return;
        }

        String[] fileList = f.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });

        for (String str : fileList) {

            File y = new File("/maps/" + str);

            File gens = new File(y.getPath() + "/gens");
            File gensD = new File(plugin.getDataFolder() + y.getPath() + "/gens");
            File spawns = new File(y.getPath() + "/spawns");
            File spawnsD = new File(plugin.getDataFolder() + y.getPath() + "/spawns");
            File shops = new File(y.getPath() + "/shops");
            File shopsD = new File(plugin.getDataFolder() + y.getPath() + "/shops");
            Files files = new Files();
            YamlConfiguration config = files.getConfig(y.getPath(), str);

            File[] genFiles = gensD.listFiles();
            File[] spawnFiles = spawnsD.listFiles();
            File[] shopFiles = shopsD.listFiles();

            /**
             * Loading Maps
             */

            java.util.Map<String, Object> mapList = config.getValues(false);

            Map map = Map.deserialize(mapList);

            /**
             * Loading Gens
             */

            if (genFiles != null) {

                for (File file : genFiles) {
                    if (!file.isDirectory()) {
                        YamlConfiguration genConfig = files.getConfig(gens.getPath(), file.getName().replace(".yml", ""));

                        java.util.Map<String, Object> genList = genConfig.getValues(false);

                        Generator gen = Generator.deserialize(String.valueOf(map.getGenerators().size()), genList);

                        map.addGenerator(gen);
                    }
                }
            }

            if (spawnFiles != null) {

                for (File file : spawnFiles) {
                    if (!file.isDirectory()) {
                        YamlConfiguration spawnConfig = files.getConfig(spawns.getPath(), file.getName().replace(".yml", ""));

                        java.util.Map<String, Object> spawnList = spawnConfig.getValues(false);

                        Spawn spawn = Spawn.deserialize(String.valueOf(map.getSpawns().size()), spawnList);

                        map.addSpawn(spawn);
                    }
                }
            }

            if (shopFiles != null) {

                for (File file : shopFiles) {

                    if (!file.isDirectory()) {
                        YamlConfiguration shopConfig = files.getConfig(shops.getPath(), file.getName().replace(".yml", ""));

                        java.util.Map<String, Object> shopList = shopConfig.getValues(false);

                        Shop shop = Shop.deserialize(shopList);

                        map.addShop(shop);
                    }
                }
            }

        }
        loaded = true;
    }

}
