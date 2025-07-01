package fr.chaun14.dynamicservericon.commands;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand implements SubCommand {

    private final DynamicServerIcon plugin;

    public ReloadCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();

        String mode = plugin.getConfig().getString("mode", "manual").toLowerCase();

        if (mode.equals("carousel")) {
            List<String> icons = plugin.getConfig().getStringList("icon-list");
            int interval = plugin.getConfig().getInt("interval", 10);

            if (icons.isEmpty()) {
                sender.sendMessage("§cThe icon list is empty. Carousel cannot start.");
                return;
            }

            plugin.startCarousel(icons, interval);
        } else {
            plugin.stopCarousel();

        }

        sender.sendMessage("§aConfiguration reloaded.");
    }
}
