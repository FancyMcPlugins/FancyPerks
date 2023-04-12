package de.oliver.perks.impl;

import de.oliver.perks.Perk;
import org.bukkit.inventory.ItemStack;

public class SimplePerk extends Perk {
    public SimplePerk(String name, String description, ItemStack displayItem) {
        super(name, description, displayItem);
    }
}
