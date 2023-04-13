package de.oliver.fancyperks.perks;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.gui.inventoryClick.InventoryItemClick;
import de.oliver.fancyperks.utils.MessageHelper;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public abstract class Perk {

    public static final NamespacedKey PERK_KEY = new NamespacedKey(FancyPerks.getInstance(), "perk");

    protected static final PerkManager perkManager = FancyPerks.getInstance().getPerkManager();

    protected final String name;
    protected final String systemName;
    protected final String description;
    protected final ItemStack displayItem;

    public Perk(String name, String description, ItemStack displayItem) {
        this.name = name;
        this.systemName = name.replaceAll(" ", "_").toLowerCase();
        this.description = description;
        this.displayItem = displayItem;

        displayItem.editMeta(itemMeta -> {
            itemMeta.displayName(MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<green>" + name + "</green>"), TextDecoration.ITALIC));
            itemMeta.lore(Arrays.asList(
                    MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<gray>" + description + "</gray>"), TextDecoration.ITALIC)
            ));

            itemMeta.getPersistentDataContainer().set(InventoryItemClick.ON_CLICK_KEY, PersistentDataType.STRING, "cancelClick");
        });
    }

    public void grant(Player player){
        perkManager.enablePerk(player, this);
    }

    public void revoke(Player player){
        perkManager.disablePerk(player, this);
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public String getName() {
        return name;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getDescription() {
        return description;
    }
}
