/**
 * CustomNick Plugin
 * <p>
 * Use case: older Essentials plugin that does not contain configurations to enable colored nicknames.
 * This plugin introduces the /colornick and /customnick commands that will translate ampersand characters to
 * the native color character. This works by mutating the user's nickname input argument, then calling
 * Essential's "nick" command.
 * </p>
 *
 * @author narlock
 *
 * Dependent on Craftbukkit JAR file. This code was written to work with Minecraft Beta 1.7.3 versions.
 */

package com.narlock.customnick;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getLogger;

public class CustomNick extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("CustomNick enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomNick disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("customnick")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("customnick.use")) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /customnick <name>");
                return true;
            }

            StringBuilder nickBuilder = new StringBuilder();
            for (String part : args) {
                nickBuilder.append(part).append(" ");
            }
            String nick = nickBuilder.toString().trim();
            nick = CustomNickUtils.translateColorCodes("&", nick);

            getServer().dispatchCommand(sender, "nick " + nick);

            return true;
        } else if (cmd.getName().equalsIgnoreCase("colornick")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("customnick.colornick")) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /colornick <color>");
                return true;
            }

            String colorInput = args[0].toUpperCase().replace(" ", "_").replace("-", "_");
            ChatColor color = null;

            // Normalize names like darkblue or dark_blue
            for (ChatColor c : ChatColor.values()) {
                if (c.name().replace("_", "").equalsIgnoreCase(colorInput.replace("_", ""))) {
                    color = c;
                    break;
                }
            }

            if (color == null) {
                player.sendMessage(ChatColor.RED + "Invalid color name. Available colors: BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE");
                return true;
            }

            String coloredNick = color + player.getName();
            getServer().dispatchCommand(sender, "nick " + coloredNick);

            return true;
        }
        return false;
    }
}
