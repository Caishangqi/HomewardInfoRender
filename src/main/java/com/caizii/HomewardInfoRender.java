package com.caizii;

import com.caizi.mf.base.CommandManager;
import com.caizi.utils.logs.ConsoleLogger;
import com.caizi.utils.logs.CustomLogger;
import com.caizi.utils.logs.LoggerManipulationType;
import com.caizii.command.MainCommand;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomewardInfoRender extends JavaPlugin {

    public static HomewardInfoRender plugin;

    public static ConsoleLogger consoleLogger;
    public static CustomLogger customLogger;

    public static FileConfiguration config;

    public static CommandManager commandManager;

    public static ProtocolManager protocolManager;

    private static String pluginPrefix = "&6HInfo &f| ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        initializedComponents();
        registerCommands();
        //registerPackageListener();
        loadConfigurations();

    }

    private void registerPackageListener() {


        //Listening to an Incoming packet - from the CLIENT to SERVER
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                //Extracting information from the packet

                double x = packet.getDoubles().read(0);
                double y = packet.getDoubles().read(1);
                double z = packet.getDoubles().read(2);

                boolean isOnGround = packet.getBooleans().read(0);
                //customLogger.send("INBOUND PACKET: x = " + x + "y = " + y + " z = " + z + "isOnGround() = " + isOnGround, player);

            }
        });

        //服务器给客户端发送包的监听
        protocolManager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.REL_ENTITY_MOVE) {

            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();
                Short x = packet.getShorts().read(0);
                Short y = packet.getShorts().read(1);
                Short z = packet.getShorts().read(2);
                int entityID = packet.getIntegers().read(0);


                Entity entity = protocolManager.getEntityFromID(player.getWorld(), entityID);
                entity.teleport(player.getLocation());

                if (entity instanceof LivingEntity e) {
                    e.setHealth(0);
                }

                customLogger.send("INBOUND PACKET: x = " + x + "y = " + y + " z = " + z, player);
            }

        });

        protocolManager.addPacketListener(new PacketAdapter(this,PacketType.Play.Client.CHAT) {


            @Override
            public void onPacketReceiving(PacketEvent event) {
                event.setCancelled(true);
            }

        });


    }

    private void registerCommands() {
        commandManager = new CommandManager(this);
        commandManager.register(new MainCommand());
    }

    private void loadConfigurations() {
        //注册默认Config,没有的话创建一个
        saveDefaultConfig();
        config = getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        consoleLogger.send(LoggerManipulationType.UNLOAD, "关闭 " + this.getName() + " 中...");
    }

    /**
     * 注册基本组件，全局组件等等
     */
    private void initializedComponents() {
        plugin = this;
        consoleLogger = new ConsoleLogger(pluginPrefix);
        customLogger = new CustomLogger(pluginPrefix);
        consoleLogger.send(LoggerManipulationType.LOAD, "正在初始化 " + this.getName() + " 的必要组件");
        //初始化ProtocolLib
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public static HomewardInfoRender getInstance() {
        if (plugin != null) return plugin;
        else throw new RuntimeException("plugin has not been initialized!");
    }

}
