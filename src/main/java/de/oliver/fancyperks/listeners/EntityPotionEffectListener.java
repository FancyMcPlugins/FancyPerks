package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.impl.EffectPerk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.List;

public class EntityPotionEffectListener implements Listener {

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (!(event.getEntity() instanceof Player p)) {
            return;
        }

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);

        for (Perk perk : perks) {
            if (perk instanceof EffectPerk effectPerk) {
                if (event.getNewEffect() == null && event.getOldEffect() != null && event.getOldEffect().getType().equals(effectPerk.getEffectType())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

}
