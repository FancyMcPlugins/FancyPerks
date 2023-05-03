package de.oliver.fancyperks;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PerkManager {

    private final Map<UUID, List<Perk>> playerPerks;
    private final File playersConfig = new File(FancyPerks.getInstance().getDataFolder().getAbsolutePath() + "/players.yml");

    public PerkManager() {
        this.playerPerks = new HashMap<>();
    }

    public List<Perk> getEnabledPerks(Player player){
        return playerPerks.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public boolean hasPerkEnabled(Player player, Perk perk){
        return playerPerks.containsKey(player.getUniqueId()) && playerPerks.get(player.getUniqueId()).contains(perk);
    }

    public void enablePerk(Player player, Perk perk){
        List<Perk> perks = getEnabledPerks(player);
        if(!perks.contains(perk)){
            perks.add(perk);
        }

        playerPerks.put(player.getUniqueId(), perks);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersConfig);
        config.set("perks." + player.getUniqueId() + "." + perk.getSystemName(), true);
        try {
            config.save(playersConfig);
        } catch (IOException e) {
            e.printStackTrace();
            MessageHelper.error(Bukkit.getConsoleSender(), "Could not save player config");
        }
    }

    public void disablePerk(Player player, Perk perk){
        List<Perk> perks = getEnabledPerks(player);
        perks.remove(perk);

        playerPerks.put(player.getUniqueId(), perks);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersConfig);
        config.set("perks." + player.getUniqueId() + "." + perk.getSystemName(), false);
        try {
            config.save(playersConfig);
        } catch (IOException e) {
            e.printStackTrace();
            MessageHelper.error(Bukkit.getConsoleSender(), "Could not save player config");
        }
    }

    public void loadFromConfig(){
        playerPerks.clear();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersConfig);
        if(!config.isConfigurationSection("perks")) return;

        for (String uuidStr : config.getConfigurationSection("perks").getKeys(false)) {
            UUID uuid = UUID.fromString(uuidStr);
            for (String perkStr : config.getConfigurationSection("perks." + uuidStr).getKeys(false)) {
                Perk perk = PerkRegistry.getPerkByName(perkStr);
                if(perk == null){
                    continue;
                }

                boolean isActivated = config.getBoolean("perks." + uuidStr + "." + perkStr, false);
                if(isActivated){
                    List<Perk> current = playerPerks.getOrDefault(uuid, new ArrayList<>());
                    current.add(perk);
                    playerPerks.put(uuid, current);
                }
            }
        }
    }
}
