package fr.chaun14.dynamicservericon.listeners;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PingListener implements Listener {

    private final DynamicServerIcon plugin;
    private final Map<String, CachedServerIcon> iconCache = new HashMap<>();

    public PingListener(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (DynamicServerIcon.DEBUG) {
            plugin.getDynLogger().debug("Server ping received: " + event.getAddress());
        }

        String iconName = plugin.getConfig().getString("current-icon", "default.png");
        File iconFile = new File(plugin.getIconsFolder(), iconName);

        if (DynamicServerIcon.DEBUG) {
            plugin.getDynLogger().debug("Using icon: " + iconFile.getAbsolutePath());
        }

        // Check cache first
        CachedServerIcon cached = iconCache.get(iconName);
        if (cached != null) {
            event.setServerIcon(cached);
            return;
        }

        // If not cached, load and store
        try {
            BufferedImage image = ImageIO.read(iconFile);
            if (image != null) {
                CachedServerIcon icon = plugin.getServer().loadServerIcon(image);
                iconCache.put(iconName, icon);
                event.setServerIcon(icon);
            } else {
                plugin.getDynLogger().warning("Invalid image for icon: " + iconName);
            }
        } catch (Exception e) {
            plugin.getDynLogger().warning("Failed to load server icon: " + e.getMessage());
        }
    }

    public void clearCache(String iconName) {
        iconCache.remove(iconName);
    }

    public void clearAllCache() {
        iconCache.clear();
    }
}
