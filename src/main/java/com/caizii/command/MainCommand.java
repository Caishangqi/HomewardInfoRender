package com.caizii.command;

import com.caizi.mf.annotations.Command;
import com.caizi.mf.annotations.Default;
import com.caizi.mf.base.CommandBase;
import com.caizii.HomewardInfoRender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("hinfo")
public class MainCommand extends CommandBase {

    @Default
    public void defaultCommand(final CommandSender commandSender) {
        HomewardInfoRender.customLogger.send("Welcome to use Hinfo", (Player) commandSender);
    }

}
