package me.kayoz.bedwars;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.commands.BedWarsCommand;
import me.kayoz.bedwars.events.AddGeneratorEvent;
import me.kayoz.bedwars.events.LobbyEvents;
import me.kayoz.bedwars.game.GameState;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        loadMaps();
        new UserManager();
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                checkFiles();
            }
        }, 20);
    }

    private void loadMaps() {

        File f = new File(this.getDataFolder() + File.separator + "maps");

        if(f.listFiles() == null){
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
            Files files = new Files();
            YamlConfiguration config = files.getConfig(y.getPath(), str);

            File[] genFiles = gensD.listFiles();
            File[] spawnFiles = spawns.listFiles();

            /**
             * Loading Maps
             */

            Map map = new Map(str);

            System.out.println("Map " + map.getName() + " has been created.");

            /**
             * Loading Gens
             */

            if(genFiles != null) {

                for (File file : genFiles) {
                    if(!file.isDirectory()) {
                        YamlConfiguration genConfig = files.getConfig(gens.getPath(), file.getName().replace(".yml", ""));

                        java.util.Map<String, Object> genList = genConfig.getValues(false);

                        Generator gen = Generator.deserialize(genConfig.getName().replace(".yml", ""), genList);

                        map.addGenerator(gen);
                    }
                }
            }

            //Generator gen = Generator.deserialize(genConfig.getName().replace(".yml", ""), genList);

            //map.addGenerator(gen);

            //getLogger().info(ChatUtils.format("&aGenerator &e" + gen + " &ahas been loaded!"));

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
    }

    private void checkFiles() {
        Files files = new Files();

        if (files.getFile("config") == null) {
            files.createFile("config");
            YamlConfiguration config = files.getConfig("config");
            config.set("MaxPlayers", 8);

            try {
                config.save(files.getFile("config"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
