package me.kayoz.bedwars.commands;

import org.bukkit.command.CommandSender;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public abstract class SubCommand {

    private final String name;
    private final String permission;

    public SubCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public abstract String getName();

    public abstract String getPermission();
}
