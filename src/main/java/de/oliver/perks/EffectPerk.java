package de.oliver.perks;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectPerk extends Perk {

    private final PotionEffectType effectType;

    public EffectPerk(String name, String description, PotionEffectType effectType) {
        super(name, description);
        this.effectType = effectType;
    }

    @Override
    public void grant(Player player) {
        super.grant(player);
        player.addPotionEffect(new PotionEffect(effectType, -1, 0, true, false, false));
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
