package de.oliver.listeners;

import de.oliver.FancyPerks;
import de.oliver.PerkManager;
import de.oliver.perks.Perk;
import de.oliver.perks.PerkRegistry;
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

        boolean hasKeepExp = perks.contains(PerkRegistry.NO_FIRE_DAMAGE);
        if(hasKeepExp && event.getCause().name().toLowerCase().contains("fire")){
            event.setDamage(0);
        }

    }

}
