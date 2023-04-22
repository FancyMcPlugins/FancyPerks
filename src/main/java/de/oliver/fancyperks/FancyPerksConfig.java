package de.oliver.fancyperks;

import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.configuration.file.FileConfiguration;

public class FancyPerksConfig {

    private boolean muteVersionNotification;

    public void reload(){
        FancyPerks.getInstance().reloadConfig();
        FileConfiguration config = FancyPerks.getInstance().getConfig();

        muteVersionNotification = (boolean) getOrDefault(config, "mute_version_notification", false);

        for (Perk perk : PerkRegistry.ALL_PERKS) {
            boolean isEnabled = (boolean) getOrDefault(config, "perks." + perk.getSystemName() + ".enabled", true);
            perk.setEnabled(isEnabled);

            boolean buyable = (boolean) getOrDefault(config, "perks." + perk.getSystemName() + ".buyable", true);
            perk.setBuyable(buyable);

            double price = (double) getOrDefault(config, "perks." + perk.getSystemName() + ".price", 1000d);
            perk.setPrice(price);
        }

        FancyPerks.getInstance().saveConfig();
    }

    public boolean isMuteVersionNotification() {
        return muteVersionNotification;
    }

    public static Object getOrDefault(FileConfiguration config, String path, Object defaultVal){
        if(!config.contains(path)){
            config.set(path, defaultVal);
            return defaultVal;
        }

        return config.get(path);
    }

}
