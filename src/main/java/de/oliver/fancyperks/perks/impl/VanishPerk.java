package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.perks.Perk;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class VanishPerk extends Perk {
    public VanishPerk(String name, String description, ItemStack displayItem) {
        super(name, description, displayItem);
    }

    @Override
    public void grant(Player player) {
        super.grant(player);
        player.getPersistentDataContainer().set(new NamespacedKey(FancyPerks.getInstance(), "vanish"), PersistentDataType.BYTE, (byte) 1);
        FancyPerks.getInstance().getServer().getOnlinePlayers().forEach(otherPlayer -> otherPlayer.hidePlayer(FancyPerks.getInstance(), player));
    }

    @Override
    public void revoke(Player player) {
        super.revoke(player);
        player.getPersistentDataContainer().set(new NamespacedKey(FancyPerks.getInstance(), "vanish"), PersistentDataType.BYTE, (byte) 0);
        FancyPerks.getInstance().getServer().getOnlinePlayers().forEach(otherPlayer -> otherPlayer.showPlayer(FancyPerks.getInstance(), player));
    }
}
