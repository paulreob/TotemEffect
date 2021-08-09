package me.paulreob.totemeffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TotemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String totemprefix = ChatColor.translateAlternateColorCodes('&', "&6TotemEffect  ¦ &r");

        if(!sender.hasPermission("tottemeffect.use")) {
            sender.sendMessage(totemprefix + ChatColor.RED + "Insufficient Permissions");
            return true;
        }
        if (args.length != 2) {
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(totemprefix +"Could not find player " + ChatColor.RED + args[0]);
            return true;
        }

        int customModelData;
        try {
            customModelData = Integer.parseInt(args[1]);
        } catch (Exception e) {
            sender.sendMessage(totemprefix + ChatColor.RED + args[1] + ChatColor.RESET + " is not a valid number.");
            return true;
        }

        Main.playCustomTotemAnimation(player, customModelData);

        return true;
    }
}

