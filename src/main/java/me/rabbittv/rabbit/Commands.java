package me.rabbittv.rabbit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Commands implements CommandExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();

    Rabbit plugin = Rabbit.getPlugin(Rabbit.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player player) {
                Location teleportLocation = new Location(player.getWorld(), plugin.Config.getDouble("x", 2.5), plugin.Config.getDouble("y", 67.5), plugin.Config.getDouble("z", -5.5));
                player.teleport(teleportLocation);
                player.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("teleport_to_spawn", "<gold>Teleporting to spawn...")));
                return true;
            } else {
                sender.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("not_a_player", "<dark_red>You have to be a player to use that command.")));
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("website")) {
            sender.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("website", "<red><b>Visit the website<br><click:open_url:https://www.rabbit-network.net>www.rabbit-network.net")));
            return true;
        } else if (command.getName().equalsIgnoreCase("nv")) {
            if (sender instanceof Player player) {

                if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    player.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("nv_remove", "<blue>You removed you night vision effect.")));
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                    player.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("nv_add", "<blue>You now have night vision.")));

                }
                return true;
            } else {
                sender.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("not_a_player", "<dark_red>You have to be a player to use that command.")));
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("rabbitreload") && sender.hasPermission("rabbit.reload")) {
            plugin.reloadConfig();
            plugin.Config = plugin.getConfig().getConfigurationSection("spawn_coords");
            plugin.MessagesConfig = plugin.getConfig().getConfigurationSection("messages");
            if (plugin.Config == null || plugin.MessagesConfig == null) {
                plugin.getLogger().warning("Failed to reload config: missing sections.");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return true;
            }
            sender.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("config_reload", "<green>Config has been reloaded.")));
            return true;
        } else if (command.getName().equalsIgnoreCase("map")) {
            sender.sendMessage(mm.deserialize(plugin.MessagesConfig.getString("map", "<gradient:#1535EF:#04ACEE><click:open_url:'https://map.rabbit-network.net'>View the map at <i><u>map.rabbit-network.net</click>")));
            return true;
        }
        return false;
    }
}
