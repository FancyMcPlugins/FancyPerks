package de.oliver.fancyperks;

import de.oliver.fancylib.ConfigHelper;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import de.oliver.fancyperks.perks.impl.LavaRunnerPerk;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class FancyPerksConfig {

    private boolean muteVersionNotification;
    private boolean activatePerkOnPermissionSet;

    public void reload() {
        FancyPerks.getInstance().reloadConfig();
        FileConfiguration config = FancyPerks.getInstance().getConfig();

        muteVersionNotification = (boolean) ConfigHelper.getOrDefault(config, "mute_version_notification", false);
        activatePerkOnPermissionSet = (boolean) ConfigHelper.getOrDefault(config, "activate_perk_on_permission_set", false);

        for (Perk perk : PerkRegistry.ALL_PERKS) {
            String displayName = (String) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".name", perk.getDisplayName());
            perk.setDisplayName(displayName);

            String description = (String) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".description", perk.getDescription());
            perk.setDescription(description);

            boolean isEnabled = (boolean) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".enabled", true);
            perk.setEnabled(isEnabled);

            List<String> disabledWorlds = (List<String>) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".disabled_worlds", new ArrayList<>());
            perk.setDisabledWorlds(disabledWorlds);

            boolean buyable = (boolean) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".buyable", true);
            perk.setBuyable(buyable);

            double price = (double) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".price", 1000d);
            perk.setPrice(price);

            if (perk instanceof LavaRunnerPerk lavaRunnerPerk) {
                int radius = (int) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".radius", 4);
                int dissolutionTime = (int) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".dissolution_time", 3000);

                lavaRunnerPerk.setRadius(radius);
                lavaRunnerPerk.setDissolutionTime(dissolutionTime);
            }
        }

        FancyPerks.getInstance().saveConfig();
    }

    public boolean isMuteVersionNotification() {
        return muteVersionNotification;
    }

    public boolean isActivatePerkOnPermissionSet() {
        return activatePerkOnPermissionSet;
    }
}
