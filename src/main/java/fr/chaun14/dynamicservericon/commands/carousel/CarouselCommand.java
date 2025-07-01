package fr.chaun14.dynamicservericon.commands.carousel;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import fr.chaun14.dynamicservericon.commands.SubCommand;
import fr.chaun14.dynamicservericon.commands.TabCompletableSubCommand;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarouselCommand implements SubCommand, TabCompletableSubCommand {

    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public CarouselCommand(DynamicServerIcon plugin) {
        subcommands.put("interval", new CarouselIntervalCommand(plugin));
        subcommands.put("add", new CarouselAddCommand(plugin));
        subcommands.put("remove", new CarouselRemoveCommand(plugin));
        subcommands.put("list", new CarouselListCommand(plugin));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String subCommandsList = String.join(" | ", subcommands.keySet());

        if (args.length < 2) {
            // Generate automatically the subcommands list
            sender.sendMessage("§cUsage: /icon carousel <" + subCommandsList + ">");
            return;
        }

        String sub = args[1].toLowerCase();
        SubCommand command = subcommands.get(sub);
        if (command == null) {
            sender.sendMessage("§cUnknown carousel subcommand:  " + sub + ". Available: <" + subCommandsList + ">");
            return;
        }

        command.execute(sender, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return List.copyOf(subcommands.keySet());
        } else if (args.length > 2) {
            String sub = args[1].toLowerCase();
            SubCommand command = subcommands.get(sub);
            if (command instanceof TabCompletableSubCommand tabCompletable) {
                return tabCompletable.onTabComplete(sender, args);
            }
        }
        return List.of();
    }
}
