package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.chat.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by KaYoz on 7/24/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class HelpSubCommand extends SubCommand {

    @Getter
    private String name = "help";
    @Getter
    private String permission = "bedwars.admin";

    public HelpSubCommand() {
        super("help", "bedwars.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            p.sendMessage(Chat.createLine("&8"));
            Chat.sendColoredMessage(p, "&6&lBedwars Help &7(Page 1/1)");
            Chat.sendColoredMessage(p, "   &e/bw map &8- &7Command to manage the maps");
            Chat.sendColoredMessage(p, "   &e/bw generator &8- &7Command to manage the generators");
            Chat.sendColoredMessage(p, "   &e/bw spawn &8- &7Command to manage the spawn points");
            p.sendMessage(Chat.createLine("&8"));

        } else {
            Chat.sendPrefixMessage(sender, "&cYou must be a player to execute this command.");
        }
    }
}
