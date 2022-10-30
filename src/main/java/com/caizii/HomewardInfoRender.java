package com.caizii;

import com.caizi.mf.base.CommandManager;
import com.caizi.utils.logs.ConsoleLogger;
import com.caizi.utils.logs.CustomLogger;
import com.caizi.utils.logs.LoggerManipulationType;
import com.caizii.command.MainCommand;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.configuration.file.FileConfiguration;
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
        loadConfigurations();

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
        consoleLogger.send(LoggerManipulationType.UNLOAD,"关闭 "+this.getName()+" 中...");
    }

    /**
     * 注册基本组件，全局组件等等
     */
    private void initializedComponents() {
        plugin = this;
        consoleLogger = new ConsoleLogger(pluginPrefix);
        customLogger = new CustomLogger(pluginPrefix);
        consoleLogger.send(LoggerManipulationType.LOAD,"正在初始化 " +this.getName()+ " 的必要组件");
        //初始化ProtocolLib
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public static HomewardInfoRender getInstance() {
        if (plugin != null) return plugin;else throw new RuntimeException("plugin has not been initialized!");
    }

}
