package me.rabbittv.rabbit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Bukkit.getServer;

public class EventHandlers implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            event.setJoinMessage(ChatColor.DARK_GRAY + player.getDisplayName() + ChatColor.DARK_GRAY + " has reappeared");
        } else {
            event.setJoinMessage(ChatColor.DARK_PURPLE + "Welcome to the server, " + ChatColor.DARK_PURPLE + player.getDisplayName());
            Bukkit.dispatchCommand(player, "spawn");
        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.RED + player.getDisplayName() + " has disappeared");
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // Get the player who respawned
        Player player = event.getPlayer();

        // Set the respawn location to the desired coordinates
        Location respawnLocation = new Location(player.getWorld(), 2.5, 67.5, -6.5);

        // Teleport the player to the respawn location
        player.teleport(respawnLocation);
    }



}