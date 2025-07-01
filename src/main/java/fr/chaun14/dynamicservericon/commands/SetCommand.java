package fr.chaun14.dynamicservericon.commands;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import fr.chaun14.dynamicservericon.utils.ImageValidator;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SetCommand implements SubCommand, TabCompletableSubCommand {

    private final DynamicServerIcon plugin;

    public SetCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§cUsage: /icon set <filename.png>");
            return;
        }

        String filename = args[1];
        File iconFile = new File(plugin.getIconsFolder(), filename);

        String validationError = ImageValidator.validateIconFile(iconFile);
        if (validationError != null) {
            sender.sendMessage("§c" + validationError);
            return;
        }

        // if we are in carousel mode, we need to stop it first and go back to manual
        // mode
        if (plugin.getConfig().getString("mode", "manual").equals("carousel")) {
            plugin.getConfig().set("mode", "manual");
            plugin.saveConfig();
            plugin.stopCarousel();
            sender.sendMessage(
                    "§eCarousel mode stopped. Switching to manual mode. If you want to add this icon to the carousel, use /icon carousel add "
                            + filename);
        }

        plugin.getConfig().set("current-icon", filename);
        plugin.saveConfig();

        sender.sendMessage("§aIcon successfully set to: " + filename);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String iconsFolderPath = plugin.getIconsFolder();
            File iconsFolder = new File(iconsFolderPath);
            String[] files = iconsFolder.list((dir, name) -> name.toLowerCase().endsWith(".png"));
            if (files != null) {
                String currentIcon = plugin.getConfig().getString("current-icon", "");
                return Arrays.stream(files)
                        .filter(name -> !name.equalsIgnoreCase(currentIcon))
                        .toList();
            }
        }
        return List.of();
    }
}
