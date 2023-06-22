package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import de.oliver.fancyperks.perks.impl.LavaRunnerPerk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(!event.hasChangedPosition()){
            return;
        }

        Player p = event.getPlayer();

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);

        boolean hasLavaRunner = perks.contains(PerkRegistry.LAVA_RUNNER);
        if(hasLavaRunner){
            ((LavaRunnerPerk) PerkRegistry.LAVA_RUNNER).updateBlocks(p);
        }
    }

}
