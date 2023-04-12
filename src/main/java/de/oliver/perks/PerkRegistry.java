package de.oliver.perks;

import de.oliver.perks.impl.EffectPerk;
import de.oliver.perks.impl.NoHungerPerk;
import de.oliver.perks.impl.SimplePerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PerkRegistry {

    public static final Perk FAST_DIGGING = new EffectPerk("Fast Digging", "Gives you the haste effect", new ItemStack(Material.NETHERITE_PICKAXE), PotionEffectType.FAST_DIGGING);
    public static final Perk NIGHT_VISION = new EffectPerk("Night Vision", "Gives you the night vision effect", new ItemStack(Material.SPYGLASS), PotionEffectType.NIGHT_VISION);
    public static final Perk KEEP_EXP = new SimplePerk("Keep EXP", "Don't lose your exp after dying", new ItemStack(Material.EXPERIENCE_BOTTLE));
    public static final Perk NO_HUNGER = new NoHungerPerk("No Hunger", "You don't need to eat again", new ItemStack(Material.COOKED_CHICKEN));
    public static final Perk KEEP_INVENTORY = new SimplePerk("Keep Inventory", "Don't lose your items after dying", new ItemStack(Material.CHEST));
    public static final Perk NO_FIRE_DAMAGE = new SimplePerk("No Fire Damage", "Don't get hurt by fire", new ItemStack(Material.FIRE_CHARGE));

    public static final List<Perk> ALL_PERKS = new ArrayList<>();

    static {
        ALL_PERKS.add(FAST_DIGGING);
        ALL_PERKS.add(NIGHT_VISION);
        ALL_PERKS.add(KEEP_EXP);
        ALL_PERKS.add(NO_HUNGER);
        ALL_PERKS.add(KEEP_INVENTORY);
        ALL_PERKS.add(NO_FIRE_DAMAGE);
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
