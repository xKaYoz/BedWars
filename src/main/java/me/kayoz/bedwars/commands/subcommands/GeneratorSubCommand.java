package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.inventories.AddGenInv;
import me.kayoz.bedwars.inventories.AllGensInv;
import me.kayoz.bedwars.managers.MapManager;
import me.kayoz.bedwars.objects.Map;
import me.kayoz.bedwars.utils.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by KaYoz on 7/17/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class GeneratorSubCommand extends SubCommand {
    @Getter
    private String name = "generator";
    @Getter
    private String permission = "bedwars.admin.generator";

    public GeneratorSubCommand() {
        super("generator", "bedwars.admin.generator");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args.length == 3 && args[1].equalsIgnoreCase("list")) {
                Map map = MapManager.getMap(args[2]);

                if (map == null) {
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

                AllGensInv.create(p, map);

                return;
            } else if (args.length == 3 && args[1].equalsIgnoreCase("create")) {
                Map map = MapManager.getMap(args[2]);

                if (map == null) {
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

                AddGenInv.open(p, map);

                return;
            } else if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {

                Map map = MapManager.getMap(args[2]);

                if (map == null) {
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

            } else {
                p.sendMessage(Chat.createLine("&8"));
                Chat.sendColoredMessage(p, "&6&lGenerator Help &7(Page 1/1)");
                Chat.sendColoredMessage(p, "  &e/bw generator help &8- &7Displays this help menu.");
                Chat.sendColoredMessage(p, "  &e/bw generator list <name> &8- &7A list of all generators created within the map specified.");
                Chat.sendColoredMessage(p, "  &e/bw generator create <name> &8- &7Create a generator within the map specified.");
                Chat.sendColoredMessage(p, "  &e/bw generator remove <name> &8- &7Remove a generator within the map specified.");
                p.sendMessage(Chat.createLine("&8"));
            }

        }

    }
}
