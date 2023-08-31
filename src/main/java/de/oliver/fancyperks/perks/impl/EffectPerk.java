package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectPerk extends Perk {

    private final PotionEffectType effectType;

    public EffectPerk(String systemName, String name, String description, ItemStack displayName, PotionEffectType effectType) {
        super(systemName, name, description, displayName);
        this.effectType = effectType;
    }

    @Override
    public boolean grant(Player player) {
        if(!super.grant(player)) return false;

        player.addPotionEffect(new PotionEffect(effectType, -1, 0, true, false, false));
        return true;
    }

    @Override
    public void revoke(Player player) {
        super.revoke(player);
        player.removePotionEffect(effectType);
    }

    public PotionEffectType getEffectType() {
        return effectType;
    }
}
