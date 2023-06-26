package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BlockDropItemListener implements Listener {

    private static final EnumMap<Material, Material> instantSmeltBlocks = new EnumMap<>(
            Map.of(
                    Material.RAW_COPPER, Material.COPPER_INGOT,
                    Material.RAW_GOLD, Material.GOLD_INGOT,
                    Material.RAW_IRON, Material.IRON_INGOT
            )
    );

    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent event) {
        Player p = event.getPlayer();
        if (p.getGameMode() == GameMode.SURVIVAL) {
            Collection<Item> drops = event.getItems();

            PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
            Set<Perk> perks = new HashSet<>(perkManager.getEnabledPerks(p));

            boolean hasInstantSmelt = perks.contains(PerkRegistry.INSTANT_SMELT);
            if (hasInstantSmelt) {
                drops.forEach(drop -> {
                    Material dropType = drop.getItemStack().getType();
                    if (instantSmeltBlocks.containsKey(dropType)) {
                        Material ingot = instantSmeltBlocks.get(dropType);
                        drop.getItemStack().setType(ingot);
                    }
                });
            }

            boolean hasTelekinesis = perks.contains(PerkRegistry.TELEKINESIS);
            if (hasTelekinesis) {
                event.setCancelled(true);
                HashMap<Integer, ItemStack> couldNotFit = new HashMap<>();
                drops.forEach(drop -> couldNotFit.putAll(p.getInventory().addItem(drop.getItemStack())));
                couldNotFit.values().forEach(item -> event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item));
            }
        }
    }

}
