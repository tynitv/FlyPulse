package fr.tynitv.flypulse.fly;

import fr.tynitv.flypulse.FlyPulse;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlyManager {

    private final FlyPulse plugin;
    private final Map<UUID, Long> flyTimes = new HashMap<>();
    private final MiniMessage mm = MiniMessage.miniMessage();

    public FlyManager(FlyPulse plugin) {
        this.plugin = plugin;
        startFlyTask();
    }

    public void addFlyTime(Player player, int seconds) {
        long current = flyTimes.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
        if (current < System.currentTimeMillis()) current = System.currentTimeMillis();

        flyTimes.put(player.getUniqueId(), current + (seconds * 1000L));
        player.setAllowFlight(true);
        player.setFlying(true);

        String prefix = plugin.getConfig().getString("messages.prefix", "");
        player.sendMessage(mm.deserialize(plugin.getConfig().getString("messages.enabled", "").replace("<prefix>", prefix).replace("{seconds}", String.valueOf(seconds))));
    }

    private void startFlyTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long now = System.currentTimeMillis();
            for (Map.Entry<UUID, Long> entry : new HashMap<>(flyTimes).entrySet()) {
                if (entry.getValue() <= now) {
                    flyTimes.remove(entry.getKey());
                    Player p = Bukkit.getPlayer(entry.getKey());
                    if (p != null && p.isOnline()) {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        String prefix = plugin.getConfig().getString("messages.prefix", "");
                        p.sendMessage(mm.deserialize(plugin.getConfig().getString("messages.expired", "").replace("<prefix>", prefix)));
                    }
                }
            }
        }, 20L, 20L);
    }
}
