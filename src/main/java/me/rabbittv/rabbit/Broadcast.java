package me.rabbittv.rabbit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class Broadcast implements Runnable {
    public final Plugin plugin;
    private final ConfigurationSection BroadcastConfig;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public Broadcast(Plugin plugin, ConfigurationSection BroadcastConfig) {
        this.plugin = plugin;
        this.BroadcastConfig = BroadcastConfig;
    }
    public void run(){
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            String message = BroadcastConfig.getString("message_1", "<gold>Annoucement.");
            Component Parsed = miniMessage.deserialize(message);
            Bukkit.broadcast(Parsed);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Broadcast(plugin, BroadcastConfig));
        }
    }

}
