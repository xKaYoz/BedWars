package me.kayoz.bedwars;

import me.kayoz.bedwars.commands.BedWarsCommand;
import me.kayoz.bedwars.events.LobbyEvents;
import me.kayoz.bedwars.utils.ChatUtils;
import me.kayoz.bedwars.utils.Files;
import me.kayoz.bedwars.game.GameState;
import me.kayoz.bedwars.utils.maps.Map;
import me.kayoz.bedwars.utils.users.UserManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BedWarsPlugin extends JavaPlugin {

    @Getter
    private static BedWarsPlugin instance;
    @Getter @Setter
    private GameState state;

    @Override
    public void onEnable() {
        instance = this;
        registerListeners();
        registerCommands();
        state = GameState.LOBBY;
        loadMaps();
        new UserManager();
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                checkFiles();
            }
        }, 20);
    }

    private void loadMaps(){

        File folder = new File(this.getDataFolder() + File.separator + "maps");
        File[] fileList = folder.listFiles();
        Files files = new Files();

        if(!(folder == null && fileList == null)){

            for(File file : fileList){

                String fileName = file.getName().replace(".yml", "");

                YamlConfiguration config = files.getConfig("maps", fileName);

                Map map = (Map) config.get("Map");

                Bukkit.broadcastMessage(ChatUtils.formatWithPrefix("Map " + fileName + " has been loaded!"));

            }

        }

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void registerCommands(){
        this.getCommand("bw").setExecutor(new BedWarsCommand());
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
