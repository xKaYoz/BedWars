package com.furyhcf.bedwars.utils.team;

import com.furyhcf.bedwars.utils.ChatUtils;
import com.furyhcf.bedwars.utils.Color;
import com.furyhcf.bedwars.utils.users.User;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Team {

    @Getter
    private ArrayList<User> members;
    @Getter @Setter
    private int kills;
    @Getter @Setter
    private int deaths;
    @Getter @Setter
    private boolean canRespawn = true;
    @Getter @Setter
    private Block bed;
    @Getter @Setter
    private Inventory backpack;
    @Getter @Setter
    private Color color;

    public Team(User member){
        this.members.add(member);
        backpack = Bukkit.createInventory(null, 27, ChatUtils.format("&cBackpack"));
    }

}
