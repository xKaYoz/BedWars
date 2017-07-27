package me.kayoz.bedwars.utils.team;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.users.User;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Team {

    @Getter
    private ArrayList<User> members = new ArrayList<>();
    @Getter
    @Setter
    private int kills;
    @Getter
    @Setter
    private int deaths;
    @Getter
    @Setter
    private boolean canRespawn = true;
    @Getter
    @Setter
    private Inventory backpack;
    @Getter
    @Setter
    private Color color;
    @Getter
    @Setter
    private int colorRGB;
    @Getter
    @Setter
    private String name;

    public Team(User member) {
        this.members.add(member);
        member.setTeam(this);
        backpack = Bukkit.createInventory(null, 27, Chat.format("&cBackpack"));
        TeamManager.register(this);
    }
}
