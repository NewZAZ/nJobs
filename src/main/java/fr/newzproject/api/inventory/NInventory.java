package fr.newzproject.api.inventory;

import fr.newzproject.api.MainApi;
import fr.newzproject.api.custom.events.NInventoryOpenEvent;
import fr.newzproject.api.interfaces.Update;
import fr.newzproject.api.items.ClickableItem;
import fr.newzproject.api.utils.InventoryVariables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class NInventory implements InventoryHolder {

    private final Inventory bukkitInventory;
    private final String title;
    private final InventoryType inventoryType;
    private final HashMap<Integer, ClickableItem> clickableItems = new HashMap<>();
    private final ArrayList<Integer> clickableSlots = new ArrayList<>();
    private Close closeChecker;
    private String id;
    private boolean closeable;
    private boolean clickable;

    public NInventory(String title, InventoryType inventoryType, int size, String id, boolean closeable, boolean clickable) {
        this.id = id;
        this.closeable = closeable;
        this.clickable = clickable;
        this.title = title;
        this.inventoryType = inventoryType;
        if (inventoryType.equals(InventoryType.CHEST)) {
            this.bukkitInventory = Bukkit.createInventory(this, size * 9, title);
        } else {
            this.bukkitInventory = Bukkit.createInventory(this, inventoryType, title);
        }
    }

    public NInventory() {
        this("", InventoryType.CHEST, 1, "default_inventory", true, false);
    }

    public void open(Player player) {
        if (player == null) return;

        NInventory hInventory = this;
        Listener openListener = new Listener() {
            @EventHandler
            public void onInventoryOpen(InventoryOpenEvent event) {
                Bukkit.getPluginManager().callEvent(new NInventoryOpenEvent(player, hInventory, event));
            }
        };
        Bukkit.getPluginManager().registerEvents(openListener, MainApi.getInstance());

        player.openInventory(this.bukkitInventory);
        InventoryVariables.playerInventory.put(player, this);

        InventoryOpenEvent.getHandlerList().unregister(openListener);
    }

    public void close(Player player) {
        if (player == null) return;
        InventoryVariables.playerInventory.remove(player);
        this.closeable = true;
        player.closeInventory();
    }

    public void update(Player player, int runLater, int period, Update update) {
        final BukkitTask[] bukkitTask = {null};
        if (MainApi.getInstance() != null) {
            bukkitTask[0] = new BukkitRunnable() {
                public void run() {
                    if (MainApi.getInventory(player) == null || MainApi.getInstance() == null) {
                        cancel();
                        return;
                    }
                    update.update(bukkitTask[0]);
                }
            }.runTaskTimer(MainApi.getInstance(), runLater, period);
        }
    }

    public void guiFill(ClickableItem clickableItem) {
        for (int slot = 0; slot < this.getSize() * 9; slot++) {
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void guiFill(ItemStack itemStack) {
        guiFill(ClickableItem.empty(itemStack));
    }

    public void guiAir() {
        guiFill(new ItemStack(Material.AIR));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return this.bukkitInventory.getSize() / 9;
    }

    public String getTitle() {
        return this.title;
    }

    public InventoryType getInventoryType() {
        return this.inventoryType;
    }

    public boolean isCloseable() {
        return this.closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public boolean isAllowedSlot(int slot) {
        return this.clickableSlots.contains(slot);
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setAllowedSlot(int slot) {
        this.clickableSlots.add(slot);
    }

    @Override
    public Inventory getInventory() {
        return this.bukkitInventory;
    }

    public void setAirItem(int slot) {
        this.bukkitInventory.setItem(slot, new ItemStack(Material.AIR));
    }

    public void setItem(int slot, ClickableItem clickableItem) {
        if (clickableItem.getItem() == null || clickableItem.getItem().getType().equals(Material.AIR)) {
            this.clickableItems.put(slot, ClickableItem.empty(new ItemStack(Material.AIR)));
            this.bukkitInventory.setItem(slot, clickableItem.getItem());
            return;
        }
        this.clickableItems.put(slot, clickableItem);
        this.bukkitInventory.setItem(slot, clickableItem.getItem());
    }


    public Close getCloseChecker() {
        return this.closeChecker;
    }

    public ClickableItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public NInventory whenClosed(Close close) {
        this.closeChecker = close;
        return this;
    }

    public NInventory clone() {
        return new NInventory(getTitle(), getInventoryType(), getSize(), getId() + "_clone", isCloseable(), isClickable());
    }

    public Pagination getPagination() {
        return new Pagination(this);
    }

    public interface Close {
        void close(InventoryCloseEvent event);
    }
}
