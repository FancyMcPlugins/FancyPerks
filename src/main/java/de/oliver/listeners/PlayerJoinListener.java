package de.oliver.listeners;

import de.oliver.FancyPerks;
import de.oliver.PerkManager;
import de.oliver.perks.EffectPerk;
import de.oliver.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getPerks(event.getPlayer());

        for (Perk perk : perks) {
            if(perk instanceof EffectPerk effectPerk){
                p.addPotionEffect(new PotionEffect(effectPerk.getEffectType(), -1, 0, true, false, false));
            }
        }

    }

}
