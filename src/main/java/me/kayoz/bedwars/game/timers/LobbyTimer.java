package me.kayoz.bedwars.game.timers;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by KaYoz on 7/11/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class LobbyTimer {

    private static int id;
    static int time = 10;

    public static boolean on = false;

    public static void start(){

        if(!on){
            id = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWarsPlugin.getInstance(), new Runnable() {
                int tick = 10;
                @Override
                public void run() {
                    if(tick > 0){
                        if(tick == 10 || (tick <= 5 && tick != 1)){
                            Bukkit.getServer().broadcastMessage(ChatUtils.format("&7The game will start in &c" + tick + " &7seconds."));
                            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                                p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                            }
                        }

                        if(tick == 1){
                            Bukkit.getServer().broadcastMessage(ChatUtils.format("&7The game will start in &c" + tick + " &7second."));
                            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                                p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                            }
                        }

                        tick --;
                        time = tick;
                    } else {
                        stop();
                    }

                }
            }, 0, 20);
            on = true;
        }

    }

    public static void stop(){
        on = false;
        Bukkit.getServer().getScheduler().cancelTask(id);
    }

}
