package de.oliver.commands;

import de.oliver.gui.customInventories.impl.PerksInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PerksCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;

        PerksInventory perksInventory = new PerksInventory(p);
        p.openInventory(perksInventory.getInventory());

        return false;
    }
}
