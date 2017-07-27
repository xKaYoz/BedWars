package me.kayoz.bedwars.utils;

import me.kayoz.bedwars.utils.chat.Chat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by KaYoz on 7/10/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class ItemBuilder {
    public static ItemStack build(Material material, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Chat.format(lore));
        meta.setDisplayName(Chat.format(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack build(Material material, int amount, int id, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) id);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Chat.format(lore));
        meta.setDisplayName(Chat.format(name));
        item.setItemMeta(meta);
        return item;
    }
}
