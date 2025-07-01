package fr.chaun14.dynamicservericon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import fr.chaun14.dynamicservericon.commands.IconTabCompleter;
import fr.chaun14.dynamicservericon.listeners.CommandListener;
import fr.chaun14.dynamicservericon.listeners.PingListener;
import fr.chaun14.dynamicservericon.utils.ConfigValidator;
import fr.chaun14.dynamicservericon.utils.DynIconLogger;
import fr.chaun14.dynamicservericon.utils.IconCarousel;
import fr.chaun14.dynamicservericon.utils.Metrics;

public class DynamicServerIcon extends JavaPlugin {

    public static Boolean DEBUG = false;

    public IconCarousel iconCarousel;
    private DynIconLogger logger;

    // startcarousel function
    public void startCarousel(List<String> icons, int interval) {
        if (iconCarousel != null) {
            this.stopCarousel();
        }
        iconCarousel = new IconCarousel(this, icons, interval);
        iconCarousel.start();
    }

    // stopcarousel function
    public void stopCarousel() {
        if (iconCarousel != null) {
            iconCarousel.cancel();
            iconCarousel = null;
        }
    }

    public void restartCarousel() {
        stopCarousel();
        List<String> icons = getConfig().getStringList("icon-list");
        int interval = getConfig().getInt("interval", 10);
        startCarousel(icons, interval);
    }

    public IconCarousel getIconCarousel() {
        return iconCarousel;
    }

    @Override
    public void onEnable() {

        this.logger = new DynIconLogger(getLogger(), "[DynIcon] "); // Create the plugin data folder if it doesn't
                                                                    // exist

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Save default config.yml if it doesn't exist
        saveDefaultConfig();

        // Create the "icons" subfolder inside the plugin folder
        File iconsFolder = new File(getDataFolder(), "icons");
        if (!iconsFolder.exists()) {
            iconsFolder.mkdirs();
        }

        // Copy the default icon to the icons folder if it doesn't exist
        File defaultIcon = new File(iconsFolder, "default.png");
        if (!defaultIcon.exists()) {
            try (InputStream in = getResource("default.png")) {
                if (in != null) {
                    Files.copy(in, defaultIcon.toPath());
                    getDynLogger().info("Copied default icon to icons/default.png");
                } else {
                    getDynLogger().warning("default.png was not found in resources.");
                }
            } catch (IOException e) {
                getDynLogger().warning("Failed to copy default icon: " + e.getMessage());
            }
        }

        // Validate the configuration on startup
        ConfigValidator.validateConfigOnStartup(this);

        if (getConfig().getString("mode", "manual").equalsIgnoreCase("carousel")) {
            List<String> icons = getConfig().getStringList("icon-list");
            int interval = getConfig().getInt("interval", 10);
            iconCarousel = new IconCarousel(this, icons, interval);
            iconCarousel.start();
        }

        CommandListener commandListener = new CommandListener(this);
        getCommand("dynicon").setExecutor(commandListener);
        getCommand("dynicon").setTabCompleter(new IconTabCompleter(commandListener));
        getCommand("icon").setExecutor(commandListener);
        getCommand("icon").setTabCompleter(new IconTabCompleter(commandListener));

        getServer().getPluginManager().registerEvents(new PingListener(this), this);
        getDynLogger().info("DynamicServerIcon enabled!");

        int pluginId = 26327; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

    }

    @Override
    public void onDisable() {

        if (iconCarousel != null) {
            iconCarousel.cancel();
        }

        getDynLogger().info("DynamicServerIcon disabled!");

    }

    public String getIconsFolder() {
        return new File(getDataFolder(), "icons").getAbsolutePath();
    }

    public DynIconLogger getDynLogger() {
        return logger;
    }

    public boolean getDebug() {
        return DEBUG;
    }

}
