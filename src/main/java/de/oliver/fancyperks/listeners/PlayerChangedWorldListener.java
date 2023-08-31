package de.oliver.fancyperks.listeners;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event){
        Player p = event.getPlayer();

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);

        for (Perk perk : new ArrayList<>(perks)) {
            if(perk.getDisabledWorlds().contains(p.getWorld().getName())){
                perk.revoke(p);
                MessageHelper.warning(p, "The " + perk.getSystemName() + " perk is disabled in this world");
            }
        }
    }

}
