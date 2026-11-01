package me.rabbittv.rabbit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class EventHandlers implements Listener {
    private final Plugin Rabbit;
    private final ConfigurationSection MessagesConfig;
    private final ConfigurationSection Config;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public EventHandlers(Plugin plugin, ConfigurationSection messagesConfig, ConfigurationSection Config) {
        this.Rabbit = plugin;
        this.MessagesConfig = messagesConfig;
        this.Config = Config;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            e.joinMessage(mm.deserialize(MessagesConfig.getString("first_join_message", "<red><bold>Welcome %player% to the server for the first time!").replace("%player%", player.getName())));

            Location teleportLocation = new Location(player.getWorld(), Config.getDouble("x", 2.5), Config.getDouble("y", 67.5), Config.getDouble("z", -5.5));
            player.teleport(teleportLocation);
        } else {
            e.joinMessage(mm.deserialize(MessagesConfig.getString("join_message", "<gray>%player% joined the game.").replace("%player%", player.getName())));
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.quitMessage(mm.deserialize(MessagesConfig.getString("leave_message", "<gray>%player% has left the game.").replace("%player%", player.getName())));
    }

}