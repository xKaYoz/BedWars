package me.kayoz.bedwars.game.timers;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.game.GameManager;
import me.kayoz.bedwars.managers.UserManager;
import me.kayoz.bedwars.objects.Spawn;
import me.kayoz.bedwars.objects.User;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by KaYoz on 7/27/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class RespawnTimer {

    private static HashMap<Player, Integer> task = new HashMap<>();
    private static HashMap<Player, Integer> time = new HashMap<>();

    public static void start(Player p) {

        if (!task.containsKey(p)) {

            time.put(p, 5);

            task.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWarsPlugin.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (time.get(p) == 0) {

                        User u = UserManager.getUser(p);

                        Chat.sendColoredMessage(p, "&eRespawning...");

                        for (Spawn spawn : GameManager.getMap().getSpawns()) {

                            if (u.getTeam() != null && spawn.getColorRGB() == u.getTeam().getColorRGB()) {

                                p.teleport(new Location(spawn.getLoc().getWorld(), spawn.getLoc().getX(), spawn.getLoc().getY(), spawn.getLoc().getZ(), spawn.getLoc().getYaw(), spawn.getLoc().getPitch()).add(0, 2, 0));

                                p.setGameMode(GameMode.SURVIVAL);

                               GameManager.giveItems(u);

                            }

                        }

                        stop(p);

                    } else {
                        Chat.sendColoredMessage(p, "&eRespawning in &6" + time.get(p) + "&e seconds.");
                        time.put(p, time.get(p) - 1);
                    }
                }
            }, 0, 20));

        }

    }

    public static void stop(Player p) {

        Bukkit.getScheduler().cancelTask(task.get(p));

        task.remove(p);
        time.remove(p);

    }

}
