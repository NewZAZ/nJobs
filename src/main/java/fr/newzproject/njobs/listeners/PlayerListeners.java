package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.boosters.BoosterItem;
import fr.newzproject.njobs.boosters.BoosterManager;
import fr.newzproject.njobs.jobs.JobsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        JobsManager.getInstance().init(event.getPlayer().getUniqueId());
        BoosterManager.getInstance().stopBoosters(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        JobsManager.getInstance().save(event.getPlayer().getUniqueId());
        BoosterManager.getInstance().stopBoosters(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        JobsManager.getInstance().save(event.getPlayer().getUniqueId());
        BoosterManager.getInstance().stopBoosters(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack == null) return;
        if (!itemStack.hasItemMeta()) return;
        if (!itemStack.getItemMeta().hasDisplayName()) return;
        if (!itemStack.getItemMeta().hasLore()) return;

        new BoosterItem().onBoosterInteract(event);

    }
}
