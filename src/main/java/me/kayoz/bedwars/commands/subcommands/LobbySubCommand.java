package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Created by KaYoz on 7/27/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class LobbySubCommand extends SubCommand {

    @Getter
    private String name = "lobby";
    @Getter
    private String permission = "bedwars.admin.lobby";

    public LobbySubCommand() {
        super("lobby", "bedwars.admin.lobby");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            Files files = new Files();
            if(files.getFile("lobby") == null){
                files.createFile("lobby");
            }
            YamlConfiguration config = files.getConfig("lobby");

            if(args.length == 2 && args[1].equalsIgnoreCase("set")){

                World world = p.getLocation().getWorld();
                double x = p.getLocation().getX();
                double y = p.getLocation().getY();
                double z = p.getLocation().getZ();
                float yaw = p.getLocation().getYaw();
                float pitch = p.getLocation().getPitch();

                config.set("lobby.world", world.getName());
                config.set("lobby.x", x);
                config.set("lobby.y", y);
                config.set("lobby.z", z);
                config.set("lobby.yaw", yaw);
                config.set("lobby.pitch", pitch);

                try {
                    config.save(files.getFile("lobby"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Chat.sendPrefixMessage(p, "&eYou have set the &6Lobby Spawn&e.");
            } else if(args.length == 2 && args[1].equalsIgnoreCase("teleport")){

                World world = Bukkit.getWorld(config.getString("lobby.world"));
                double x = config.getDouble("lobby.x");
                double y = config.getDouble("lobby.y");
                double z = config.getDouble("lobby.z");
                float yaw = (float) config.getDouble("lobby.yaw");
                float pitch = (float) config.getDouble("lobby.pitch");

                p.teleport(new Location(world, x, y, z, yaw, pitch));

                Chat.sendPrefixMessage(p, "&eYou have been teleported to the lobby.");
                return;

            }
        }

    }
}
