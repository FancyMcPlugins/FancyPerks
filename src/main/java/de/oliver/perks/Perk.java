package de.oliver.perks;

import de.oliver.FancyPerks;
import de.oliver.PerkManager;
import org.bukkit.entity.Player;

public abstract class Perk {

    protected static final PerkManager perkManager = FancyPerks.getInstance().getPerkManager();

    protected final String name;
    protected final String description;

    public Perk(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void grant(Player player){
        perkManager.addPerk(player, this);
    }

    public void revoke(Player player){
        perkManager.removePerk(player, this);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
