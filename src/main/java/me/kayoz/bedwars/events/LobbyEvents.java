package me.kayoz.bedwars.events;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.Settings;
import me.kayoz.bedwars.game.GameState;
import me.kayoz.bedwars.game.timers.LobbyTimer;
import me.kayoz.bedwars.managers.UserManager;
import me.kayoz.bedwars.managers.UserState;
import me.kayoz.bedwars.objects.User;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class LobbyEvents implements Listener {

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (BedWarsPlugin.getInstance().getState() == GameState.LOBBY) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {

        Player p = (Player) e.getEntity();

        p.setFoodLevel(20);
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Files files = new Files();
        YamlConfiguration config = files.getConfig("config");
        YamlConfiguration lobby = files.getConfig("lobby");

        if (!config.getBoolean("Admin Mode")) {
            User u;

            if (UserManager.getUser(e.getPlayer()) == null) {
                u = new User(e.getPlayer());
            } else {
                u = UserManager.getUser(e.getPlayer());
            }

            if (BedWarsPlugin.getInstance().getState() == GameState.LOBBY) {
                u.setState(UserState.LOBBY);
                e.setJoinMessage(Chat.format("&7" + u.getPlayer().getDisplayName() + "&e has joined!" +
                        " &7(&c" + Bukkit.getServer().getOnlinePlayers().size() + "&7/&c" + Settings.MAX_PLAYERS + "&7)"));

                World world = Bukkit.getWorld(lobby.getString("lobby.world"));
                double x = lobby.getDouble("lobby.x");
                double y = lobby.getDouble("lobby.y");
                double z = lobby.getDouble("lobby.z");
                float yaw = (float) lobby.getDouble("lobby.yaw");
                float pitch = (float) lobby.getDouble("lobby.pitch");

                u.getPlayer().teleport(new Location(world, x, y, z, yaw, pitch));

                if (Bukkit.getServer().getOnlinePlayers().size() >= Settings.MAX_PLAYERS) {
                    LobbyTimer.start();
                }
            } else if (BedWarsPlugin.getInstance().getState() == GameState.INGAME) {
                if (u.getState() == UserState.LOGGED) {
                    u.setState(UserState.GAME);
                    e.setJoinMessage(Chat.format("&c" + u.getPlayer().getDisplayName() + "&6 has reconnected!"));
                } else {
                    u.setState(UserState.SPECTATOR);
                    e.setJoinMessage(null);
                }
            } else if (BedWarsPlugin.getInstance().getState() == GameState.STARTING) {
                u.setState(UserState.SPECTATOR);
            } else if (BedWarsPlugin.getInstance().getState() == GameState.RESTARTING) {
                u.setState(UserState.LOBBY);
            }
        } else {
            Chat.sendPrefixBroadcast("&cThe game will not start due to the server being in admin mode.");
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        User u = UserManager.getUser(e.getPlayer());

        if (BedWarsPlugin.getInstance().getState() == GameState.LOBBY) {
            if (LobbyTimer.on) {
                LobbyTimer.stop();
                Chat.sendColoredBroadcast("&cThere are not enough players to start the game.");
            }
        }

        if (BedWarsPlugin.getInstance().getState() == GameState.INGAME) {
            u.setState(UserState.LOGGED);
        }

        e.setQuitMessage(Chat.format("&c" + e.getPlayer().getDisplayName() + "&6 has quit!"));

    }
}
