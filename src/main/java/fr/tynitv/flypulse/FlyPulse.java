package fr.tynitv.flypulse;

import fr.tynitv.flypulse.command.FlyTabCompleter;
import fr.tynitv.flypulse.command.TempFlyCommand;
import fr.tynitv.flypulse.fly.FlyManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyPulse extends JavaPlugin {

    private static FlyPulse instance;
    private FlyManager flyManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.flyManager = new FlyManager(this);

        if (getCommand("fly") != null) {
            getCommand("fly").setExecutor(new TempFlyCommand(this, flyManager));
            getCommand("fly").setTabCompleter(new FlyTabCompleter());
        }

        getLogger().info("FlyPulse v1.1.0 enabled with Timed Survival Flight & TabCompleter!");
    }

    @Override
    public void onDisable() {
        getLogger().info("FlyPulse disabled!");
    }

    public static FlyPulse getInstance() {
        return instance;
    }

    public FlyManager getFlyManager() {
        return flyManager;
    }
}
