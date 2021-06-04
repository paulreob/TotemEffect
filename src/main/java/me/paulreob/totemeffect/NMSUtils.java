package me.paulreob.totemeffect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMSUtils {

    public static Class<?> getBukkitNMSClass(final String nmsClassString) throws ClassNotFoundException {
        final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        final String name = "org.bukkit.craftbukkit." + version + nmsClassString;
        return Class.forName(name);
    }

    public static Object getConnection(final Player player) {
        try {
            final Method getHandle = player.getClass().getMethod("getHandle");
            final Object nmsPlayer = getHandle.invoke(player);
            final Field conField = nmsPlayer.getClass().getField("playerConnection");
            final Object con = conField.get(nmsPlayer);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        return Class.forName(name);
    }

}