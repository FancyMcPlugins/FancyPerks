package de.oliver.fancyperks.perks;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancylib.gui.inventoryClick.InventoryItemClick;
import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
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

    protected final String systemName;
    protected String displayName;
    protected String description;
    protected final ItemStack displayItem;

    protected boolean enabled;
    protected boolean buyable;
    protected double price;

    public Perk(String systemName, String displayName, String description, ItemStack displayItem) {
        this.systemName = systemName;
        this.displayName = displayName;
        this.description = description;
        this.displayItem = displayItem;
        this.enabled = true;
        this.buyable = false;
    }

    public boolean hasPermission(Player player){
        if(player.hasPermission("fancyperks.perk.*")){
            return true;
        }

        return player.hasPermission("fancyperks.perk." + systemName);
    }

    public void grant(Player player){
        perkManager.enablePerk(player, this);
    }

    public void revoke(Player player){
        perkManager.disablePerk(player, this);
    }

    public ItemStack getDisplayItem() {
        ItemStack item = displayItem.clone();

        item.editMeta(itemMeta -> {
            final String primaryColor = MessageHelper.getPrimaryColor();
            itemMeta.displayName(MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<color:" + primaryColor + ">" + displayName + "</color>"), TextDecoration.ITALIC));
            itemMeta.lore(Arrays.asList(
                    MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<gray>" + description + "</gray>"), TextDecoration.ITALIC)
            ));

            itemMeta.getPersistentDataContainer().set(InventoryItemClick.ON_CLICK_KEY, PersistentDataType.STRING, "cancelClick");
        });

        return item;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
