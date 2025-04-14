package me.rabbittv.rabbit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class Rabbit extends JavaPlugin implements Listener {

    private ConfigurationSection Config;
    private ConfigurationSection MessagesConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        Config = config.getConfigurationSection("spawn_coords");
        MessagesConfig = config.getConfigurationSection("messages");
        if (Config == null || Config == null) {
            getLogger().warning("Configuration sections not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Sigma!");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new EventHandlers(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location teleportLocation = new Location(player.getWorld(), Config.getDouble("x", 2.5), Config.getDouble("y", 67.5), Config.getDouble("z", -5.5));
                player.teleport(teleportLocation);
                var Spawn = MiniMessage.miniMessage();
                Component Parsed = Spawn.deserialize(MessagesConfig.getString("teleport_to_spawn", "<gold>Teleporting to spawn..."));
                player.sendMessage(String.valueOf(Parsed));
                return true;
            } else {
                sender.sendMessage("You need to be in-game to use that");
            }
        } else if (command.getName().equalsIgnoreCase("website")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Visit the website!\n" + ChatColor.RED + "https://www.rabbit-network.net");
            } else if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage("You need to be in-game to use that");
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


    @Override
    public void onDisable() {
        getLogger().info("Shut down");
    }
}
