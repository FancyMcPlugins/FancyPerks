package de.oliver.fancyperks.gui.inventoryClick;

import de.oliver.fancyperks.gui.inventoryClick.impl.CancelInventoryItemClick;
import de.oliver.fancyperks.gui.inventoryClick.impl.ChangePageInventoryItemClick;
import de.oliver.fancyperks.gui.inventoryClick.impl.TogglePerkInventoryItemClick;

import java.util.HashMap;
import java.util.Map;

public class ItemClickRegistry {

    private static final Map<String, InventoryItemClick> inventoryItemClickMap = new HashMap<>();

    public static InventoryItemClick getInventoryItemClick(String id){
        return inventoryItemClickMap.getOrDefault(id, InventoryItemClick.EMPTY);
    }

    private static void registerInventoryItemClick(InventoryItemClick inventoryItemClick){
        inventoryItemClickMap.put(inventoryItemClick.getId(), inventoryItemClick);
    }


    public static void register(){
        registerInventoryItemClick(ChangePageInventoryItemClick.INSTANCE);
        registerInventoryItemClick(TogglePerkInventoryItemClick.INSTANCE);
        registerInventoryItemClick(CancelInventoryItemClick.INSTANCE);
    }

}
