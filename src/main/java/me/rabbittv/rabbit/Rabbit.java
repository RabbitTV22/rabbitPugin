package me.rabbittv.rabbit;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
    public ConfigurationSection MessagesConfig;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        Config = config.getConfigurationSection("spawn_coords");
        MessagesConfig = config.getConfigurationSection("messages");
        if (Config == null || MessagesConfig == null) {
            getLogger().warning("Configuration sections not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Sigma!");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new EventHandlers(this, MessagesConfig, Config), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location teleportLocation = new Location(player.getWorld(), Config.getDouble("x", 2.5), Config.getDouble("y", 67.5), Config.getDouble("z", -5.5));
                player.teleport(teleportLocation);
                Audience p = (Audience) sender;
                Component Parsed = miniMessage.deserialize(MessagesConfig.getString("teleport_to_spawn", "<gold>Teleporting to spawn..."));
                p.sendMessage(Parsed);
                return false;
            } else {
                Audience p = (Audience) sender;
                Component Parsed = miniMessage.deserialize(MessagesConfig.getString("not_a_player", "<dark_red>You have to be a player to use that command."));
                p.sendMessage(Parsed);
            }
        } else if (command.getName().equalsIgnoreCase("website")) {
            if (sender instanceof Player) {
                Audience p = (Audience) sender;
                Component Parsed = miniMessage.deserialize(MessagesConfig.getString("website", "<red><b>Visit the website<br><click:open_url:https://www.rabbit-network.net>www.rabbit-network.net"));
                p.sendMessage(Parsed);
            } else if (sender instanceof ConsoleCommandSender) {
                Audience p = (Audience) sender;
                Component Parsed = miniMessage.deserialize(MessagesConfig.getString("not_a_player", "<dark_red>You have to be a player to use that command."));
                p.sendMessage(Parsed);
            }
            return false;
        } else if (command.getName().equalsIgnoreCase("nv")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Audience p = (Audience) sender;

                // Check if the player already has night vision
                if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    // If they have night vision, remove it
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    Component Parsed = miniMessage.deserialize(MessagesConfig.getString("nv_remove", "<blue>You removed you night vision effect."));
                    p.sendMessage(Parsed);
                } else {
                    // If they don't have night vision, give it to them
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                    Component Parsed = miniMessage.deserialize(MessagesConfig.getString("nv_add", "<blue>You now have night vision."));
                    p.sendMessage(Parsed);
                }
                return false;
            } else {
                Audience p = (Audience) sender;
                Component Parsed = miniMessage.deserialize(MessagesConfig.getString("not_a_player", "<dark_red>You have to be a player to use that command."));
                p.sendMessage(Parsed);
            }
        }
        return false;
    }


    @Override
    public void onDisable() {
        getLogger().info("Shut down");
    }
}
