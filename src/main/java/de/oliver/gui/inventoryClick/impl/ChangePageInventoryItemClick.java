package de.oliver.gui.inventoryClick.impl;

import de.oliver.gui.customInventories.PageInventory;
import de.oliver.gui.inventoryClick.InventoryItemClick;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.List;

public class ChangePageInventoryItemClick implements InventoryItemClick {

    public static final ChangePageInventoryItemClick INSTANCE = new ChangePageInventoryItemClick();

    private final static List<NamespacedKey> REQUIRED_KEYS = Collections.singletonList(
            PageInventory.PAGE_KEY
    );

    private ChangePageInventoryItemClick() { }

    @Override
    public String getId() {
        return "changePage";
    }

    @Override
    public void onClick(InventoryClickEvent event, Player player) {
        ItemStack item = event.getCurrentItem();

        if(item != null && InventoryItemClick.hasKeys(item, REQUIRED_KEYS)){
            event.setCancelled(true);

            int page = item.getItemMeta().getPersistentDataContainer().get(PageInventory.PAGE_KEY, PersistentDataType.INTEGER);

            if(event.getInventory().getHolder() == null || !(event.getInventory().getHolder() instanceof PageInventory pageInventory)){
                return;
            }

            pageInventory.loadPage(page);
        }

    }
}
