package me.kayoz.bedwars.events;

import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.objects.Team;
import me.kayoz.bedwars.managers.TeamManager;
import me.kayoz.bedwars.objects.User;
import me.kayoz.bedwars.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.Random;

/**
 * Created by KaYoz on 7/25/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */
@Deprecated
public class BedEvents implements Listener {

    public static Block getBedHead(Block b) {
        byte data = b.getData();
        return data == 8 || data == 9 || data == 10 || data == 11 ? b : b.getWorld().getBlockAt(b.getX() + (data == 3 ? 1 : data == 1 ? -1 : 0), b.getY(), b.getZ() + (data == 0 ? 1 : data == 2 ? -1 : 0));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        Location loc = e.getBlock().getLocation();
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if ((block.getType() == Material.BED || block.getType() == Material.BED_BLOCK) && p.getItemInHand().hasItemMeta()
                && ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName()).contains("'s Bed")) {

            User u = UserManager.getUser(p);
            Team team = u.getTeam();

            team.setCanRespawn(true);

            team.getBed().add(loc);

            Location nextLoc = getBedHead(block).getLocation();

            team.getBed().add(nextLoc);

            for (User user : team.getMembers()) {

                Player pu = user.getPlayer();

                pu.getInventory().remove(Material.BED);

            }
        }

    }

    @EventHandler
    public void onCheckPlace(BlockPlaceEvent e) {

        Player p = e.getPlayer();
        Block block = e.getBlock();

        if (!(block.getType() == Material.BED || block.getType() == Material.BED_BLOCK)) {

        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Location loc = e.getBlock().getLocation();
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if ((block.getType() == Material.BED || block.getType() == Material.BED_BLOCK)) {

            User u = UserManager.getUser(p);
            Team breakTeam = u.getTeam();

            for (Team team : TeamManager.getTeams()) {

                if (team.getBed().contains(loc)) {

                    if (team == u.getTeam()) {

                        e.setCancelled(true);

                        Chat.sendColoredMessage(p, "&cYou cannot destroy your own bed.");

                    } else {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + ColorManager.getColorName(team.getColorRGB()) + "&7's bed has been destroyed by "
                                + ColorManager.getChatColor(breakTeam.getColorRGB()) + p.getName()));

                        team.setCanRespawn(false);

                        team.getBed().clear();

                        Random ran = new Random();

                        ran.nextInt(10);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Item item = e.getItem();

        if (item.getItemStack().getType() == Material.BED_BLOCK || item.getItemStack().getType() == Material.BED) {

            e.setCancelled(true);

        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Item item = e.getItemDrop();

        if (item.getItemStack().getType() == Material.BED_BLOCK || item.getItemStack().getType() == Material.BED) {

            e.setCancelled(true);

        }
    }

}
