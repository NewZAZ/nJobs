package fr.newzproject.api.listeners;

import fr.newzproject.api.MainApi;
import fr.newzproject.api.custom.events.NInventoryClickEvent;
import fr.newzproject.api.custom.events.NInventoryCloseEvent;
import fr.newzproject.api.interfaces.Click;
import fr.newzproject.api.inventory.NInventory;
import fr.newzproject.api.items.ClickableItem;
import fr.newzproject.api.utils.InventoryVariables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListeners implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.UNKNOWN)) {
            event.setCancelled(true);
            return;
        }

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            NInventory nInventory = MainApi.getInventory(player);

            if (nInventory != null) {

                NInventoryClickEvent nInventoryClickEvent = new NInventoryClickEvent(player, nInventory, event);
                Bukkit.getPluginManager().callEvent(nInventoryClickEvent);

                if (nInventoryClickEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }

                Inventory inventory = event.getClickedInventory();
                if (inventory == null || event.getSlot() < 0) {
                    return;
                } else if (!nInventory.isClickable() && !inventory.equals(nInventory.getInventory())) {
                    event.setCancelled(true);
                    return;
                }

                ClickableItem clickableItem = nInventory.getItem(event.getSlot());
                if (clickableItem != null) {
                    if (nInventory.isClickable()) {
                        if (!inventory.equals(nInventory.getInventory())) {
                            if (event.isShiftClick()) {
                                return;
                            }
                        } else if (nInventory.isAllowedSlot(event.getSlot())) {
                            if (event.isShiftClick()) {
                                return;
                            }
                        }
                    }
                    event.setCancelled(true);

                    Click click = clickableItem.getClick();
                    if (click != null) {
                        click.click(event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            NInventory NInventory = MainApi.getInventory(player);

            if (NInventory != null) {
                if (NInventory.isCloseable()) {
                    NInventory.Close closeChecker = NInventory.getCloseChecker();
                    if (closeChecker != null) {
                        closeChecker.close(event);
                    }

                    Bukkit.getPluginManager().callEvent(new NInventoryCloseEvent(player, NInventory, event));
                    InventoryVariables.playerInventory.remove(player);

                    if (MainApi.getInstance() != null) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.updateInventory();
                            }
                        }.runTaskLater(MainApi.getInstance(), 1);
                    }
                } else {
                    if (MainApi.getInstance() != null) {
                        new BukkitRunnable() {
                            public void run() {
                                NInventory.open(player);
                            }
                        }.runTaskLater(MainApi.getInstance(), 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();

            NInventory NInventory = MainApi.getInventory(player);
            if (NInventory != null) {
                InventoryVariables.playerInventory.remove(player);
            }
        }
    }
}
