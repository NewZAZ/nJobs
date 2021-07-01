package fr.newzproject.api.utils;

import fr.newzproject.api.inventory.NInventory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class InventoryVariables {

    public static Map<Player, NInventory> playerInventory = new HashMap<>();
}
