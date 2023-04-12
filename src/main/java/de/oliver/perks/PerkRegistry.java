package de.oliver.perks;

import org.bukkit.potion.PotionEffectType;

public class PerkRegistry {

    public static final Perk FAST_DIGGING = new EffectPerk("Fast Digging", "Gives you the haste effect", PotionEffectType.FAST_DIGGING);
    public static final Perk NIGHT_VISION = new EffectPerk("Night Vision", "Gives you the night vision effect", PotionEffectType.NIGHT_VISION);
    public static final Perk KEEP_EXP = new SimplePerk("Keep EXP", "Don't lose your exp after dying");

}
