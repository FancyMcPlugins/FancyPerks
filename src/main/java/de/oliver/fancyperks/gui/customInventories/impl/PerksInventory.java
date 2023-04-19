package de.oliver.fancyperks.gui.customInventories.impl;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.gui.customInventories.CustomPlayerInventory;
import de.oliver.fancyperks.gui.customInventories.PageInventory;
import de.oliver.fancyperks.gui.inventoryClick.InventoryItemClick;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PerksInventory extends CustomPlayerInventory implements PageInventory {
    public PerksInventory(Player player) {
        super(player, 6, MiniMessage.miniMessage().deserialize("Perks"));

        loadPage(1);
    }

    @Override
    public void loadPage(int page) {
        inventory.clear();

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, CustomPlayerInventory.getPlaceholder());
        }

        Map<Perk, Boolean> isPerkEnabled = FancyPerks.getInstance().getFanyPerksConfig().getPerks();
        List<Perk> perks = PerkRegistry.ALL_PERKS.stream().filter(perk -> isPerkEnabled.getOrDefault(perk, true)).toList();
        final int perksPerPage = 2*9;
        int perkIndex = perksPerPage * Math.max(0, page-1);
        boolean isLastPage = false;

        rowLoop:
        for (int row = 0; row < 4; row+=2) {
            for (int col = 0; col < 9; col++) {
                if(perkIndex >= perks.size()){
                    isLastPage = true;
                    break rowLoop;
                }

                int topIndex = row*9+col;
                int bottomIndex = topIndex+9;

                Perk perk = perks.get(perkIndex);
                perkIndex++;

                boolean hasPerk = FancyPerks.getInstance().getPerkManager().hasPerkEnabled(player, perk);

                inventory.setItem(topIndex, perk.getDisplayItem());

                if(hasPerk) inventory.setItem(bottomIndex, getEnabledPerkItem(perk));
                else inventory.setItem(bottomIndex, getDisabledPerkItem(perk));
            }
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

    public static ItemStack getEnabledPerkItem(Perk perk){
        ItemStack item = new ItemStack(Material.GREEN_DYE);

        item.editMeta(itemMeta -> {
            itemMeta.displayName(MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<gradient:dark_green:green>Perk is enabled</gradient>"), TextDecoration.ITALIC));
            itemMeta.lore(Arrays.asList(
                    Component.empty(),
                    MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<yellow>Click to toggle</yellow>"), TextDecoration.ITALIC)
            ));

            itemMeta.getPersistentDataContainer().set(InventoryItemClick.ON_CLICK_KEY, PersistentDataType.STRING, "togglePerk");
            itemMeta.getPersistentDataContainer().set(Perk.PERK_KEY, PersistentDataType.STRING, perk.getName());
        });

        return item;
    }

    public static ItemStack getDisabledPerkItem(Perk perk){
        ItemStack item = new ItemStack(Material.RED_DYE);

        item.editMeta(itemMeta -> {
            itemMeta.displayName(MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<gradient:dark_red:red>Perk is disabled</gradient>"), TextDecoration.ITALIC));
            itemMeta.lore(Arrays.asList(
                    Component.empty(),
                    MessageHelper.removeDecoration(MiniMessage.miniMessage().deserialize("<yellow>Click to toggle</yellow>"), TextDecoration.ITALIC)
            ));

            itemMeta.getPersistentDataContainer().set(InventoryItemClick.ON_CLICK_KEY, PersistentDataType.STRING, "togglePerk");
            itemMeta.getPersistentDataContainer().set(Perk.PERK_KEY, PersistentDataType.STRING, perk.getName());
        });

        return item;
    }
}