package me.kayoz.bedwars.events;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.game.GameManager;
import me.kayoz.bedwars.game.timers.RespawnTimer;
import me.kayoz.bedwars.managers.TeamManager;
import me.kayoz.bedwars.managers.UserManager;
import me.kayoz.bedwars.managers.UserState;
import me.kayoz.bedwars.objects.Team;
import me.kayoz.bedwars.objects.User;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.utils.ColorManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by KaYoz on 7/27/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class DeathEvent implements Listener {

    @EventHandler
    public void onOtherDeath(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player) {

            Player p = (Player) e.getEntity();

            if (e.getDamage() >= p.getHealth()) {

                e.setCancelled(true);

                User u = UserManager.getUser(p);

                Team team = u.getTeam();

                if (!team.isCanRespawn()) {

                    p.setGameMode(GameMode.SPECTATOR);
                    u.setState(UserState.SPECTATOR);

                    if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 fell into the void and has been &cEliminated&7."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                        TeamManager.getAlive().remove(team);

                        checkEnd();

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 has exploded and has been &cEliminated&7."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                        TeamManager.getAlive().remove(team);

                        checkEnd();

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 fell to their death and has been &cEliminated&7."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                        TeamManager.getAlive().remove(team);

                        checkEnd();

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 burned to death and has been &cEliminated&7."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                        TeamManager.getAlive().remove(team);

                        checkEnd();

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 was shot and has been &cEliminated&7."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                        TeamManager.getAlive().remove(team);

                        checkEnd();
                    }
                } else {

                    RespawnTimer.start(p);

                    p.setGameMode(GameMode.SPECTATOR);

                    if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 fell into the void."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 has exploded."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 fell to their death."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 burned to death."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                    } else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {

                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                + "&7 was shot."));

                        p.setHealth(p.getMaxHealth());
                        p.getInventory().clear();
                        p.getInventory().setArmorContents(null);

                    }
                }
            }

        }

    }

    @EventHandler
    public void onEntityDeath(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {

            Player p = (Player) e.getEntity();

            if (e.getDamage() >= p.getHealth()) {

                e.setCancelled(true);

                User u = UserManager.getUser(p);

                Team team = u.getTeam();

                if (!team.isCanRespawn()) {

                    u.setState(UserState.SPECTATOR);

                    int alive = 0;

                    for (User s : team.getMembers()) {
                        if (s.getState() == UserState.GAME) {
                            alive++;
                        }
                    }

                    if (alive == 0) {
                        Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + ColorManager.getColorName(team.getColorRGB()) + " &7team has been eliminated."));
                        TeamManager.getAlive().remove(team);
                    } else {
                        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {

                            Entity entity = e.getDamager();

                            if (entity instanceof Player) {

                                Player d = (Player) entity;

                                User ud = UserManager.getUser(d);

                                Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                        + "&7 has been eliminated by " + ColorManager.getChatColor(ud.getColor().asRGB()) + d.getName()));

                                p.setHealth(p.getMaxHealth());
                                p.getInventory().clear();
                                p.getInventory().setArmorContents(null);

                            }

                        }
                    }
                    checkEnd();
                } else {

                    RespawnTimer.start(p);

                    p.setGameMode(GameMode.SPECTATOR);

                    if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {

                        Entity entity = e.getDamager();

                        if (entity instanceof Player) {

                            Player d = (Player) entity;

                            User ud = UserManager.getUser(d);

                            Bukkit.getServer().broadcastMessage(Chat.format(ColorManager.getChatColor(team.getColorRGB()) + p.getName()
                                    + "&7 has been killed by " + ColorManager.getChatColor(ud.getColor().asRGB()) + d.getName()));

                            p.setHealth(p.getMaxHealth());
                            p.getInventory().clear();
                            p.getInventory().setArmorContents(null);

                        }

                    }
                }
            }
        }

    }

    public void checkEnd() {

        if (TeamManager.getAlive().size() <= 1) {

            Team win = TeamManager.getAlive().get(0);

            if (TeamManager.getAlive().size() == 0) {
                Bukkit.broadcastMessage("Something went wrong.");
                GameManager.stop();
            }

            Bukkit.broadcastMessage(Chat.format(ColorManager.getChatColor(win.getColorRGB()) + ColorManager.getColorName(win.getColorRGB())
                    + "&e has won the game!"));

            GameManager.stopGenerators();
            MapResetEvents.removeAll();
            Bukkit.getServer().getScheduler().runTaskLater(BedWarsPlugin.getInstance(), new Runnable() {
                @Override
                public void run() {
                    GameManager.stop();
                }
            }, 10 * 20);

        }

    }
}
