package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class AutoRepairPerk extends Perk {
    public AutoRepairPerk(String systemName, String displayName, String description, ItemStack displayItem) {
        super(systemName, displayName, description, displayItem);
    }

    @Override
    public void grant(Player player) {
        super.grant(player);
        for (ItemStack item : player.getInventory().getContents()) {
            if(item == null){
                continue;
            }


            item.editMeta(itemMeta -> {
                if(!(itemMeta instanceof Damageable damageable)){
                    return;
                }

                damageable.setDamage(0);
            });
        }
    }

    @Override
    public void revoke(Player player) {
        super.revoke(player);
    }
}
