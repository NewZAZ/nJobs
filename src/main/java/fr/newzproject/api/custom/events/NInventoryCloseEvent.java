package fr.newzproject.api.custom.events;

import fr.newzproject.api.inventory.NInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class NInventoryCloseEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final NInventory nInventory;
    private final InventoryCloseEvent event;

    public NInventoryCloseEvent(Player player, NInventory nInventory, InventoryCloseEvent event) {
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

    public InventoryCloseEvent getCloseEvent() {
        return event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
