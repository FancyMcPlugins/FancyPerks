package de.oliver.fancyperks.gui.inventoryClick;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancylib.gui.inventoryClick.InventoryItemClick;
import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.gui.customInventories.PerksInventory;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyPerkInventoryItemClick implements InventoryItemClick {

    public static final BuyPerkInventoryItemClick INSTANCE = new BuyPerkInventoryItemClick();

    private final static List<NamespacedKey> REQUIRED_KEYS = Collections.singletonList(
            Perk.PERK_KEY
    );

    private BuyPerkInventoryItemClick(){ }

    @Override
    public String getId() {
        return "buyPerk";
    }

    @Override
    public void onClick(InventoryClickEvent event, Player player) {
        Player p = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(item != null && InventoryItemClick.hasKeys(item, REQUIRED_KEYS)){
            event.setCancelled(true);
            String perkName = item.getItemMeta().getPersistentDataContainer().get(Perk.PERK_KEY, PersistentDataType.STRING);
            Perk perk = PerkRegistry.getPerkByName(perkName);

            if(perk == null || !perk.isBuyable() || !FancyPerks.getInstance().isUsingVault()){
                return;
            }

            Economy economy = FancyPerks.getInstance().getVaultEconomy();
            Permission permission = FancyPerks.getInstance().getVaultPermission();

            if (!economy.has(player, perk.getPrice())) {
                MessageHelper.error(player, "You don't own enough money to buy this perk");
                return;
            }

            EconomyResponse response = economy.withdrawPlayer(player, perk.getPrice());
            if (!response.transactionSuccess()) {
                MessageHelper.warning(player, "Transaction was not successful");
                return;
            }

            if (!perk.getTime().equals("-1")) {
                Node node = Node.builder("fancyperks.perk." + perk.getSystemName())
                        .value(true)
                        .expiry(parseTime(perk.getTime()))
                        .build();
                User user = FancyPerks.getInstance().getLuckPerms().getUserManager().getUser(player.getUniqueId());
                if (user != null) {
                    user.data().add(node);
                    FancyPerks.getInstance().getLuckPerms().getUserManager().saveUser(user);
                } else {
                    MessageHelper.warning(player, "Could not find you");
                }
            } else if (!permission.playerAdd(null, player, "fancyperks.perk." + perk.getSystemName())){
                MessageHelper.warning(player, "Could not give you the perk");
                return;
            }

            perk.grant(p);
            event.setCurrentItem(PerksInventory.getEnabledPerkItem(perk));
        }
    }

    public static Duration parseTime(String time) {
        Pattern pattern = Pattern.compile("(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?");
        Matcher matcher = pattern.matcher(time);

        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if (matcher.matches()) {
            String dayPart = matcher.group(1);
            String hourPart = matcher.group(2);
            String minutePart = matcher.group(3);
            String secondPart = matcher.group(4);

            if (dayPart != null) {
                days = Integer.parseInt(dayPart.substring(0, dayPart.length() - 1));
            }
            if (hourPart != null) {
                hours = Integer.parseInt(hourPart.substring(0, hourPart.length() - 1));
            }
            if (minutePart != null) {
                minutes = Integer.parseInt(minutePart.substring(0, minutePart.length() - 1));
            }
            if (secondPart != null) {
                seconds = Integer.parseInt(secondPart.substring(0, secondPart.length() - 1));
            }

            //System.out.println("Parsed duration: " + duration);
            return Duration.ofDays(days)
                    .plusHours(hours)
                    .plusMinutes(minutes)
                    .plusSeconds(seconds);
        } else {
            FancyPerks.getInstance().getLogger().warning("Invalid time format " + time);
        }
        return null;
    }

}
