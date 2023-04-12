package de.oliver.gui.customInventories;

import de.oliver.FancyPerks;
import org.bukkit.NamespacedKey;

public interface PageInventory {

    void loadPage(int page);

    NamespacedKey PAGE_KEY = new NamespacedKey(FancyPerks.getInstance(), "page");

}
