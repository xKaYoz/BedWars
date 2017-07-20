package me.kayoz.bedwars.commands;

import me.kayoz.bedwars.BedWarsPlugin;
import me.kayoz.bedwars.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class BedWarsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (args.length == 0) {
            return false;
        }

        SubCommand subCommand = SubCommandManager.getInstance().find(args[0]);

        if (subCommand == null) {
            return false;
        }

        if (sender.hasPermission(subCommand.getPermission())) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage(ChatUtils.format("&cYou do not have permission to execute this command."));
        }


        return false;
    }
}
