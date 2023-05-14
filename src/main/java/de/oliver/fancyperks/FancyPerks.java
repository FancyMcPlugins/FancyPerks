package de.oliver.fancyperks;

import de.oliver.fancylib.FancyLib;
import de.oliver.fancylib.Metrics;
import de.oliver.fancylib.VersionFetcher;
import de.oliver.fancylib.serverSoftware.ServerSoftware;
import de.oliver.fancyperks.commands.FancyPerksCMD;
import de.oliver.fancyperks.commands.PerksCMD;
import de.oliver.fancyperks.gui.inventoryClick.BuyPerkInventoryItemClick;
import de.oliver.fancyperks.gui.inventoryClick.TogglePerkInventoryItemClick;
import de.oliver.fancyperks.listeners.*;
import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class FancyPerks extends JavaPlugin {

    private static FancyPerks instance;
    private final PerkManager perkManager;
    private final VersionFetcher versionFetcher;
    private final FancyPerksConfig config;
    private boolean usingVault;
    private Economy vaultEconomy;
    private Permission vaultPermission;
    private boolean usingLuckPerms;
    private LuckPerms luckPerms;

    public FancyPerks() {
        instance = this;
        loadDependencies();

        perkManager = new PerkManager();
        versionFetcher = new VersionFetcher("https://api.modrinth.com/v2/project/fancyperks/version", "https://modrinth.com/plugin/fancyperks/versions");
        config = new FancyPerksConfig();
        usingVault = false;
    }

    @Override
    public void onEnable() {
        FancyLib.setPlugin(this);

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

        if(!ServerSoftware.isPaper()){
            getLogger().warning("--------------------------------------------------");
            getLogger().warning("It is recommended to use Paper as server software.");
            getLogger().warning("Because you are not using paper, the plugin");
            getLogger().warning("might not work correctly.");
            getLogger().warning("--------------------------------------------------");
        }

        usingVault = pluginManager.getPlugin("Vault") != null;
        if(usingVault){
            RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (economyProvider != null) {
                vaultEconomy = economyProvider.getProvider();
                getLogger().info("Found vault economy");
            } else {
                usingVault = false;
                getLogger().warning("Could not find any economy plugin");
            }

            RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);
            if (permissionProvider != null) {
                vaultPermission = permissionProvider.getProvider();
                getLogger().info("Found vault permission");
            } else {
                usingVault = false;
                getLogger().warning("Could not find any permission plugin");
            }
        }

        usingLuckPerms = pluginManager.isPluginEnabled("LuckPerms");
        if(usingLuckPerms){
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckPerms = provider.getProvider();
            } else {
                usingLuckPerms = false;
            }
        }


        Metrics metrics = new Metrics(instance, 18195);

        config.reload();

        getCommand("fancyperks").setExecutor(new FancyPerksCMD());
        getCommand("perks").setExecutor(new PerksCMD());

        pluginManager.registerEvents(new PlayerJoinListener(), instance);
        pluginManager.registerEvents(new EntityPotionEffectListener(), instance);
        pluginManager.registerEvents(new PlayerDeathListener(), instance);
        pluginManager.registerEvents(new FoodLevelChangeListener(), instance);
        pluginManager.registerEvents(new EntityDamageListener(), instance);
        pluginManager.registerEvents(new EntityDeathListener(), instance);
        pluginManager.registerEvents(new EntityTargetLivingEntityListener(), instance);
        pluginManager.registerEvents(new BlockDropItemListener(), instance);
        if(usingLuckPerms && config.isActivatePerkOnPermissionSet()){
            new LuckPermsListener();
        }


        TogglePerkInventoryItemClick.INSTANCE.register();
        BuyPerkInventoryItemClick.INSTANCE.register();

        perkManager.loadFromConfig();
    }

    @Override
    public void onDisable() {

    }

    private void loadDependencies(){
        BukkitLibraryManager paperLibraryManager = new BukkitLibraryManager(instance);
        paperLibraryManager.addJitPack();

        boolean hasFancyLib;
        try{
            Class.forName("de.oliver.fancylib.FancyLib");
            hasFancyLib = true;
        } catch (ClassNotFoundException e){
            hasFancyLib = false;
        }

        if(!hasFancyLib){
            getLogger().info("Loading FancyLib");
            Library fancyLib = Library.builder()
                    .groupId("com{}github{}FancyMcPlugins")
                    .artifactId("FancyLib")
                    .version("225ba14e03")
                    .build();
            paperLibraryManager.loadLibrary(fancyLib);
        }
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

    public boolean isUsingVault() {
        return usingVault;
    }

    public Economy getVaultEconomy() {
        return vaultEconomy;
    }

    public Permission getVaultPermission() {
        return vaultPermission;
    }

    public boolean isUsingLuckPerms() {
        return usingLuckPerms;
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public static FancyPerks getInstance() {
        return instance;
    }
}
