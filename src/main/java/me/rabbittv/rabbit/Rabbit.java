package me.rabbittv.rabbit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class Rabbit extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println("Success!");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new EventHandlers(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location teleportLocation = new Location(player.getWorld(), 2.5, 67.5, -5.5);
                player.teleport(teleportLocation);
                player.sendMessage(ChatColor.GOLD + "Teleporting to spawn...");
                return true;
            } else {
                sender.sendMessage("You need to be in-game to use that");
            }
        } else if (command.getName().equalsIgnoreCase("website")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Visit the website!\n" + ChatColor.RED + "https://www.rabbit-network.net");
            } else if (sender instanceof ConsoleCommandSender) {
                System.out.println("You need to be in-game to use that");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("discord")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Join the discord!\n" + ChatColor.RED + "https://discord.gg/AGnu449cMf");
            } else if (sender instanceof ConsoleCommandSender) {
                System.out.println("You need to be in-game to use that");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("nv")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Check if the player already has night vision
                if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    // If they have night vision, remove it
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    player.sendMessage(ChatColor.BLUE+ "You removed your night vision effect");
                } else {
                    // If they don't have night vision, give it to them
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                    player.sendMessage(ChatColor.BLUE+ "You now have night vision");
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            }
        }
        return false;
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
            getServer().getScheduler().runTaskLater(this, () -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, duration, amplifier, false, false));

            }, 1L);
        }

        // Use Bukkit.dispatchCommand to run the /spawn command for the player
        Bukkit.dispatchCommand(player, "spawn");
    }

    @Override
    public void onDisable() {
        System.out.println("Closed!");
    }
}
