package de.oliver.fancyperks.commands;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancyperks.FancyPerks;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class FancyPerksCMD implements CommandExecutor, TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Stream.of("version", "reload").filter(input -> input.startsWith(args[0].toLowerCase())).toList();
        }

        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1 && args[0].equalsIgnoreCase("version")) {
            MessageHelper.info(sender, "<i>Checking version, please wait...</i>");
            new Thread(() -> {
                ComparableVersion newestVersion = FancyPerks.getInstance().getVersionFetcher().fetchNewestVersion();
                ComparableVersion currentVersion = new ComparableVersion(FancyPerks.getInstance().getDescription().getVersion());
                if (newestVersion == null) {
                    MessageHelper.error(sender, "Could not find latest version");
                } else if (newestVersion.compareTo(currentVersion) > 0) {
                    MessageHelper.warning(sender, "You are using an outdated version of the FancyPerks Plugin");
                    MessageHelper.warning(sender, "[!] Please download the newest version (" + newestVersion + "): <click:open_url:'" + FancyPerks.getInstance().getVersionFetcher().getDownloadUrl() + "'><u>click here</u></click>");
                } else {
                    MessageHelper.success(sender, "You are using the latest version of the FancyPerks Plugin (" + currentVersion + ")");
                }
            }).start();
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            FancyPerks.getInstance().getFanyPerksConfig().reload();
            FancyPerks.getInstance().getLanguageConfig().load();
            MessageHelper.success(sender, "Reloaded the config");
        }

        return false;
    }
}
