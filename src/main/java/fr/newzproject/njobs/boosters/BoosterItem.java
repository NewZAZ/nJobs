package fr.newzproject.njobs.boosters;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.newzproject.njobs.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BoosterItem {

    public boolean isBoosterItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);

        return nbtItem.hasKey("njobs_booster_item");
    }

    public double getMultiplier(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);

        if (!nbtItem.hasKey("njobs_booster_item")) return -1;

        return nbtItem.getDouble("multiplier");
    }

    public int getTime(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);

        if (!nbtItem.hasKey("njobs_booster_item")) return -1;

        return nbtItem.getInteger("time");
    }

    public ItemStack getItem(Booster booster) {
        NBTItem nbtItem = new NBTItem(new ItemBuilder(Material.PAPER).setDisplayName("§cMultiplier").setLore(Arrays.asList("Multiplier : " + booster.getMultiplier(), "Temps : " + booster.getTime())).toItemStack());

        nbtItem.setBoolean("njobs_booster_item", true);
        nbtItem.setDouble("multiplier", booster.getMultiplier());
        nbtItem.setInteger("time", booster.getTime());

        return nbtItem.getItem();
    }

    public void onBoosterInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        BoosterManager boosterManager = BoosterManager.getInstance();

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!isBoosterItem(itemStack)) return;

        if (boosterManager.isAlreadyStart(player.getUniqueId()) || boosterManager.hasBoosters(player.getUniqueId())) {
            player.sendMessage("§cErreur : vous ne pouvez pas utiliser de booster maintenant");
            return;
        }
        Booster booster = new Booster();
        booster.setMultiplier(getMultiplier(itemStack));
        booster.setTime(getTime(itemStack));
        boosterManager.startBoosters(player.getUniqueId(), booster);
    }

}
