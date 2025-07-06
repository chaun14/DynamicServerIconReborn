package fr.chaun14.dynamicservericon.utils;

import org.bukkit.scheduler.BukkitRunnable;

import fr.chaun14.dynamicservericon.DynamicServerIcon;

import java.util.List;

public class IconCarousel extends BukkitRunnable {

    private final DynamicServerIcon plugin;
    private final List<String> iconList;
    private final int intervalSeconds;
    private int currentIndex = 0;

    public IconCarousel(DynamicServerIcon plugin, List<String> iconList, int intervalSeconds) {
        this.plugin = plugin;
        this.iconList = iconList;
        this.intervalSeconds = intervalSeconds;
    }

    @Override
    public void run() {
        if (iconList.isEmpty()) {
            plugin.getDynLogger().warning("Icon list is empty. Carousel stopped.");
            this.cancel();
            return;
        }

        String nextIcon = iconList.get(currentIndex);
        plugin.getConfig().set("current-icon", nextIcon);
        plugin.saveConfig();

        if (plugin.getDebug()) {
            plugin.getDynLogger().debug("Switching server icon to: " + nextIcon);
        }

        currentIndex = (currentIndex + 1) % iconList.size();
    }

    public void start() {
        this.runTaskTimer(plugin, 0L, intervalSeconds * 20L);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
