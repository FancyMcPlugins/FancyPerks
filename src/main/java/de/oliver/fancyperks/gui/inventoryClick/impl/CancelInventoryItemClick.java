package de.oliver.fancyperks.gui.inventoryClick.impl;

import de.oliver.fancyperks.gui.inventoryClick.InventoryItemClick;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CancelInventoryItemClick implements InventoryItemClick {

    public static final CancelInventoryItemClick INSTANCE = new CancelInventoryItemClick();

    private CancelInventoryItemClick(){ }

    @Override
    public String getId() {
        return "cancelClick";
    }

    @Override
    public void onClick(InventoryClickEvent event, Player player) {
        event.setCancelled(true);
    }
}
