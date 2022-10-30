package com.caizii;

import com.caizi.utils.logs.ConsoleLogger;
import com.caizi.utils.logs.LoggerManipulationType;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomewardInfoRender extends JavaPlugin {

    public static HomewardInfoRender plugin;

    public static ConsoleLogger consoleLogger;

    private static String pluginPrefix = "&6HInfo &f| ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        initializedComponents();


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
        consoleLogger.send(LoggerManipulationType.LOAD,"正在初始化 " +this.getName()+ " 的必要组件");
    }

    public static HomewardInfoRender getInstance() {
        if (plugin != null) return plugin;else throw new RuntimeException("plugin has not been initialized!");
    }

}
