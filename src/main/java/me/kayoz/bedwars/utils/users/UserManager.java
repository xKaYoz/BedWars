package me.kayoz.bedwars.utils.users;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class UserManager {
    @Getter
    private static UserManager instance;
    @Getter
    private ArrayList<User> users = new ArrayList<>();

    public UserManager() {
        instance = this;
    }

    public User getUser(Player p) {
        for (User u : users) {
            if (u.getPlayer() == p) {
                return u;
            }
        }
        return null;
    }

    public void register(User u) {
        if (!users.contains(u)) {
            users.add(u);
        }
    }

    public void unregister(User u) {
        if (users.contains(u)) {
            users.remove(u);
        }
    }
}
