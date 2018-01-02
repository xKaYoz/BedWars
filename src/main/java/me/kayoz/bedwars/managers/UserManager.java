package me.kayoz.bedwars.managers;

import lombok.Getter;
import me.kayoz.bedwars.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class UserManager {
    private static ArrayList<User> users = new ArrayList<>();

    public static User getUser(Player p) {
        for (User u : users) {
            if (u.getPlayer() == p) {
                return u;
            }
        }
        return null;
    }

    public static void register(User u) {
        if (!users.contains(u)) {
            users.add(u);
        }
    }

    public static void unregister(User u) {
        if (users.contains(u)) {
            users.remove(u);
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void createUsers() {

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {

            if (getUser(p) == null) {

                User u = new User(p);

                u.setState(UserState.LOBBY);

            }

            p.setGameMode(GameMode.SURVIVAL);

        }

    }
}
