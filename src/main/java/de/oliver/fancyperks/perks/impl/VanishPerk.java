package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class VanishPerk extends Perk {
    public VanishPerk(String systemName, String name, String description, ItemStack displayItem) {
        super(systemName, name, description, displayItem);
    }

    @Override
    public boolean grant(Player player) {
        if(!super.grant(player)) return false;

        FancyPerks.getInstance().getServer().getOnlinePlayers().forEach(otherPlayer -> {
            if (!otherPlayer.hasPermission("FancyPerks.seevanished")) {
                otherPlayer.hidePlayer(FancyPerks.getInstance(), player);
            }
        });

        player.setMetadata("vanished", new FixedMetadataValue(FancyPerks.getInstance(), true));

        return true;
    }

    @Override
    public void revoke(Player player) {
        super.revoke(player);
        FancyPerks.getInstance().getServer().getOnlinePlayers().forEach(otherPlayer -> otherPlayer.showPlayer(FancyPerks.getInstance(), player));
        player.setMetadata("vanished", new FixedMetadataValue(FancyPerks.getInstance(), false));
    }
}
