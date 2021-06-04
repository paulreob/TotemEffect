package me.paulreob.totemeffect;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TotemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {

        if (args.length != 2) {
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            commandSender.sendMessage("Could not find player " + args[0]);
            return true;
        }

        int customModelData;
        try {
            customModelData = Integer.parseInt(args[1]);
        } catch (Exception e) {
            commandSender.sendMessage(args[1] + " is not a valid number.");
            return true;
        }

        Main.playCustomTotemAnimation(player, customModelData);

        return true;
    }
}
