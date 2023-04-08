package de.oliver;

import de.oliver.perks.Perk;
import org.bukkit.entity.Player;

import java.util.*;

public class PerkManager {

    private final Map<UUID, List<Perk>> playerPerks;

    public PerkManager() {
        this.playerPerks = new HashMap<>();
    }

    public List<Perk> getPerks(Player player){
        return playerPerks.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public boolean hasPerk(Player player, Perk perk){
        return playerPerks.containsKey(player.getUniqueId()) && playerPerks.get(player.getUniqueId()).contains(perk);
    }

    public void addPerk(Player player, Perk perk){
        List<Perk> perks = getPerks(player);
        if(!perks.contains(perk)){
            perks.add(perk);
        }

        playerPerks.put(player.getUniqueId(), perks);
    }

    public void removePerk(Player player, Perk perk){
        List<Perk> perks = getPerks(player);
        perks.remove(perk);

        playerPerks.put(player.getUniqueId(), perks);
    }
}
