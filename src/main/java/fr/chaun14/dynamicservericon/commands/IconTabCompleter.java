package fr.chaun14.dynamicservericon.commands;

import fr.chaun14.dynamicservericon.listeners.CommandListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class IconTabCompleter implements TabCompleter {

    private final CommandListener commandListener;

    public IconTabCompleter(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!sender.hasPermission("dynamicservericon.admin"))
            return completions;

        if (args.length == 1) {
            // Suggest subcommand names
            completions.addAll(commandListener.getSubCommands().keySet());
        } else {
            // Delegate to subcommand if it implements TabCompletableSubCommand
            SubCommand sub = commandListener.getSubCommands().get(args[0].toLowerCase());
            if (sub instanceof TabCompletableSubCommand completable) {
                completions.addAll(completable.onTabComplete(sender, args));
            }
        }

        return completions;
    }
}
