package fr.chaun14.dynamicservericon.commands;

import org.bukkit.command.CommandSender;

import fr.chaun14.dynamicservericon.DynamicServerIcon;

import java.util.Collections;
import java.util.List;

public class ModeCommand implements SubCommand, TabCompletableSubCommand {

    private final DynamicServerIcon plugin;

    public ModeCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§cUsage: /icon mode <manual | carousel>");
            return;
        }

        String mode = args[1].toLowerCase();

        if (!mode.equals("manual") && !mode.equals("carousel")) {
            sender.sendMessage("§cInvalid mode. Use 'manual' or 'carousel'.");
            return;
        }

        plugin.getConfig().set("mode", mode);
        plugin.saveConfig();

        if (mode.equals("carousel")) {
            List<String> icons = plugin.getConfig().getStringList("icon-list");
            int interval = plugin.getConfig().getInt("interval", 10);

            // Check if the icon list is empty
            if (icons.isEmpty()) {
                sender.sendMessage(
                        "§cThe icon list is empty. Carousel cannot start. Add icons using /icon carousel add <filename.png>.");
                return;
            }

            plugin.startCarousel(icons, interval);
        } else {
            plugin.stopCarousel();
        }

        sender.sendMessage("§aIcon mode set to: " + mode.toUpperCase());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return List.of("manual", "carousel");
        } else
            return Collections.emptyList();
    }
}
