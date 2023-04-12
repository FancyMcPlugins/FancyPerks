package de.oliver;

import de.oliver.commands.PerksCMD;
import de.oliver.gui.inventoryClick.ItemClickRegistry;
import de.oliver.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FancyPerks extends JavaPlugin {

    private static FancyPerks instance;
    private final PerkManager perkManager;

    public FancyPerks() {
        instance = this;
        perkManager = new PerkManager();
    }

    @Override
    public void onEnable() {
        getCommand("perks").setExecutor(new PerksCMD());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), instance);
        pluginManager.registerEvents(new EntityPotionEffectListener(), instance);
        pluginManager.registerEvents(new PlayerDeathListener(), instance);
        pluginManager.registerEvents(new InventoryClickListener(), instance);
        pluginManager.registerEvents(new FoodLevelChangeListener(), instance);

        ItemClickRegistry.register();
    }

    @Override
    public void onDisable() {

    }

    public PerkManager getPerkManager() {
        return perkManager;
    }

    public static FancyPerks getInstance() {
        return instance;
    }
}
