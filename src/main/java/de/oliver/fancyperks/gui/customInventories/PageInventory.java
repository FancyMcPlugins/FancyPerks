package de.oliver.fancyperks.gui.customInventories;

import de.oliver.fancyperks.FancyPerks;
import org.bukkit.NamespacedKey;

public interface PageInventory {

    void loadPage(int page);

    NamespacedKey PAGE_KEY = new NamespacedKey(FancyPerks.getInstance(), "page");

}
