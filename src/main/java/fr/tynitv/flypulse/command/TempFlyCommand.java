package fr.tynitv.flypulse.command;

import fr.tynitv.flypulse.FlyPulse;
import fr.tynitv.flypulse.fly.FlyManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TempFlyCommand implements CommandExecutor {

    private final FlyPulse plugin;
    private final FlyManager flyManager;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public TempFlyCommand(FlyPulse plugin, FlyManager flyManager) {
        this.plugin = plugin;
        this.flyManager = flyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Commande reservée aux joueurs.");
            return true;
        }

        if (!player.hasPermission("flypulse.use")) {
            player.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
            return true;
        }

        int sec = plugin.getConfig().getInt("default-fly-seconds", 60);
        flyManager.addFlyTime(player, sec);
        return true;
    }
}
