package de.oliver.fancyperks;

import de.oliver.fancyperks.perks.Perk;
import org.bukkit.entity.Player;

import java.util.*;

public class PerkManager {

    private final Map<UUID, List<Perk>> playerPerks;

    public PerkManager() {
        this.playerPerks = new HashMap<>();
    }

    public List<Perk> getEnabledPerks(Player player){
        return playerPerks.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public boolean hasPerkEnabled(Player player, Perk perk){
        return playerPerks.containsKey(player.getUniqueId()) && playerPerks.get(player.getUniqueId()).contains(perk);
    }

    public void enablePerk(Player player, Perk perk){
        List<Perk> perks = getEnabledPerks(player);
        if(!perks.contains(perk)){
            perks.add(perk);
        }

        playerPerks.put(player.getUniqueId(), perks);
    }

    public void disablePerk(Player player, Perk perk){
        List<Perk> perks = getEnabledPerks(player);
        perks.remove(perk);

        playerPerks.put(player.getUniqueId(), perks);
    }
}
