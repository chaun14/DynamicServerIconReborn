package fr.chaun14.dynamicservericon.commands.carousel;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import fr.chaun14.dynamicservericon.commands.SubCommand;
import fr.chaun14.dynamicservericon.commands.TabCompletableSubCommand;

import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.List;

public class CarouselRemoveCommand implements SubCommand, TabCompletableSubCommand {

    private final DynamicServerIcon plugin;

    public CarouselRemoveCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("§cUsage: /icon carousel remove <filename.png>");
            return;
        }

        String filename = args[2];
        List<String> icons = plugin.getConfig().getStringList("icon-list");

        if (!icons.remove(filename)) {
            sender.sendMessage("§eIcon not found in carousel list: " + filename);
            return;
        }

        plugin.getConfig().set("icon-list", icons);
        plugin.saveConfig();

        plugin.restartCarousel(); // Restart carousel to apply changes

        sender.sendMessage("§aIcon removed from carousel: " + filename);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 3) {
            // List all icons in the carousel config for tab completion
            List<String> icons = plugin.getConfig().getStringList("icon-list");
            return icons;

        } else {
            return List.of();
        }

    }
}