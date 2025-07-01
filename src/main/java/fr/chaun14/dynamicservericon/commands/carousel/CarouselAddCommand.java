package fr.chaun14.dynamicservericon.commands.carousel;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import fr.chaun14.dynamicservericon.commands.SubCommand;
import fr.chaun14.dynamicservericon.commands.TabCompletableSubCommand;
import fr.chaun14.dynamicservericon.utils.ImageValidator;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.List;

public class CarouselAddCommand implements SubCommand, TabCompletableSubCommand {

    private final DynamicServerIcon plugin;

    public CarouselAddCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("§cUsage: /icon carousel add <filename.png>");
            return;
        }

        String filename = args[2];
        File iconFile = new File(plugin.getIconsFolder(), filename);

        // Validate the icon
        String validationError = ImageValidator.validateIconFile(iconFile);
        if (validationError != null) {
            sender.sendMessage("§c" + validationError);
            return;
        }

        List<String> icons = plugin.getConfig().getStringList("icon-list");
        if (icons.contains(filename)) {
            sender.sendMessage("§eThis icon is already in the carousel.");
            return;
        }

        icons.add(filename);
        plugin.getConfig().set("icon-list", icons);
        plugin.saveConfig();

        String enableMessage = "";

        // if the mode is manual we prompt the user to switch to carousel mode
        if (plugin.getConfig().getString("mode", "manual").equals("manual"))
            enableMessage = "§eYou are currently in manual mode. To start the carousel, use /icon mode carousel.";
        else
            plugin.restartCarousel(); // Restart carousel to apply changes

        sender.sendMessage("§aIcon added to carousel: " + filename + ". " + enableMessage);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 3) {
            String iconsFolderPath = plugin.getIconsFolder();
            File iconsFolder = new File(iconsFolderPath);
            String[] files = iconsFolder.list((dir, name) -> name.toLowerCase().endsWith(".png"));
            if (files != null) {
                List<String> alreadyAdded = plugin.getConfig().getStringList("icon-list");
                return java.util.Arrays.stream(files)
                        .filter(f -> !alreadyAdded.contains(f))
                        .toList();
            }
        }
        return List.of();
    }
}
