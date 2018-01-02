package me.kayoz.bedwars.objects;

import lombok.Getter;
import lombok.Setter;
import me.kayoz.bedwars.managers.UserManager;
import me.kayoz.bedwars.managers.UserState;
import org.bukkit.Color;
import org.bukkit.entity.Player;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class User {

    private Player player;
    private int kills;
    private int deaths;
    private Team team;
    private UserState state;
    private Color color;

    public User(Player p) {
        this.player = p;
        UserManager.register(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
