package de.oliver.fancyperks.listeners;

import de.oliver.fancylib.MessageHelper;
import de.oliver.fancyperks.FancyPerks;
import de.oliver.fancyperks.perks.Perk;
import de.oliver.fancyperks.perks.PerkRegistry;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsListener {

    public LuckPermsListener() {
        EventBus eventBus = FancyPerks.getInstance().getLuckPerms().getEventBus();
        eventBus.subscribe(FancyPerks.getInstance(), NodeAddEvent.class, this::onNodeAdd);
    }

    private void onNodeAdd(NodeAddEvent event) {
        String permission = event.getNode().getKey().toLowerCase();
        if (!permission.startsWith("fancyperks.perk.")) {
            return;
        }

        String perkStr = permission.substring(permission.lastIndexOf('.') + 1, permission.length());
        Perk perk = PerkRegistry.getPerkByName(perkStr);
        if (perk == null) {
            return;
        }

        if (event.isUser()) {
            User user = (User) event.getTarget();
            Player p = Bukkit.getPlayer(user.getUniqueId());
            if (p == null) {
                return;
            }

            if (event.getNode().getValue() && perk.hasPermission(p)) {
                perk.grant(p);
                MessageHelper.success(p, "Automatically enabled the " + perk.getDisplayName() + " perk");
            } else if (!perk.hasPermission(p)) {
                perk.revoke(p);
                MessageHelper.success(p, "Automatically disabled the " + perk.getDisplayName() + " perk");
            }
        }

    }

}
