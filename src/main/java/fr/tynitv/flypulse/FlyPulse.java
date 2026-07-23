package fr.tynitv.flypulse;

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

        if (getCommand("tempfly") != null) {
            getCommand("tempfly").setExecutor(new TempFlyCommand(this, flyManager));
        }

        getLogger().info("FlyPulse v1.0.0 enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("FlyPulse disabled!");
    }

    public static FlyPulse getInstance() {
        return instance;
    }
}
