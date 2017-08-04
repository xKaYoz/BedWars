package me.kayoz.bedwars.game.timers;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.game.GameManager;
import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.spawns.Spawn;
import me.kayoz.bedwars.utils.users.User;
import me.kayoz.bedwars.utils.users.UserManager;
import org.bukkit.*;
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

        if(!task.containsKey(p)){

            time.put(p, 5);

            task.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWarsPlugin.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(time.get(p) == 0){

                        User u = UserManager.getInstance().getUser(p);

                        Chat.sendColoredMessage(p, "&eRespawning...");

                        for(Spawn spawn : GameManager.getMap().getSpawns()){

                            if(spawn.getColorRGB() == u.getTeam().getColorRGB()){

                                p.teleport(new Location(spawn.getWorld(), spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch()).add(0, 2, 0));

                                p.setGameMode(GameMode.SURVIVAL);

                                u.getPlayer().getInventory().clear();

                                ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
                                LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
                                helmmeta.setColor(u.getColor());
                                helm.setItemMeta(helmmeta);

                                ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
                                LeatherArmorMeta chestmeta = (LeatherArmorMeta) chest.getItemMeta();
                                chestmeta.setColor(u.getColor());
                                chest.setItemMeta(chestmeta);

                                ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
                                LeatherArmorMeta legmeta = (LeatherArmorMeta) leg.getItemMeta();
                                legmeta.setColor(u.getColor());
                                leg.setItemMeta(legmeta);

                                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                                LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
                                bootsmeta.setColor(u.getColor());
                                boots.setItemMeta(bootsmeta);

                                ItemStack bed = ItemBuilder.build(Material.BED, 1, ColorManager.getChatColor(u.getColor().asRGB()) + "&l" + ColorManager.getColorName(u.getColor().asRGB()) + "'s Bed",
                                        Arrays.asList("&7Place this where you would like your bed to be."));

                                ItemStack sword = ItemBuilder.build(Material.WOOD_SWORD, 1, "&eStarting Sword", Arrays.asList("&7This is your beginning sword"));

                                u.getPlayer().getInventory().setItem(0, sword);
                                u.getPlayer().getInventory().setItem(8, bed);
                                u.getPlayer().getInventory().setHelmet(helm);
                                u.getPlayer().getInventory().setChestplate(chest);
                                u.getPlayer().getInventory().setLeggings(leg);
                                u.getPlayer().getInventory().setBoots(boots);

                                u.getPlayer().updateInventory();

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
