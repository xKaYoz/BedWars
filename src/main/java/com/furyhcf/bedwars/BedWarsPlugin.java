package com.furyhcf.bedwars;

import com.furyhcf.bedwars.utils.game.GameState;
import com.furyhcf.bedwars.utils.users.UserManager;
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
