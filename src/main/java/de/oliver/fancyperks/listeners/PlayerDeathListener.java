package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getPlayer();

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);


        boolean hasKeepExp = perks.contains(PerkRegistry.KEEP_EXP);
        if (hasKeepExp) {
            event.setKeepLevel(true);
            event.setShouldDropExperience(false);
        }

        boolean hasKeepInv = perks.contains(PerkRegistry.KEEP_INVENTORY);
        if (hasKeepInv) {
            event.setKeepInventory(true);
            event.getDrops().clear();
        }

        boolean hasFly = perks.contains(PerkRegistry.FLY);
        if (hasFly) {
            p.setAllowFlight(true);
        }
    }

}
