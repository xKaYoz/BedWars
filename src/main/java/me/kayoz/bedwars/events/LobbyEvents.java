package me.kayoz.bedwars.events;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.Configuration;
import me.kayoz.bedwars.game.timers.LobbyTimer;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.game.GameState;
import me.kayoz.bedwars.utils.users.User;
import me.kayoz.bedwars.utils.users.UserManager;
import me.kayoz.bedwars.utils.users.UserState;
import org.bukkit.Bukkit;
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
    public void onWeather(WeatherChangeEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(BedWarsPlugin.getInstance().getState() == GameState.LOBBY){
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        if(BedWarsPlugin.getInstance().getState() == GameState.LOBBY){
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        User u;

        if(UserManager.getInstance().getUser(e.getPlayer()) == null){
            u = new User(e.getPlayer());
        } else {
            u = UserManager.getInstance().getUser(e.getPlayer());
        }

        if(BedWarsPlugin.getInstance().getState() == GameState.LOBBY){
            u.setState(UserState.LOBBY);
            e.setJoinMessage(ChatUtils.format("&7" + u.getPlayer().getDisplayName() + "&e has joined!" +
                    " &7(&c" + Bukkit.getServer().getOnlinePlayers().size() + "&7/&c" + Configuration.MAX_PLAYERS + "&7)"));

            if(Bukkit.getServer().getOnlinePlayers().size() >= Configuration.MAX_PLAYERS){
                LobbyTimer.start();
            }
        } else if(BedWarsPlugin.getInstance().getState() == GameState.INGAME){
            if(u.getState() == UserState.LOGGED){
                u.setState(UserState.GAME);
                e.setJoinMessage(ChatUtils.format("&c" + u.getPlayer().getDisplayName() + "&6 has reconnected!"));
            } else {
                u.setState(UserState.SPECTATOR);
                e.setJoinMessage(null);
            }
        } else if(BedWarsPlugin.getInstance().getState() == GameState.STARTING){
            u.setState(UserState.SPECTATOR);
        } else if(BedWarsPlugin.getInstance().getState() == GameState.RESTARTING){
            u.setState(UserState.LOBBY);
        }

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        User u = UserManager.getInstance().getUser(e.getPlayer());

        if(BedWarsPlugin.getInstance().getState() == GameState.LOBBY){
            if(LobbyTimer.on){
                LobbyTimer.stop();
                Bukkit.broadcastMessage(ChatUtils.format("&cThere are not enough players to start the game."));
            }
        }

        if(BedWarsPlugin.getInstance().getState() == GameState.INGAME){
            u.setState(UserState.LOGGED);
        }

        e.setQuitMessage(ChatUtils.format("&c" + u.getPlayer().getDisplayName() + "&6 has quit!"));

    }
}
