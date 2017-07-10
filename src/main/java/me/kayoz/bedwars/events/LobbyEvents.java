package me.kayoz.bedwars.events;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.utils.game.GameState;
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

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){

    }
}
