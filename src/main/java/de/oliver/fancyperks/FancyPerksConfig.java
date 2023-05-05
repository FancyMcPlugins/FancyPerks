package de.oliver.fancyperks;

import de.oliver.fancylib.ConfigHelper;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.configuration.file.FileConfiguration;

public class FancyPerksConfig {

    private boolean muteVersionNotification;

    public void reload(){
        FancyPerks.getInstance().reloadConfig();
        FileConfiguration config = FancyPerks.getInstance().getConfig();

        muteVersionNotification = (boolean) ConfigHelper.getOrDefault(config, "mute_version_notification", false);

        for (Perk perk : PerkRegistry.ALL_PERKS) {
            String displayName = (String) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".name", perk.getDisplayName());
            perk.setDisplayName(displayName);

            String description = (String) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".description", perk.getDescription());
            perk.setDescription(description);

            boolean isEnabled = (boolean) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".enabled", true);
            perk.setEnabled(isEnabled);

            boolean buyable = (boolean) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".buyable", true);
            perk.setBuyable(buyable);

            double price = (double) ConfigHelper.getOrDefault(config, "perks." + perk.getSystemName() + ".price", 1000d);
            perk.setPrice(price);
        }

        FancyPerks.getInstance().saveConfig();
    }

    public boolean isMuteVersionNotification() {
        return muteVersionNotification;
    }

}
