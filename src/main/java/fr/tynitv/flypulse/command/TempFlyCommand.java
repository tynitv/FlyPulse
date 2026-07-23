package fr.tynitv.flypulse.command;

import fr.tynitv.flypulse.FlyPulse;
import fr.tynitv.flypulse.fly.FlyManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
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
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("flypulse.admin")) {
                sender.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
                return true;
            }
            plugin.reloadConfig();
            sender.sendMessage(mm.deserialize("<gradient:#11998E:#38EF7D><bold>FlyPulse</bold></gradient> <gray>»</gray> <green>Configuration rechargée.</green>"));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("flypulse.admin")) {
                sender.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
                return true;
            }
            if (args.length < 3) {
                sender.sendMessage(mm.deserialize("<red>Usage: /fly give <joueur> <secondes></red>"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(mm.deserialize("<red>Joueur introuvable.</red>"));
                return true;
            }
            int sec = Integer.parseInt(args[2]);
            flyManager.addFlyTime(target, sec);
            sender.sendMessage(mm.deserialize("<gradient:#11998E:#38EF7D><bold>FlyPulse</bold></gradient> <gray>»</gray> <green>" + sec + "s de vol ajoutées à " + target.getName() + ".</green>"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Commande reservée aux joueurs.");
            return true;
        }

        if (!player.hasPermission("flypulse.use")) {
            player.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
            return true;
        }

        int sec = plugin.getConfig().getInt("default-fly-seconds", 60);
        if (args.length > 0 && !args[0].equalsIgnoreCase("toggle") && !args[0].equalsIgnoreCase("time")) {
            try { sec = Integer.parseInt(args[0]); } catch (Exception ignored) {}
        }

        flyManager.addFlyTime(player, sec);
        return true;
    }
}
