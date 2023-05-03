package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.List;

public class EntityTargetLivingEntityListener implements Listener {

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() != null && event.getTarget() instanceof Player player) {
            PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
            List<Perk> perks = perkManager.getEnabledPerks(player);
            boolean hasMobsIgnore = perks.contains(PerkRegistry.MOBS_IGNORE);
            if (hasMobsIgnore) {
                event.setTarget(null);
            }
        }
    }

}
