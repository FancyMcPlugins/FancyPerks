package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player p)){
            return;
        }

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);

        boolean hasGod = perks.contains(PerkRegistry.GOD);
        if(hasGod){
            event.setDamage(0);
            return;
        }

        boolean hasNoFireDamage = perks.contains(PerkRegistry.NO_FIRE_DAMAGE);
        if(hasNoFireDamage && event.getCause().name().toLowerCase().contains("fire")){
            event.setDamage(0);
        }

        boolean hasNoFallDamage = perks.contains(PerkRegistry.NO_FALL_DAMAGE);
        if(hasNoFallDamage && event.getCause() == EntityDamageEvent.DamageCause.FALL){
            event.setDamage(0);
        }
    }

}
