package com.furyhcf.bedwars;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedWarsPlugin extends JavaPlugin {

    @Getter
    private static BedWarsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
