package fr.chaun14.dynamicservericon.utils;

import fr.chaun14.dynamicservericon.DynamicServerIcon;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ConfigValidator {

    public static void validateConfigOnStartup(DynamicServerIcon plugin) {
        plugin.getDynLogger().info("Validating icon configuration...");

        String iconsFolderPath = plugin.getIconsFolder();
        File iconsFolder = new File(iconsFolderPath);

        // Validate current icon
        String currentIcon = plugin.getConfig().getString("current-icon", "default.png");
        File currentIconFile = new File(iconsFolder, currentIcon);
        String error = ImageValidator.validateIconFile(currentIconFile);
        if (error != null) {
            plugin.getDynLogger().warning(
                    "Invalid current-icon in config: " + currentIcon + " (" + error + "). Reverting to default.png.");
            plugin.getConfig().set("current-icon", "default.png");
        }

        // Validate icon-list
        List<String> iconList = plugin.getConfig().getStringList("icon-list");
        Iterator<String> iterator = iconList.iterator();
        while (iterator.hasNext()) {
            String filename = iterator.next();
            File iconFile = new File(iconsFolder, filename);
            String validationError = ImageValidator.validateIconFile(iconFile);
            if (validationError != null) {
                plugin.getDynLogger().warning(
                        "Invalid carousel icon: " + filename + " (" + validationError + "). Removing from list.");
                iterator.remove();
            }
        }

        plugin.getConfig().set("icon-list", iconList);
        plugin.saveConfig();

        plugin.getDynLogger().info("Icon configuration validated successfully.");
    }
}
