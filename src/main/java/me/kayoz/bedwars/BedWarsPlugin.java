package me.kayoz.bedwars;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.commands.BedWarsCommand;
import me.kayoz.bedwars.commands.subcommands.AdminSubCommand;
import me.kayoz.bedwars.events.*;
import me.kayoz.bedwars.events.shops.ShopEvents;
import me.kayoz.bedwars.game.GameState;
import me.kayoz.bedwars.game.timers.LobbyTimer;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.VaultManager;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.shops.Shop;
import me.kayoz.bedwars.utils.spawns.Spawn;
import me.kayoz.bedwars.utils.users.User;
import me.kayoz.bedwars.utils.users.UserManager;
import me.kayoz.bedwars.utils.users.UserState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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

        AdminSubCommand.check(Bukkit.getConsoleSender());

        teleportLobby();
        createUsers();

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
            File shops = new File(y.getPath() + "/shops");
            File shopsD = new File(this.getDataFolder() + y.getPath() + "/shops");
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

                        getLogger().info("A shop has been loaded for map " + map.getName() + ".");
                    }
                }
            }

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
        pm.registerEvents(new BedEvents(), this);
        pm.registerEvents(new DeathEvent(), this);
        pm.registerEvents(new ShopEvents(), this);
        pm.registerEvents(new MapResetEvents(), this);

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
            config.set("Admin Mode", true);

            try {
                config.save(files.getFile("config"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    /**
     * Converts a rotation to a cardinal direction name.
     *
     * @param rot
     * @return
     */
    private String getDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return "North";
        } else if (22.5 <= rot && rot < 67.5) {
            return "Northeast";
        } else if (67.5 <= rot && rot < 112.5) {
            return "East";
        } else if (112.5 <= rot && rot < 157.5) {
            return "Southeast";
        } else if (157.5 <= rot && rot < 202.5) {
            return "South";
        } else if (202.5 <= rot && rot < 247.5) {
            return "Southwest";
        } else if (247.5 <= rot && rot < 292.5) {
            return "West";
        } else if (292.5 <= rot && rot < 337.5) {
            return "Northwest";
        } else if (337.5 <= rot && rot < 360.0) {
            return "North";
        } else {
            return null;
        }
    }

    public void createUsers() {

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {

            if (UserManager.getInstance().getUser(p) == null) {

                User u = new User(p);

                u.setState(UserState.LOBBY);

            }

            p.setGameMode(GameMode.SURVIVAL);

        }

    }

    public void teleportLobby() {

        Files files = new Files();
        if (files.getFile("lobby") == null) {
            files.createFile("lobby");
        }

        YamlConfiguration lobby = files.getConfig("lobby");
        YamlConfiguration config = files.getConfig("config");

        if (!config.getBoolean("Admin Mode")) {

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {

                World world = Bukkit.getWorld(lobby.getString("lobby.world"));
                double x = lobby.getDouble("lobby.x");
                double y = lobby.getDouble("lobby.y");
                double z = lobby.getDouble("lobby.z");
                float yaw = (float) lobby.getDouble("lobby.yaw");
                float pitch = (float) lobby.getDouble("lobby.pitch");

                p.teleport(new Location(world, x, y, z, yaw, pitch));

                p.setHealth(p.getMaxHealth());

            }

            if (Bukkit.getServer().getOnlinePlayers().size() == Configuration.MAX_PLAYERS) {
                LobbyTimer.start();
            }
        }

    }
}
