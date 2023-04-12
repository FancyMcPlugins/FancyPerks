package de.oliver.listeners;

import de.oliver.FancyPerks;
import de.oliver.PerkManager;
import de.oliver.perks.Perk;
import de.oliver.perks.PerkRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class PlayerDeathListener implements Listener {
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player p = event.getPlayer();
        
        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getPerks(p);

        int i = perks.indexOf(PerkRegistry.KEEP_EXP);

        if(i == -1){
            return;
        }

        event.setKeepLevel(true);
        event.setShouldDropExperience(false);
    }
    
}
