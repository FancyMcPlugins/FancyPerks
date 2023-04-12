package de.oliver.perks.impl;

import de.oliver.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NoHungerPerk extends Perk {
    public NoHungerPerk(String name, String description, ItemStack displayItem) {
        super(name, description, displayItem);
    }

    @Override
    public void grant(Player player) {
        super.grant(player);
        player.setFoodLevel(20);
    }
}
