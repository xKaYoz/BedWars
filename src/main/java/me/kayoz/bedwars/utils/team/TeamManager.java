package me.kayoz.bedwars.utils.team;

import lombok.Getter;
import me.kayoz.bedwars.utils.users.User;
import org.bukkit.Color;

import java.util.ArrayList;

/**
 * Created by KaYoz on 7/23/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class TeamManager {

    @Getter
    private static ArrayList<Team> teams = new ArrayList<>();
    @Getter
    private static ArrayList<Team> alive = new ArrayList<>();

    public static Team getTeam(Color color) {

        for (Team team : teams) {

            if (team.getColor() == color) {
                return team;
            }

        }
        return null;
    }

    public static Team getTeam(User u) {

        for (Team team : teams) {

            if (team.getMembers().contains(u)) {
                return team;
            }
        }
        return null;
    }

    public static void register(Team team) {
        if (!teams.contains(team)) {
            teams.add(team);
            alive.add(team);
        }
    }

    public static void unregister(Team team) {
        if (teams.contains(team)) {
            teams.remove(team);
        }
        if (!alive.contains(team)) {
            alive.remove(team);
        }
    }
}
