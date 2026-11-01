package me.rabbittv.rabbit;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
        Commands commands = new Commands();
        this.getCommand("spawn").setExecutor(commands);
        this.getCommand("website").setExecutor(commands);
        this.getCommand("nv").setExecutor(commands);
        this.getCommand("rabbitreload").setExecutor(commands);
        this.getCommand("map").setExecutor(commands);
    }
}