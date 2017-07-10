package me.kayoz.bedwars;

import me.kayoz.bedwars.utils.game.GameState;
import me.kayoz.bedwars.utils.users.UserManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedWarsPlugin extends JavaPlugin {

    @Getter
    private static BedWarsPlugin instance;
    @Getter @Setter
    private GameState state;

    @Override
    public void onEnable() {
        instance = this;
        state = GameState.LOBBY;
        new UserManager();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
