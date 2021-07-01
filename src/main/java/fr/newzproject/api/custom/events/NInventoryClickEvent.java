package fr.newzproject.api.custom.events;

import fr.newzproject.api.inventory.NInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class NInventoryClickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final NInventory nInventory;
    private final InventoryClickEvent event;
    private boolean cancelled = false;

    public NInventoryClickEvent(Player player, NInventory nInventory, InventoryClickEvent event) {
        this.player = player;
        this.nInventory = nInventory;
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public NInventory getInventory() {
        return nInventory;
    }

    public InventoryClickEvent getClickEvent() {
        return event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
