package de.oliver.listeners;

import de.oliver.FancyPerks;
import de.oliver.PerkManager;
import de.oliver.perks.EffectPerk;
import de.oliver.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.List;

public class EntityPotionEffectListener implements Listener {

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event){
        if(!(event.getEntity() instanceof Player p)){
            return;
        }

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getPerks(p);

        for (Perk perk : perks) {
            if(perk instanceof EffectPerk effectPerk){
                if(event.getNewEffect() == null && event.getOldEffect() != null && event.getOldEffect().getType().equals(effectPerk.getEffectType())){
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

}
