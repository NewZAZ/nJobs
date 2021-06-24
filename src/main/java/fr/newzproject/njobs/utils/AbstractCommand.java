package fr.newzproject.njobs.utils;

import fr.newzproject.njobs.JobsCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractCommand implements CommandExecutor {

    private static JavaPlugin plugin;
    private final String commandName;
    private String permission;
    private final boolean canConsoleUse;

    public AbstractCommand(String commandName, boolean canConsoleUse) {
        this.commandName = commandName;
        this.canConsoleUse = canConsoleUse;
        plugin.getCommand(commandName).setExecutor(this);
    }

    public AbstractCommand(String commandName, String permission, boolean canConsoleUse) {
        this.commandName = commandName;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        plugin.getCommand(commandName).setExecutor(this);
    }

    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!cmd.getLabel().equalsIgnoreCase(commandName))
            return true;
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.utils.notHavePermission")));
            return true;
        }
        if (!canConsoleUse && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.utils.instancePlayer")));
            return true;
        }
        execute(sender, args);
        return true;
    }

    public static void registerCommands(JobsCore core) {
        plugin = core;
    }
}
