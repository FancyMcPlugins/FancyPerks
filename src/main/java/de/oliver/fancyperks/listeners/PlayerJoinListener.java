package de.oliver.fancyperks.listeners;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.impl.EffectPerk;
import de.oliver.fancyperks.perks.impl.FlyPerk;
import de.oliver.fancyperks.perks.impl.VanishPerk;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (!FancyPerks.getInstance().getFanyPerksConfig().isMuteVersionNotification() && event.getPlayer().hasPermission("FancyPerks.admin")) {
            new Thread(() -> {
                ComparableVersion newestVersion = FancyPerks.getInstance().getVersionFetcher().fetchNewestVersion();
                ComparableVersion currentVersion = new ComparableVersion(FancyPerks.getInstance().getDescription().getVersion());
                if (newestVersion != null && newestVersion.compareTo(currentVersion) > 0) {
                    MessageHelper.warning(event.getPlayer(), "You are using an outdated version of the FancyPerks Plugin");
                    MessageHelper.warning(event.getPlayer(), "[!] Please download the newest version (" + newestVersion + "): <click:open_url:'" + FancyPerks.getInstance().getVersionFetcher().getDownloadUrl() + "'><u>click here</u></click>");
                }
            }).start();
        }
        if (!p.hasPermission("FancyPerks.seevanished")) {
            FancyPerks.getInstance().getServer().getOnlinePlayers().forEach(onlinePlayer -> {
                if (!onlinePlayer.getMetadata("vanished").isEmpty() && onlinePlayer.getMetadata("vanished").get(0).asBoolean()) {
                    p.hidePlayer(FancyPerks.getInstance(), onlinePlayer);
                }
            });
        }

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(event.getPlayer());

        for (Perk perk : perks) {
            if(perk.getDisabledWorlds().contains(p.getWorld().getName())){
                MessageHelper.warning(p, "The " + perk.getSystemName() + " perk is disabled in this world");
                continue;
            }

            if (perk instanceof EffectPerk effectPerk) {
                p.addPotionEffect(new PotionEffect(effectPerk.getEffectType(), -1, 0, true, false, false));
            } else if (perk instanceof FlyPerk) {
                p.setAllowFlight(true);
            } else if (perk instanceof VanishPerk vanishPerk) {
                vanishPerk.grant(p);
            }
        }

    }

}
