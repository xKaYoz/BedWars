package me.kayoz.bedwars.commands;

import me.kayoz.bedwars.commands.subcommands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class SubCommandManager {

    private static SubCommandManager ourInstance = new SubCommandManager();
    private Map<String, SubCommand> subcommands = new HashMap<>();

    private SubCommandManager() {
        subcommands.put("map", new MapSubCommand());
        subcommands.put("generator", new GeneratorSubCommand());
        subcommands.put("spawn", new SpawnSubCommand());
        subcommands.put("help", new HelpSubCommand());
        subcommands.put("lobby", new LobbySubCommand());
        subcommands.put("adminmode", new AdminSubCommand());
        subcommands.put("shop", new ShopSubCommand());
    }

    public static SubCommandManager getInstance() {
        return ourInstance;
    }

    public SubCommand find(String command) {
        return subcommands.get(command);
    }
}
