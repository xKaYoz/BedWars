package me.kayoz.bedwars;

import me.kayoz.bedwars.commands.BedWarsCommand;
import me.kayoz.bedwars.commands.subcommands.AdminSubCommand;
import me.kayoz.bedwars.events.*;
import me.kayoz.bedwars.events.shops.*;
import me.kayoz.bedwars.game.GameManager;
import me.kayoz.bedwars.game.GameState;
import me.kayoz.bedwars.game.timers.LobbyTimer;
import me.kayoz.bedwars.managers.MapManager;
import me.kayoz.bedwars.managers.UserManager;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.VaultManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class BedWarsPlugin extends JavaPlugin {

    private static BedWarsPlugin instance;

    private GameState state;

    public static BedWarsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        registerListeners();
        registerCommands();
        state = GameState.LOBBY;
        VaultManager.setup();
        MapManager.loadMaps();
        new UserManager();
        checkFiles();

        AdminSubCommand.check(Bukkit.getConsoleSender());

        teleportLobby();
        UserManager.createUsers();

    }

    @Override
    public void onDisable() {

        GameManager.stop();

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

        pm.registerEvents(new BlockShop(), this);
        pm.registerEvents(new SwordShop(), this);
        pm.registerEvents(new RangedShop(), this);
        pm.registerEvents(new FoodShop(), this);
        pm.registerEvents(new ArmorShop(), this);

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

            if (Bukkit.getServer().getOnlinePlayers().size() == Settings.MAX_PLAYERS) {
                LobbyTimer.start();
            }
        }

    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
