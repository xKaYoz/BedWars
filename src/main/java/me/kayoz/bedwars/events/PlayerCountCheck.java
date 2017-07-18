package me.kayoz.bedwars.events;

import me.kayoz.bedwars.Configuration;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.VaultManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.permissions.PermissibleBase;

/**
 * Created by KaYoz on 7/17/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class PlayerCountCheck implements Listener {

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        if (Bukkit.getServer().getOnlinePlayers().size() >= Configuration.MAX_PLAYERS) {
            Permission perm = VaultManager.permission;

            PermissibleBase pb = new PermissibleBase(Bukkit.getServer().getOfflinePlayer(e.getUniqueId()));

            if (Bukkit.getServer().getOnlinePlayers().size() >= Configuration.MAX_PLAYERS) {
                if (!pb.hasPermission(new org.bukkit.permissions.Permission("bedwars.join"))) {
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, ChatUtils.format("&cThe server is currently full."));
                } else {
                    e.allow();
                }
            }


        }
    }
}
