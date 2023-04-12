package de.oliver.perks;

import de.oliver.FancyPerks;
import de.oliver.PerkManager;
import de.oliver.utils.MessageHelper;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public abstract class Perk {

    public static final NamespacedKey PERK_KEY = new NamespacedKey(FancyPerks.getInstance(), "perk");

    protected static final PerkManager perkManager = FancyPerks.getInstance().getPerkManager();

    protected final String name;
    protected final String description;
    protected final ItemStack displayItem;

    public Perk(String name, String description, ItemStack displayItem) {
        this.name = name;
        this.description = description;
        this.displayItem = displayItem;

        displayItem.editMeta(itemMeta -> {
            itemMeta.displayName(MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<green>" + name + "</green>"), TextDecoration.ITALIC));
            itemMeta.lore(Arrays.asList(
                    MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<gray>" + description + "</gray>"), TextDecoration.ITALIC)
            ));
        });
    }

    public void grant(Player player){
        perkManager.addPerk(player, this);
    }

    public void revoke(Player player){
        perkManager.removePerk(player, this);
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
