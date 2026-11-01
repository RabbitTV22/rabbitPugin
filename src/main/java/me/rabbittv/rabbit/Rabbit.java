package me.rabbittv.rabbit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;

public final class Rabbit extends JavaPlugin implements Listener {

    public ConfigurationSection Config;
    public ConfigurationSection MessagesConfig;
    private final MiniMessage mm = MiniMessage.miniMessage();

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
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new EventHandlers(this, MessagesConfig, Config), this);
        Commands commandsClass = new Commands();
        String[] commands = {"spawn","website","nv","rabbitreload","map"};
        for (String command : commands) {
            this.getCommand(command).setExecutor(commandsClass);
        }
    }
}