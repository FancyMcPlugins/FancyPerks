package de.oliver.fancyperks.perks.impl;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DoubleDropsPerk extends SimplePerk {

    private final List<EntityType> blacklist;

    public DoubleDropsPerk(String systemName, String name, String description, ItemStack displayItem) {
        super(systemName, name, description, displayItem);
        this.blacklist = new ArrayList<>();
    }

    public void addToBlacklist(EntityType entityType) {
        blacklist.add(entityType);
    }

    public List<EntityType> getBlacklist() {
        return blacklist;
    }
}
