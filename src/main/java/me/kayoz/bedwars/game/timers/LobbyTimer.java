package me.kayoz.bedwars.game.timers;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.game.GameManager;
import me.kayoz.bedwars.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by KaYoz on 7/11/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class LobbyTimer {

    public static boolean on = false;
    static int time = 10;
    private static int id;

    public static void start() {
        on = true;
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWarsPlugin.getInstance(), new Runnable() {
            int tick = 10;

            @Override
            public void run() {
                if (tick > 0) {
                    if (tick == 10 || (tick <= 5 && tick != 1)) {
                        Bukkit.getServer().broadcastMessage(Chat.format("&eThe game will start in &6" + tick + " &eseconds."));
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                        }
                    }

                    if (tick == 1) {
                        Bukkit.getServer().broadcastMessage(Chat.format("&eThe game will start in &6" + tick + " &esecond."));
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                        }
                    }

                    tick--;
                    time = tick;
                } else {
                    stop();
                    GameManager.start();
                }

            }
        }, 0, 20);
    }

    public static void stop() {
        on = false;
        Bukkit.getServer().getScheduler().cancelTask(id);
    }

}
