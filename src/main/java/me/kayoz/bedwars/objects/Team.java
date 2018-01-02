package me.kayoz.bedwars.objects;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.utils.Chat;
import me.kayoz.bedwars.managers.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Team {

    private String name;
    private ArrayList<User> members = new ArrayList<>();
    private boolean canRespawn = false;
    private Color color;
    private int colorRGB;
    private ArrayList<Location> bed = new ArrayList<>();

    public Team(User member) {
        this.members.add(member);
        member.setTeam(this);
        TeamManager.register(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public boolean isCanRespawn() {
        return canRespawn;
    }

    public void setCanRespawn(boolean canRespawn) {
        this.canRespawn = canRespawn;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getColorRGB() {
        return colorRGB;
    }

    public void setColorRGB(int colorRGB) {
        this.colorRGB = colorRGB;
    }

    public ArrayList<Location> getBed() {
        return bed;
    }

    public void setBed(ArrayList<Location> bed) {
        this.bed = bed;
    }
}
