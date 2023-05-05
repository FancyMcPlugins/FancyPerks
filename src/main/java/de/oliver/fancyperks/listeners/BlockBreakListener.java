package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockBreakListener implements Listener {

    private static final Map<Material, Material> instantSmeltBlocks = new HashMap<>();
    static {
        instantSmeltBlocks.put(Material.RAW_IRON, Material.IRON_INGOT);
        instantSmeltBlocks.put(Material.RAW_GOLD, Material.GOLD_INGOT);
        instantSmeltBlocks.put(Material.RAW_COPPER, Material.COPPER_INGOT);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player p = event.getPlayer();

        if(p.getGameMode() != GameMode.SURVIVAL){
            return;
        }

        Collection<ItemStack> drops = event.getBlock().getDrops();
        event.setDropItems(false);

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);

        boolean hasInstantSmelt = perks.contains(PerkRegistry.INSTANT_SMELT);
        if(hasInstantSmelt){
            drops.forEach(drop -> {
                if (instantSmeltBlocks.containsKey(drop.getType())) {
                    Material ingot = instantSmeltBlocks.get(drop.getType());
                    drop.setType(ingot);
                }
            });
        }

        boolean hasTelekinesis = perks.contains(PerkRegistry.TELEKINESIS);
        if(hasTelekinesis){
            for (ItemStack drop : drops) {
                HashMap<Integer, ItemStack> couldNotFit = p.getInventory().addItem(drop);
                for (ItemStack item : couldNotFit.values()) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
                }
            }
        } else {
            for (ItemStack drop : drops) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
            }
        }

    }

}
