package me.kayoz.bedwars.game;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.Settings;
import me.kayoz.bedwars.managers.MapManager;
import me.kayoz.bedwars.managers.TeamManager;
import me.kayoz.bedwars.managers.UserManager;
import me.kayoz.bedwars.managers.UserState;
import me.kayoz.bedwars.objects.*;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by KaYoz on 7/11/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class GameManager {

    private static Map map;

    public static void teleport(Map map) {
        for (Team team : TeamManager.getTeams()) {
            for (User u : team.getMembers()) {
                if (u.getState() != UserState.GAME) {
                    return;
                }
                for (Spawn s : map.getSpawns()) {
                    if (s.getColorRGB() == team.getColorRGB()) {
                        Player p = u.getPlayer();

                        Location loc = new Location(s.getLoc().getWorld(), s.getLoc().getX(), s.getLoc().getY(), s.getLoc().getZ(), s.getLoc().getYaw(), s.getLoc().getPitch());

                        p.sendMessage(Chat.createLine("&8"));
                        Chat.sendCenteredMessage(p, "&6&lBedWars");
                        Chat.sendCenteredMessage(p, "&eYou have spawned on an island with a bed on it.");
                        Chat.sendCenteredMessage(p, "&eProtect your bed by getting minerals from your generator.");
                        Chat.sendCenteredMessage(p, "&eWith these minerals, you can buy blocks.");
                        Chat.sendCenteredMessage(p, "&eGo to your opponents' island and destroy their bed.");
                        Chat.sendCenteredMessage(p, "&eThe last team alive wins.");
                        Chat.sendCenteredMessage(p, "&eGood luck!");
                        p.sendMessage(Chat.createLine("&8"));

                        p.teleport(loc);
                    }
                }
            }

        }
    }

    public static void manageGameInventory() {

        for (User u : UserManager.getUsers()) {

            if (u.getState() == UserState.GAME) {

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

                ItemStack sword = ItemBuilder.build(Material.WOOD_SWORD, 1, "&eStarting Sword", Arrays.asList("&7This is your beginning sword"));

                u.getPlayer().getInventory().setItem(0, sword);
                u.getPlayer().getInventory().setHelmet(helm);
                u.getPlayer().getInventory().setChestplate(chest);
                u.getPlayer().getInventory().setLeggings(leg);
                u.getPlayer().getInventory().setBoots(boots);

                u.getPlayer().updateInventory();

            }

        }

    }

    public static void stopGenerators(Map map) {

        for (Generator gen : map.getGenerators()) {

            Bukkit.getServer().getScheduler().cancelTask(gen.getTimerID());

        }

    }

    //Start the game.
    public static void start() {

        Chat.sendColoredBroadcast("&aCheck 0");

        Map map = selectMap();

        GameManager.map = map;

        Chat.sendColoredBroadcast("&aCheck 1");

        ColorManager.addColors(map);

        Chat.sendColoredBroadcast("&aCheck 2");

        loadGens(map);

        Chat.sendColoredBroadcast("&aCheck 3");

        loadShops(map);

        Chat.sendColoredBroadcast("&aCheck 4");

        arrangeTeams();

        Chat.sendColoredBroadcast("&aCheck 5");

        teleport(map);

        Chat.sendColoredBroadcast("&aCheck 5");

        manageGameInventory();

        BedWarsPlugin.getInstance().setState(GameState.INGAME);

    }

    public static void stop() {

        if (BedWarsPlugin.getInstance().getState() != GameState.RESTARTING) {

            BedWarsPlugin.getInstance().setState(GameState.RESTARTING);

            Chat.sendColoredBroadcast("&aCheck 1");

            stopGenerators();

            Chat.sendColoredBroadcast("&aCheck 2");

            despawnShops();

            Chat.sendColoredBroadcast("&aCheck 3");

            clearDrops();

            Chat.sendColoredBroadcast("&aCheck 4");

            manageLobbyInventory();

            Chat.sendColoredBroadcast("&aCheck 5");

            BedWarsPlugin.getInstance().teleportLobby();

            Chat.sendColoredBroadcast("&aCheck 6");

            Bukkit.getServer().reload();

        }

    }

    private static void clearDrops() {

        for (Entity e : map.getLoc().getWorld().getEntities()) {
            if (e.getType() == EntityType.DROPPED_ITEM) {
                e.remove();
            }
        }

    }

    private static void manageLobbyInventory() {

        for (Player p : Bukkit.getOnlinePlayers()) {

            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            p.updateInventory();

        }

    }

    private static void despawnShops() {

        for (Shop shop : map.getShops()) {
            shop.despawn();
        }

    }

    //This will determine if there is voting allowed. If allowed, see what has more votes. If not allowed, pick a random map.
    private static Map selectMap() {
        if (Settings.VOTE_MAP) {
            //Get the highest voted map.
        } else {
            Random ran = new Random();

            int mapNum = ran.nextInt(MapManager.getMaps().size());

            return MapManager.getMaps().get(mapNum);
        }
        return null;
    }

    private static void loadGens(Map map) {
        for (Generator generator : map.getGenerators()) {
            generator.start();
        }
    }

    private static void loadShops(Map map) {
        for (Shop shop : map.getShops()) {
            shop.spawn();
        }
    }

    private static void arrangeTeams() {
        for (User u : UserManager.getUsers()) {

            if (!Settings.TEAM) {
                if (u.getState() != UserState.LOBBY) {
                    return;
                }

                u.setState(UserState.GAME);

                Team team = new Team(u);

                Color color = ColorManager.nextColor();

                team.setColor(color);
                team.setColorRGB(color.asRGB());
                team.setName(ColorManager.getColorName(color.asRGB()));

                u.setColor(color);

            }

        }
    }

    public static Map getMap() {
        return map;
    }

    public static void stopGenerators() {

        for (Generator gen : map.getGenerators()) {

            Bukkit.getServer().getScheduler().cancelTask(gen.getTimerID());

        }

    }

}
