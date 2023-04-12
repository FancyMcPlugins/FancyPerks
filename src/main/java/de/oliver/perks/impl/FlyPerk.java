package de.oliver.perks.impl;

import de.oliver.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FlyPerk extends Perk {
    public FlyPerk(String name, String description, ItemStack displayItem) {
        super(name, description, displayItem);
    }

    @Override
    public void grant(Player player) {
        super.grant(player);
        player.setAllowFlight(true);
    }

    @Override
    public void revoke(Player player) {
        super.revoke(player);
        player.setFlying(false);
        player.setAllowFlight(false);
    }
}
