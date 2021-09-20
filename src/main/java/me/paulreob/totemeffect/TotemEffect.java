package me.paulreob.totemeffect;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.paulreob.totemeffect.Commands.TotemCommand;
import me.paulreob.totemeffect.Utils.NMSUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TotemEffect extends JavaPlugin implements Listener {

    public static void playCustomTotemAnimation(final Player player, final int customModelData) {
        final ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        final ItemMeta meta = totem.getItemMeta();
        meta.setCustomModelData(customModelData);
        totem.setItemMeta(meta);
        final ItemStack mainHand = player.getInventory().getItemInMainHand().clone();
        player.getInventory().setItemInMainHand(totem);
        player.stopSound(Sound.ITEM_TOTEM_USE, SoundCategory.PLAYERS);

        try {
            final Class<?> statusPacketClass = NMSUtils.getNMSClass("PacketPlayOutEntityStatus");
            final Class<?> entityPlayerClass = NMSUtils.getNMSClass("EntityPlayer");
            final Class<?> entityClass = NMSUtils.getNMSClass("Entity");
            final Class<?> craftPlayerClass = NMSUtils.getBukkitNMSClass("entity.CraftPlayer");
            final Method getHandleMethod = craftPlayerClass.getMethod("getHandle");
            final Method sendPacketMethod = NMSUtils.getNMSClass("PlayerConnection").getMethod("sendPacket", NMSUtils.getNMSClass("Packet"));
            final Constructor<?> packetConstructor = statusPacketClass.getConstructor(entityClass, byte.class);
            final Object craftPlayer = craftPlayerClass.cast(player);
            final Object entityPlayer = getHandleMethod.invoke(craftPlayer);
            final Object packet = packetConstructor.newInstance(entityPlayerClass.cast(entityPlayer), (byte) 35);
            sendPacketMethod.invoke(NMSUtils.getConnection(player), packet);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        player.getInventory().setItemInMainHand(mainHand);
        player.stopSound(Sound.ITEM_TOTEM_USE, SoundCategory.PLAYERS);


    }

    @Override
    public void onEnable() {
        getCommand("totemeffect").setExecutor(new TotemCommand());
        getLogger().info("Plugin enabled (Totemeffect) by PauXD");
    }


}
