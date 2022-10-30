package com.caizii.command;

import com.caizi.mf.annotations.Command;
import com.caizi.mf.annotations.Default;
import com.caizi.mf.annotations.SubCommand;
import com.caizi.mf.base.CommandBase;
import com.caizii.HomewardInfoRender;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

}
