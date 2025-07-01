package fr.chaun14.dynamicservericon.listeners;

import org.bukkit.command.*;

import fr.chaun14.dynamicservericon.DynamicServerIcon;
import fr.chaun14.dynamicservericon.commands.ModeCommand;
import fr.chaun14.dynamicservericon.commands.SetCommand;
import fr.chaun14.dynamicservericon.commands.SubCommand;
import fr.chaun14.dynamicservericon.commands.VersionCommand;
import fr.chaun14.dynamicservericon.commands.ReloadCommand;
import fr.chaun14.dynamicservericon.commands.carousel.CarouselCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandListener implements CommandExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandListener(DynamicServerIcon plugin) {
        // Register subcommands here
        subCommands.put("mode", new ModeCommand(plugin));
        subCommands.put("set", new SetCommand(plugin));
        subCommands.put("reload", new ReloadCommand(plugin));
        subCommands.put("carousel", new CarouselCommand(plugin));
        subCommands.put("version", new VersionCommand(plugin));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String subCommandsList = String.join(" | ", subCommands.keySet());
        if (!sender.hasPermission("dynamicservericon.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {

            sender.sendMessage("§cUsage: /icon <" + subCommandsList + ">");
            return true;
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());
        if (sub == null) {
            sender.sendMessage("§cUnknown subcommand. (<" + subCommandsList + ">)");
            return true;
        }

        sub.execute(sender, args);
        return true;
    }

    public Map<String, SubCommand> getSubCommands() {
        return subCommands;
    }
}
