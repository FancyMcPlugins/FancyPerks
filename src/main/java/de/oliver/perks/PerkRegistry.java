package de.oliver.perks;

import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PerkRegistry {

    public static final Perk FAST_DIGGING = new EffectPerk("Fast Digging", "Gives you the haste effect", PotionEffectType.FAST_DIGGING);
    public static final Perk NIGHT_VISION = new EffectPerk("Night Vision", "Gives you the night vision effect", PotionEffectType.NIGHT_VISION);
    public static final Perk KEEP_EXP = new SimplePerk("Keep EXP", "Don't lose your exp after dying");

    public static final List<Perk> ALL_PERKS = new ArrayList<>();

    static {
        ALL_PERKS.add(FAST_DIGGING);
        ALL_PERKS.add(NIGHT_VISION);
        ALL_PERKS.add(KEEP_EXP);
    }

    public static Perk getPerkByName(String name){
        for (Perk perk : ALL_PERKS) {
            if(perk.getName().equalsIgnoreCase(name.replaceAll("_", " "))){
                return perk;
            }
        }

        return null;
    }

}
