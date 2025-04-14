package me.rabbittv.rabbit;

import org.bukkit.Bukkit;
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


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            Bukkit.dispatchCommand(player, "spawn");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Check if the player has the NIGHT_VISION effect
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            // Store the duration and amplifier of the NIGHT_VISION effect
            PotionEffect nightVisionEffect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
            int duration = nightVisionEffect.getDuration();
            int amplifier = nightVisionEffect.getAmplifier();

            // Respawn the player immediately
            player.spigot().respawn();

            // Add a delay before applying the NIGHT_VISION effect to ensure it persists through respawn
            getServer().getScheduler().runTaskLater((Plugin) this, () -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, duration, amplifier, false, false));

            }, 1L);
        }
        Bukkit.dispatchCommand(player, "spawn");
    }
}