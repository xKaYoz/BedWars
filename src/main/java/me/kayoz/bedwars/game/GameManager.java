package me.kayoz.bedwars.game;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.Configuration;
import me.kayoz.bedwars.utils.ColorManager;
import me.kayoz.bedwars.utils.ItemBuilder;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.generators.Generator;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import me.kayoz.bedwars.utils.shops.Shop;
import me.kayoz.bedwars.utils.spawns.Spawn;
import me.kayoz.bedwars.utils.team.Team;
import me.kayoz.bedwars.utils.team.TeamManager;
import me.kayoz.bedwars.utils.users.User;
import me.kayoz.bedwars.utils.users.UserManager;
import me.kayoz.bedwars.utils.users.UserState;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by KaYoz on 7/11/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class GameManager {

    @Getter
    @Setter
    private static Map map;

    public static void start() {

        BedWarsPlugin.getInstance().setState(GameState.INGAME);

        selectMap();

        placeGens();

        manageTeams();

        spawnShops();

        teleport();

        manageInventory();

    }

    public static void stop() {

        Bukkit.getServer().reload();

    }

    public static void selectMap() {
        Random ran = new Random();

        int mapNum = ran.nextInt(MapManager.getMaps().size());

        map = MapManager.getMaps().get(mapNum);

        ColorManager.addColors(map);
    }

    public static void placeGens() {
        for (Generator gen : map.getGens()) {
            gen.update();
        }
    }

    public static void manageTeams() {
        for (User u : UserManager.getInstance().getUsers()) {
            if (!Configuration.TEAM) {
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

    public static void spawnShops() {

        for (Shop shop : map.getShops()) {

            Villager villager = (Villager) map.getLoc().getWorld().spawnEntity(new Location(shop.getWorld(), shop.getX(), shop.getY(), shop.getZ()), EntityType.VILLAGER);

            villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1000));

            villager.setCustomName(Chat.format("&e&lShop"));

            villager.setCustomNameVisible(true);

            map.addVillager(villager);

        }

    }

    public static void teleport() {
        for (Team team : TeamManager.getTeams()) {
            for (User u : team.getMembers()) {
                if (u.getState() != UserState.GAME) {
                    return;
                }
                for (Spawn s : map.getSpawns()) {
                    if (s.getColorRGB() == team.getColorRGB()) {
                        Player p = u.getPlayer();

                        Location loc = new Location(s.getWorld(), s.getX(), s.getY(), s.getZ(), s.getYaw(), s.getPitch()).add(.5, 1, .5);

                        p.sendMessage(Chat.createLine("&8"));
                        Chat.sendCenteredMessage(p, "&6&lBedWars");
                        Chat.sendCenteredMessage(p, "&eYou have spawned on an island with a bed on it.");
                        Chat.sendCenteredMessage(p, "&eProtect your bed by getting minerals from your generator.");
                        Chat.sendCenteredMessage(p, "&eWith these minerals, you can buy blocks.");
                        Chat.sendCenteredMessage(p, "&eGo to your opponents' island and destroy their bed.");
                        Chat.sendCenteredMessage(p, "&eThe last person alive wins.");
                        Chat.sendCenteredMessage(p, "&eGood luck!");
                        p.sendMessage(Chat.createLine("&8"));

                        p.teleport(loc);
                    }
                }
            }

        }
    }

    public static void manageInventory() {

        for (User u : UserManager.getInstance().getUsers()) {

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

    }

    public static void stopGenerators() {

        for (Generator gen : map.getGens()) {

            Bukkit.getServer().getScheduler().cancelTask(gen.getTimerID());

        }

    }

}
