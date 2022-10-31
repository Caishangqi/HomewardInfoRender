package com.caizii.command;

import com.caizi.mf.annotations.Command;
import com.caizi.mf.annotations.Default;
import com.caizi.mf.annotations.SubCommand;
import com.caizi.mf.base.CommandBase;
import com.caizii.HomewardInfoRender;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

import static com.caizii.HomewardInfoRender.protocolManager;

@Command("hinfo")
public class MainCommand extends CommandBase {

    @Default
    public void defaultCommand(final CommandSender commandSender) {
        HomewardInfoRender.customLogger.send("Welcome to use Hinfo", (Player) commandSender);
    }

    @SubCommand("camera")
    public void fakeCameraCommand(final CommandSender commandSender) {
        Player player = (Player) commandSender;
        PacketContainer fakeCamera = new PacketContainer(PacketType.Play.Server.CAMERA);

        try {
            protocolManager.sendServerPacket(player, fakeCamera);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }


    }

    @SubCommand("explode")
    public void fakeExplodeCommand(final CommandSender commandSender) {
        Player player = (Player) commandSender;

        player.getLineOfSight(null, 50).stream().filter(block -> {
            return block.getType() != Material.AIR;
        }).forEach(block -> {
            Location blockLocation = block.getLocation();
            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.EXPLOSION);
            packet.setMeta("Caizii", 10);
            packet.getDoubles().write(0, blockLocation.getX());
            packet.getDoubles().write(1, blockLocation.getY());
            packet.getDoubles().write(2, blockLocation.getZ());
            packet.getFloat().write(0, 10.0f);

            try {
                protocolManager.sendServerPacket(player, packet);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        });

    }

}
