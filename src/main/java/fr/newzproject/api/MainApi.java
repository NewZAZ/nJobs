package fr.newzproject.api;

import fr.newzproject.api.inventory.NInventory;
import fr.newzproject.api.listeners.InventoryListeners;
import fr.newzproject.api.utils.InventoryVariables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Map;

public class MainApi {
    private static Plugin instance;

    public MainApi() {
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static void setup(Plugin plugin) {
        if (instance != null) {
            return;
        }
        instance = plugin;

        Listener disableListener = new Listener() {
            @EventHandler
            public void onDisable(PluginDisableEvent event) {
                if (event.getPlugin() == null || plugin == null) {
                    return;
                }
                if (event.getPlugin().equals(plugin)) {
                    if (InventoryVariables.playerInventory == null || InventoryVariables.playerInventory.isEmpty()) {
                        return;
                    }
                    for (Map.Entry<Player, NInventory> entry : InventoryVariables.playerInventory.entrySet()) {
                        Player player = entry.getKey();
                        NInventory hInventory = entry.getValue();
                        if (hInventory != null && hasInventory(player)) {
                            hInventory.close(player);
                        }
                    }
                }
            }
        };

        plugin.getConfig();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(), plugin);
        pm.registerEvents(disableListener, plugin);
    }

    public static NInventory getInventory(Player player) {
        return InventoryVariables.playerInventory.get(player);
    }

    public static boolean hasInventory(Player player) {
        return InventoryVariables.playerInventory.containsKey(player);
    }

    public static String getId(Player player) {
        NInventory nInventory = InventoryVariables.playerInventory.get(player);
        return nInventory != null ? nInventory.getId() : "__there_is_no_inventory__";
    }

    public static InventoryManager getInventoryManager() {
        return new InventoryManager();
    }

    public static class InventoryManager {

        private String title = "";
        private InventoryType inventoryType = InventoryType.CHEST;
        private int size = 6;
        private boolean closeable = true;
        private String id = "";
        private boolean clickable = false;

        public InventoryManager() {
        }

        public InventoryManager setTitle(String title) {
            this.title = title;
            return this;
        }

        public InventoryManager setSize(int size) {
            this.size = size;
            return this;
        }

        public InventoryManager setId(String id) {
            this.id = id;
            return this;
        }

        public InventoryManager setCloseable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        public InventoryManager setInventoryType(InventoryType inventoryType) {
            this.inventoryType = inventoryType;
            return this;
        }

        public InventoryManager setClickable(boolean clickable) {
            this.clickable = clickable;
            return this;
        }

        public NInventory create() {
            return new NInventory(ChatColor.translateAlternateColorCodes('&', this.title), this.inventoryType, this.size, this.id, this.closeable, this.clickable);
        }
    }
}
