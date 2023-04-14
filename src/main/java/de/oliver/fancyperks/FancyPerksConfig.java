package de.oliver.fancyperks;

import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class FancyPerksConfig {

    private boolean muteVersionNotification;
    private Map<Perk, Boolean> perks; // perk, enabled or not

    public void reload(){
        FancyPerks.getInstance().reloadConfig();
        FileConfiguration config = FancyPerks.getInstance().getConfig();

        muteVersionNotification = (boolean) getOrDefault(config, "mute_version_notification", false);

        perks = new HashMap<>();
        for (Perk perk : PerkRegistry.ALL_PERKS) {
            boolean isEnabled = (boolean) getOrDefault(config, "perks." + perk.getSystemName() + ".enabled", true);
            perks.put(perk, isEnabled);
        }

        FancyPerks.getInstance().saveConfig();
    }

    public boolean isMuteVersionNotification() {
        return muteVersionNotification;
    }

    /**
     * @return true if enabled, false if not
     */
    public Map<Perk, Boolean> getPerks() {
        return perks;
    }

    public static Object getOrDefault(FileConfiguration config, String path, Object defaultVal){
        if(!config.contains(path)){
            config.set(path, defaultVal);
            return defaultVal;
        }

        return config.get(path);
    }

}
