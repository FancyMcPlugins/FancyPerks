package de.oliver.perks;

import de.oliver.FancyPerks;
import de.oliver.PerkManager;
import de.oliver.gui.inventoryClick.InventoryItemClick;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class Perk {

    public static final NamespacedKey PERK_KEY = new NamespacedKey(FancyPerks.getInstance(), "perk");

    protected static final PerkManager perkManager = FancyPerks.getInstance().getPerkManager();

    protected final String name;
    protected final String description;

    public Perk(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void grant(Player player){
        perkManager.addPerk(player, this);
    }

    public void revoke(Player player){
        perkManager.removePerk(player, this);
    }

    public ItemStack getDisplayItem(Player player){
        ItemStack item = new ItemStack(Material.DIAMOND);
        item.editMeta(itemMeta -> {
            itemMeta.displayName(Component.text(name));

            itemMeta.getPersistentDataContainer().set(InventoryItemClick.ON_CLICK_KEY, PersistentDataType.STRING, "perk");
            itemMeta.getPersistentDataContainer().set(PERK_KEY, PersistentDataType.STRING, name);
        });

        return item;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
