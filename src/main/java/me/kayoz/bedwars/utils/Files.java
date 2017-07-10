package me.kayoz.bedwars.utils;

import me.kayoz.bedwars.BedWarsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Files {

    private File file;
    private YamlConfiguration config;

    public void createFile(String name) {
        this.file = new File(BedWarsPlugin.getInstance().getDataFolder(), name + ".yml");

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.config = YamlConfiguration.loadConfiguration(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void createFile(String dir, String name) {
        this.file = new File(dir, name + ".yml");

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.config = YamlConfiguration.loadConfiguration(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile(String name) {
        this.file = new File(BedWarsPlugin.getInstance().getDataFolder(), name + ".yml");

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.file = new File(BedWarsPlugin.getInstance().getDataFolder(), name + ".yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.file;
    }

    public File getFile(String dir, String name) {
        this.file = new File(dir, name + ".yml");

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.file = new File(dir, name + ".yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.file;
    }

    public YamlConfiguration getConfig(String name) {
        this.file = new File(BedWarsPlugin.getInstance().getDataFolder(), name + ".yml");

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.file = new File(BedWarsPlugin.getInstance().getDataFolder(), name + ".yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
        return this.config;
    }

    public YamlConfiguration getConfig(String dir, String name) {
        this.file = new File(dir, name + ".yml");

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
        return this.config;
    }

}
