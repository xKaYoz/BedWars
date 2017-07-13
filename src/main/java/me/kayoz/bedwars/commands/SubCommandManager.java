package me.kayoz.bedwars.commands;

import me.kayoz.bedwars.commands.subcommands.MapSubCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class SubCommandManager {

    private static SubCommandManager ourInstance = new SubCommandManager();

    public static SubCommandManager getInstance(){
        return ourInstance;
    }

    private SubCommandManager(){
        subcommands.put("map", new MapSubCommand());
    }

    public SubCommand find(String command) {
        return  subcommands.get(command);
    }

    private Map<String, SubCommand> subcommands = new HashMap<>();
}