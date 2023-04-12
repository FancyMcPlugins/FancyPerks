package de.oliver.gui.inventoryClick;

import de.oliver.gui.inventoryClick.impl.ChangePageInventoryItemClick;
import de.oliver.gui.inventoryClick.impl.PerkInventoryItemClick;

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
        registerInventoryItemClick(PerkInventoryItemClick.INSTANCE);
    }

}
