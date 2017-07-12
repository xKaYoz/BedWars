package me.kayoz.bedwars;

import me.kayoz.bedwars.events.LobbyEvents;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.utils.game.GameState;
import me.kayoz.bedwars.utils.users.UserManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class BedWarsPlugin extends JavaPlugin {

    @Getter
    private static BedWarsPlugin instance;
    @Getter @Setter
    private GameState state;

    @Override
    public void onEnable() {
        instance = this;
        registerListeners();
        state = GameState.LOBBY;
        new UserManager();
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                checkFiles();
            }
        }, 20);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void registerListeners(){
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new LobbyEvents(), this);
    }

    private void checkFiles(){
        Files files = new Files();

        if(files.getFile("config") == null){
            files.createFile("config");
            YamlConfiguration config = files.getConfig("config");
            config.set("MaxPlayers", 8);

            try {
                config.save(files.getFile("config"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
