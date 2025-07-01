package fr.chaun14.dynamicservericon.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabCompletableSubCommand {
    List<String> onTabComplete(CommandSender sender, String[] args);
}