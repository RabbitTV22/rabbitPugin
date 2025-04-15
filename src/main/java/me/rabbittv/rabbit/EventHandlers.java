package me.rabbittv.rabbit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Bukkit.getServer;

public class EventHandlers implements Listener {
    private final Plugin Rabbit;
    private final ConfigurationSection MessagesConfig;
    private final ConfigurationSection Config;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public EventHandlers(Plugin plugin, ConfigurationSection messagesConfig, ConfigurationSection Config) {
        this.Rabbit = plugin;
        this.MessagesConfig = messagesConfig;
        this.Config = Config;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            PotionEffect nightVisionEffect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
            int duration = nightVisionEffect.getDuration();
            int amplifier = nightVisionEffect.getAmplifier();

            player.spigot().respawn();

            getServer().getScheduler().runTaskLater((Plugin) this, () -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, duration, amplifier, false, false));

            }, 1L);
        }
        Bukkit.dispatchCommand(player, "spawn");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            Component joinMsgFirst = miniMessage.deserialize(MessagesConfig.getString("first_join_message", "<red><bold>Welcome %player% to the server for the first time!").replace("%player%", player.getName()));
            e.joinMessage(joinMsgFirst);

            Location teleportLocation = new Location(player.getWorld(), Config.getDouble("x", 2.5), Config.getDouble("y", 67.5), Config.getDouble("z", -5.5));
            player.teleport(teleportLocation);
        } else {
            Component joinMsg = miniMessage.deserialize(MessagesConfig.getString("join_message", "<gray>%player% joined the game.").replace("%player%", player.getName()));
            e.joinMessage(joinMsg);
        }
    }

}