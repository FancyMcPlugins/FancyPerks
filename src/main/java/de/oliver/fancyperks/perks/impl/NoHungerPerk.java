package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NoHungerPerk extends Perk {
    public NoHungerPerk(String systemName, String name, String description, ItemStack displayItem) {
        super(systemName, name, description, displayItem);
    }

    @Override
    public boolean grant(Player player) {
        if(!super.grant(player)) return false;

        player.setFoodLevel(20);
        return true;
    }
}
