package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.util.List;

public class PlayerItemDamageListener implements Listener {

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event){
        Player p = event.getPlayer();

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);

        boolean hasAutoRepair = perks.contains(PerkRegistry.AUTO_REPAIR);
        if(hasAutoRepair){
            event.setCancelled(true);
        }
    }

}
