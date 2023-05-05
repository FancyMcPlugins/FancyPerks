package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NoHungerPerk extends Perk {
    public NoHungerPerk(String systemName, String name, String description, ItemStack displayItem) {
        super(systemName, name, description, displayItem);
    }

    @Override
    public void grant(Player player) {
        super.grant(player);
        player.setFoodLevel(20);
    }
}
