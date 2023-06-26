package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();

        if (event.getBlock().hasMetadata("LavaRunner")) {
            event.setCancelled(true);
            event.getBlock().setType(Material.LAVA);
            return;
        }

        PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
        List<Perk> perks = perkManager.getEnabledPerks(p);

        boolean hasAutoPlanting = perks.contains(PerkRegistry.AUTO_PLANTING);
        if (hasAutoPlanting) {
            Block block = event.getBlock();
            BlockData blockData = block.getState().getBlockData();
            if (blockData instanceof Ageable ageable && ageable.getAge() == ageable.getMaximumAge()) {
                FancyPerks.getInstance().getFancyScheduler().runTaskLater(block.getLocation(), 3L, () -> {
                    block.setType(block.getType());
                    ageable.setAge(0);
                    block.setBlockData(ageable);
                });
            }
        }
    }

}
