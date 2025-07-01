package fr.chaun14.dynamicservericon.commands.carousel;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import fr.chaun14.dynamicservericon.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CarouselListCommand implements SubCommand {

    private final DynamicServerIcon plugin;

    public CarouselListCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<String> icons = plugin.getConfig().getStringList("icon-list");

        String current = "none";
        if (plugin.getIconCarousel() != null && !icons.isEmpty()) {
            int currentIndex = plugin.getIconCarousel().getCurrentIndex();
            if (currentIndex >= 0 && currentIndex < icons.size()) {
                current = icons.get(currentIndex);
            } else {
                current = plugin.getConfig().getString("current-icon", "none");
            }
        } else {
            current = plugin.getConfig().getString("current-icon", "none");
        }

        if (icons.isEmpty()) {
            sender.sendMessage("§eThe carousel icon list is empty.");
            return;
        }

        sender.sendMessage("§aCarousel icons:");
        for (int i = 0; i < icons.size(); i++) {
            String icon = icons.get(i);
            boolean isCurrent = icon.equalsIgnoreCase(current);
            String marker = isCurrent ? " §6(Active)" : "";
            sender.sendMessage(" §7- §f[" + i + "] " + icon + marker);
        }
    }
}