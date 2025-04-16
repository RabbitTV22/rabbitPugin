package me.rabbittv.rabbit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class Broadcast implements Runnable {
    public final Plugin plugin;
    private final String message;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public Broadcast(Plugin plugin, ConfigurationSection BroadcastConfig) {
        this.plugin = plugin;
        this.message = BroadcastConfig.getString("message", "<gray>default message");
    }
    public void run(){
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            Component Parsed = miniMessage.deserialize(message);
            Bukkit.broadcast(Parsed);
           // Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Broadcast(plugin, BroadcastConfig));
        }
    }

}
