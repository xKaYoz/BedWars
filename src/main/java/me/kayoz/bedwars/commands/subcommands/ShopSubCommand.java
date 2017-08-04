package me.kayoz.bedwars.commands.subcommands;

import lombok.Getter;
import me.kayoz.bedwars.commands.SubCommand;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.chat.Chat;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.maps.MapManager;
import me.kayoz.bedwars.utils.shops.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Created by KaYoz on 7/28/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class ShopSubCommand extends SubCommand {

    @Getter
    public String name = "shop";
    @Getter
    public String permission = "bedwars.admin.shop";


    public ShopSubCommand() {
        super("shop", "bedwars.admin.shop");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args.length == 3 && args[1].equalsIgnoreCase("create")) {

                Map map = MapManager.getMap(args[2]);

                if (map == null) {
                    Chat.sendPrefixMessage(p, "&cThere is not a map with that name.");
                    return;
                }

                Shop shop = new Shop(p.getLocation());
                map.addShop(shop);

                Files files = new Files();
                files.createFile("maps/" + map.getName() + "/shops", String.valueOf(map.getShops().size()));
                YamlConfiguration config = files.getConfig("maps/" + map.getName() + "/shops", String.valueOf(map.getShops().size()));


                for (java.util.Map.Entry<String, Object> o : shop.serialize().entrySet()) {
                    config.set(o.getKey(), o.getValue());
                }

                try {
                    config.save(files.getFile("maps/" + map.getName() + "/shops", String.valueOf(map.getShops().size())));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Chat.sendPrefixMessage(p, "&eYou have created a &6Shop&e for the map &6" + map.getName() + "&e.");

            }

        }

    }
}
