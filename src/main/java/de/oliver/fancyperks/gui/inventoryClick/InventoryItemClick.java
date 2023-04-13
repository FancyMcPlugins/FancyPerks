package de.oliver.fancyperks.gui.inventoryClick;

import de.oliver.fancyperks.FancyPerks;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;

public interface InventoryItemClick {

    NamespacedKey ON_CLICK_KEY = new NamespacedKey(FancyPerks.getInstance(), "onclick");

    InventoryItemClick EMPTY = new InventoryItemClick() {
        @Override
        public String getId() {
            return "";
        }

        @Override
        public void onClick(InventoryClickEvent event, Player player) { }
    };

    String getId();
    void onClick(InventoryClickEvent event, Player player);

    static boolean hasKeys(ItemStack item, List<NamespacedKey> keys){
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        for (NamespacedKey key : keys) {
            if(!data.has(key)){
                return false;
            }
        }

        return true;
    }

}
