package me.kayoz.bedwars;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.commands.BedWarsCommand;
import me.kayoz.bedwars.events.*;
import me.kayoz.bedwars.game.GameState;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.VaultManager;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.spawns.Spawn;
import me.kayoz.bedwars.utils.users.UserManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public final class BedWarsPlugin extends JavaPlugin {

    @Getter
    private static BedWarsPlugin instance;
    @Getter
    @Setter
    private GameState state;

    @Override
    public void onEnable() {
        instance = this;
        registerListeners();
        registerCommands();
        state = GameState.LOBBY;
        VaultManager.setup();
        loadMaps();
        new UserManager();
        checkFiles();
    }

    private void loadMaps() {

        File f = new File(this.getDataFolder() + File.separator + "maps");

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
            File gensD = new File(this.getDataFolder() + y.getPath() + "/gens");
            File spawns = new File(y.getPath() + "/spawns");
            File spawnsD = new File(this.getDataFolder() + y.getPath() + "/spawns");
            Files files = new Files();
            YamlConfiguration config = files.getConfig(y.getPath(), str);

            File[] genFiles = gensD.listFiles();
            File[] spawnFiles = spawnsD.listFiles();

            /**
             * Loading Maps
             */

            System.out.println(config.getString("creator"));
            System.out.println(y.getPath() + "/" + str);

            Map map = new Map(config.getString("creator"), str);

            //java.util.Map<String, Object> maps = config.getValues(true);

            //Map map = Map.deserialize(maps);

            /**
             * Loading Gens
             */

            if (genFiles != null) {

                for (File file : genFiles) {
                    if (!file.isDirectory()) {
                        YamlConfiguration genConfig = files.getConfig(gens.getPath(), file.getName().replace(".yml", ""));

                        java.util.Map<String, Object> genList = genConfig.getValues(false);

                        Generator gen = Generator.deserialize(String.valueOf(map.getGens().size()), genList);

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

                        getLogger().info("The spawn " + spawn.getName() + " has been loaded for map " + spawn.getMap().getName());

                    }
                }
            }

            this.getLogger().info(map.getName() + " has " + map.getGens().size() + " generators named " + map.getGens());

        }

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void registerCommands() {
        this.getCommand("bw").setExecutor(new BedWarsCommand());
    }

    private void registerListeners() {
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new LobbyEvents(), this);
        pm.registerEvents(new AddGeneratorEvent(), this);
        pm.registerEvents(new MapListInteractEvent(), this);
        pm.registerEvents(new GenListInteractEvent(), this);
        pm.registerEvents(new GenInfoInteractEvent(), this);
        pm.registerEvents(new SpawnListInteractEvent(), this);
        pm.registerEvents(new SpawnInfoInteractEvent(), this);

        pm.registerEvents(new PlayerCountCheck(), this);
    }

    private void checkFiles() {
        Files files = new Files();

        if (files.getFile("config") == null) {
            files.createFile("config");
            YamlConfiguration config = files.getConfig("config");
            config.set("Max Players", 8);
            config.set("Teams", false);
            config.set("Players Per Team", 0);
            config.set("Number of Teams", 0);

            try {
                config.save(files.getFile("config"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
