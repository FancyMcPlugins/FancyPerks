package de.oliver.fancyperks;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancylib.Metrics;
import de.oliver.fancylib.VersionFetcher;
import de.oliver.fancyperks.commands.FancyPerksCMD;
import de.oliver.fancyperks.commands.PerksCMD;
import de.oliver.fancyperks.gui.inventoryClick.ItemClickRegistry;
import de.oliver.fancyperks.listeners.*;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FancyPerks extends JavaPlugin {

    private static FancyPerks instance;
    private final PerkManager perkManager;
    private final VersionFetcher versionFetcher;
    private final FancyPerksConfig config;

    public FancyPerks() {
        instance = this;
        perkManager = new PerkManager();
        versionFetcher = new VersionFetcher("https://api.modrinth.com/v2/project/fancyperks/version", "https://modrinth.com/plugin/fancyperks/versions");
        config = new FancyPerksConfig();
    }

    @Override
    public void onEnable() {
        MessageHelper.pluginName = getDescription().getName();

        new Thread(() -> {
            ComparableVersion newestVersion = versionFetcher.getNewestVersion();
            ComparableVersion currentVersion = new ComparableVersion(getDescription().getVersion());
            if(newestVersion == null){
                getLogger().warning("Could not fetch latest plugin version");
            } else if (newestVersion.compareTo(currentVersion) > 0) {
                getLogger().warning("-------------------------------------------------------");
                getLogger().warning("You are not using the latest version the FancyPerks plugin.");
                getLogger().warning("Please update to the newest version (" + newestVersion + ").");
                getLogger().warning(versionFetcher.getDownloadUrl());
                getLogger().warning("-------------------------------------------------------");
            }
        }).start();

        PluginManager pluginManager = Bukkit.getPluginManager();
        DedicatedServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();

        String serverSoftware = nmsServer.getServerModName();
        if(!serverSoftware.equals("Paper")){
            getLogger().warning("--------------------------------------------------");
            getLogger().warning("It is recommended to use Paper as server software.");
            getLogger().warning("Because you are not using paper, the plugin");
            getLogger().warning("might not work correctly.");
            getLogger().warning("--------------------------------------------------");
        }

        Metrics metrics = new Metrics(instance, 18195);

        config.reload();

        getCommand("fancyperks").setExecutor(new FancyPerksCMD());
        getCommand("perks").setExecutor(new PerksCMD());

        pluginManager.registerEvents(new PlayerJoinListener(), instance);
        pluginManager.registerEvents(new EntityPotionEffectListener(), instance);
        pluginManager.registerEvents(new PlayerDeathListener(), instance);
        pluginManager.registerEvents(new InventoryClickListener(), instance);
        pluginManager.registerEvents(new FoodLevelChangeListener(), instance);
        pluginManager.registerEvents(new EntityDamageListener(), instance);

        ItemClickRegistry.register();
    }

    @Override
    public void onDisable() {

    }

    public PerkManager getPerkManager() {
        return perkManager;
    }

    public VersionFetcher getVersionFetcher() {
        return versionFetcher;
    }


    public FancyPerksConfig getFanyPerksConfig() {
        return config;
    }

    public static FancyPerks getInstance() {
        return instance;
    }
}
