package de.oliver.fancyperks.listeners;

import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.PerkManager;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BlockBreakListener implements Listener {

    private static final EnumMap<Material, Material> instantSmeltBlocks = new EnumMap<>(
        Map.of(
            Material.RAW_COPPER, Material.COPPER_INGOT,
            Material.RAW_GOLD, Material.GOLD_INGOT,
            Material.RAW_IRON, Material.IRON_INGOT
        )
    );

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();

        if (p.getGameMode() == GameMode.SURVIVAL) {
            event.setDropItems(false);

            Collection<ItemStack> drops = event.getBlock().getDrops();
            ItemStack handItem = p.getInventory().getItemInMainHand();

            PerkManager perkManager = FancyPerks.getInstance().getPerkManager();
            Set<Perk> perks = new HashSet<>(perkManager.getEnabledPerks(p));

            if (handItem.getType() != Material.AIR && !handItem.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) && !handItem.containsEnchantment(Enchantment.SILK_TOUCH)) {
                boolean hasInstantSmelt = perks.contains(PerkRegistry.INSTANT_SMELT);
                if (hasInstantSmelt) {
                    drops.forEach(drop -> {
                        Material dropType = drop.getType();
                        if (instantSmeltBlocks.containsKey(dropType)) {
                            Material ingot = instantSmeltBlocks.get(dropType);
                            drop.setType(ingot);
                        }
                    });
                }
            }

            boolean hasTelekinesis = perks.contains(PerkRegistry.TELEKINESIS);
            if (!event.getBlock().getType().isInteractable()) {
                if (hasTelekinesis) {
                    HashMap<Integer, ItemStack> couldNotFit = new HashMap<>();
                    drops.forEach(drop -> couldNotFit.putAll(p.getInventory().addItem(drop)));
                    couldNotFit.values().forEach(item -> event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item));
                } else {
                    drops.forEach(drop -> event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop));
                }
            }
        }

    }

}
