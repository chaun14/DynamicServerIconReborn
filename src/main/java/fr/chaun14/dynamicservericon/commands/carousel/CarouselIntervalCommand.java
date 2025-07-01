package fr.chaun14.dynamicservericon.commands.carousel;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import fr.chaun14.dynamicservericon.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class CarouselIntervalCommand implements SubCommand {

    private final DynamicServerIcon plugin;

    public CarouselIntervalCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("§cUsage: /icon carousel interval <seconds>");
            return;
        }

        int seconds;
        try {
            seconds = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cPlease enter a valid number.");
            return;
        }

        if (seconds < 1) {
            sender.sendMessage("§cInterval must be at least 1 second.");
            return;
        }

        plugin.getConfig().set("interval", seconds);
        plugin.saveConfig();

        sender.sendMessage("§aCarousel interval set to " + seconds + " seconds.");

        // If carousel mode is active, restart it with the new interval
        if ("carousel".equalsIgnoreCase(plugin.getConfig().getString("mode"))) {
            plugin.restartCarousel();
        }
    }
}
