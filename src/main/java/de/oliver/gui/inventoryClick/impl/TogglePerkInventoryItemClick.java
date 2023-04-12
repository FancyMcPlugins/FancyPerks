package de.oliver.gui.inventoryClick.impl;

import de.oliver.FancyPerks;
import de.oliver.gui.customInventories.impl.PerksInventory;
import de.oliver.gui.inventoryClick.InventoryItemClick;
import de.oliver.perks.Perk;
import de.oliver.perks.PerkRegistry;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.List;

public class TogglePerkInventoryItemClick implements InventoryItemClick {

    public static final TogglePerkInventoryItemClick INSTANCE = new TogglePerkInventoryItemClick();

    private final static List<NamespacedKey> REQUIRED_KEYS = Collections.singletonList(
            Perk.PERK_KEY
    );

    private TogglePerkInventoryItemClick(){ }

    @Override
    public String getId() {
        return "togglePerk";
    }

    @Override
    public void onClick(InventoryClickEvent event, Player player) {
        Player p = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(item != null && InventoryItemClick.hasKeys(item, REQUIRED_KEYS)){
            event.setCancelled(true);

            String perkName = item.getItemMeta().getPersistentDataContainer().get(Perk.PERK_KEY, PersistentDataType.STRING);
            Perk perk = PerkRegistry.getPerkByName(perkName);

            if(perk == null){
                return;
            }

            boolean hasPerk = FancyPerks.getInstance().getPerkManager().hasPerk(p, perk);

            if(hasPerk){
                perk.revoke(p);
                event.setCurrentItem(PerksInventory.getDisabledPerkItem(perk));
                p.sendMessage(Component.text("Revoked " + perk.getName()));
            } else {
                perk.grant(p);
                event.setCurrentItem(PerksInventory.getEnabledPerkItem(perk));
                p.sendMessage(Component.text("Granted " + perk.getName()));
            }
        }
    }
}
