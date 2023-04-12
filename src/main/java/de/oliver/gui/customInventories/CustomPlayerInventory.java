package de.oliver.gui.customInventories;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class CustomPlayerInventory implements InventoryHolder {

    protected final Player player;
    protected Inventory inventory;

    protected CustomPlayerInventory(Player player, int amountRows, Component title) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, amountRows * 9, title);
    }

    public Player getPlayer() {
        return player;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
