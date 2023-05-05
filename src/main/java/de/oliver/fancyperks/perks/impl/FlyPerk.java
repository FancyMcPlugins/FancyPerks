package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.perks.Perk;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FlyPerk extends Perk {
    public FlyPerk(String systemName, String name, String description, ItemStack displayItem) {
        super(systemName, name, description, displayItem);
    }

    @Override
    public void grant(Player player) {
        super.grant(player);
        player.setAllowFlight(true);
    }

    @Override
    public void revoke(Player player) {
        super.revoke(player);
        if (player.getGameMode() == GameMode.CREATIVE) return;
        player.setFlying(false);
        player.setAllowFlight(false);
    }
}
