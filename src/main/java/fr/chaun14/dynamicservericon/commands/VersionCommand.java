package fr.chaun14.dynamicservericon.commands;

import fr.chaun14.dynamicservericon.DynamicServerIcon;

import org.bukkit.command.CommandSender;

public class VersionCommand implements SubCommand {

    private final DynamicServerIcon plugin;

    public VersionCommand(DynamicServerIcon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String version = plugin.getPluginMeta().getVersion();
        sender.sendMessage("§aDynamicServerIcon");
        sender.sendMessage("§7Version: §e" + version);
        sender.sendMessage("§7Author: §bchaun14");
        sender.sendMessage("§7Website: §9https://github.com/chaun14");
    }

}
