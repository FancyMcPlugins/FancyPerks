package de.oliver.gui.customInventories.impl;

import de.oliver.gui.customInventories.CustomPlayerInventory;
import de.oliver.gui.customInventories.PageInventory;
import de.oliver.gui.inventoryClick.InventoryItemClick;
import de.oliver.perks.Perk;
import de.oliver.perks.PerkRegistry;
import de.oliver.utils.MessageHelper;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class PerksInventory extends CustomPlayerInventory implements PageInventory {
    public PerksInventory(Player player) {
        super(player, 6, MiniMessage.miniMessage().deserialize("Perks"));

        loadPage(1);
    }

    @Override
    public void loadPage(int page) {
        inventory.clear();

        List<Perk> perks = PerkRegistry.ALL_PERKS;
        final int itemsPerPage = 5*9;

//        for (int i = 0; i < inventory.getSize(); i++) {
//            inventory.setItem(i, InventoryUtils.getPlaceholder());
//        }

        boolean isLastPage = false;

        for (int i = 0; i < itemsPerPage; i++) {
            int index = (page-1) * itemsPerPage + i;

            if(perks.size() <= index){
                isLastPage = true;
                break;
            }

            Perk perk = perks.get(index);

            inventory.setItem(i, perk.getDisplayItem(player));
        }

        if(page > 1) {
            ItemStack previousPage = new ItemStack(Material.ARROW);
            previousPage.editMeta(itemMeta -> {
                itemMeta.displayName(MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<gradient:gold:yellow:gold>Previous page</gradient>"), TextDecoration.ITALIC));

                itemMeta.getPersistentDataContainer().set(PageInventory.PAGE_KEY, PersistentDataType.INTEGER, page-1);
                itemMeta.getPersistentDataContainer().set(InventoryItemClick.ON_CLICK_KEY, PersistentDataType.STRING, "changePage");
            });

            inventory.setItem(46, previousPage);
        }

        if(!isLastPage) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            nextPage.editMeta(itemMeta -> {
                itemMeta.displayName(MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<gradient:gold:yellow:gold>Next page</gradient>"), TextDecoration.ITALIC));

                itemMeta.getPersistentDataContainer().set(PageInventory.PAGE_KEY, PersistentDataType.INTEGER, page+1);
                itemMeta.getPersistentDataContainer().set(InventoryItemClick.ON_CLICK_KEY, PersistentDataType.STRING, "changePage");
            });

            inventory.setItem(52, nextPage);
        }
    }
}
