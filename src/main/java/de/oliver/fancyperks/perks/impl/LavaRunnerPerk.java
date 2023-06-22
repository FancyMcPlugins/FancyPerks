package de.oliver.fancyperks.perks.impl;

import de.oliver.fancyperks.FancyPerks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class LavaRunnerPerk extends SimplePerk{
    public static final Material BLOCK_TYPE = Material.OBSIDIAN;

    private int radius;
    private final Map<Player, PlayerBlockCache> playerBlockCache;
    public LavaRunnerPerk(String systemName, String name, String description, ItemStack displayItem) {
        super(systemName, name, description, displayItem);
        playerBlockCache = new HashMap<>();
    }

    public void updateBlocks(Player player){
        if(!isEnabled()){
            return;
        }

        PlayerBlockCache cache = getCache(player);

        Set<Location> keep = new HashSet<>();
        Set<Location> remove = new HashSet<>();

        Location playerLoc = player.getLocation();

        // check what blocks should be kept
        if(player.isOnline()) {
            for (int dx = -radius; dx < radius; dx++) {
                for (int dz = -radius; dz < radius; dz++) {
                    for (int dy = 1; dy <= 3; dy++) {
                        double distance = Math.sqrt(dx * dx + dz * dz);
                        if (distance > radius) {
                            continue;
                        }

                        Location loc = new Location(playerLoc.getWorld(), playerLoc.getBlockX() + dx, playerLoc.getBlockY() - dy, playerLoc.getBlockZ() + dz);

                        if (loc.getBlock().getType() == Material.LAVA || loc.getBlock().getType() == BLOCK_TYPE && loc.getBlock().hasMetadata("LavaRunner")) {
                            keep.add(loc);
                        }
                    }
                }
            }
        }

        // check what blocks should be removed
        for (Location blockLoc : cache.blocks().keySet()) {
            if(cache.isBlockExpired(blockLoc) && !keep.contains(blockLoc)){
                remove.add(blockLoc);
            }
        }


        // remove blocks
        if(remove.size() > 0) {
            FancyPerks.getInstance().getFancyScheduler().runTask(remove.iterator().next(), () -> {
                for (Location loc : remove) {
                    cache.removeBlock(loc);

                    Block block = loc.getBlock();
                    if (block.getType() != BLOCK_TYPE) {
                        continue;
                    }

                    if (!block.hasMetadata("LavaRunner")) {
                        continue;
                    }

                    block.setType(Material.LAVA);
                }
            });
        }

        // place blocks
        if(keep.size() > 0) {
            FancyPerks.getInstance().getFancyScheduler().runTask(keep.iterator().next(), () -> {
                for (Location loc : keep) {
                    cache.addBlock(loc);

                    Block block = loc.getBlock();

                    block.setType(BLOCK_TYPE);
                    block.setMetadata("LavaRunner", new FixedMetadataValue(FancyPerks.getInstance(), true));
                }
            });
        }
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public PlayerBlockCache getCache(Player player){
        if (playerBlockCache.containsKey(player)) {
            return playerBlockCache.get(player);
        }

        PlayerBlockCache cache = new PlayerBlockCache(new HashMap<>());
        playerBlockCache.put(player, cache);
        return cache;
    }

    public Map<Player, PlayerBlockCache> getPlayerBlockCache() {
        return playerBlockCache;
    }

    public record PlayerBlockCache(HashMap<Location, Long> blocks) {
        public static final long KEEP_BLOCK_DURATION = 1000L * 3L;

        public void addBlock(Location location) {
                long current = System.currentTimeMillis();
                blocks.put(location, current + KEEP_BLOCK_DURATION);
            }

            public void removeBlock(Location location) {
                blocks.remove(location);
            }

            public boolean isBlockExpired(Location location) {
                if (!blocks.containsKey(location)) {
                    return false;
                }

                long end = blocks.get(location);
                long current = System.currentTimeMillis();

                return current >= end;
            }
        }
}
