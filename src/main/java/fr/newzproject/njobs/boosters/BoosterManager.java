package fr.newzproject.njobs.boosters;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class BoosterManager {

    private static BoosterManager instance;
    private final HashMap<UUID, Booster> uuidBoosters = new HashMap<>();
    private final HashMap<UUID, BukkitTask> boosterTask = new HashMap<>();
    private BukkitTask task;

    public BoosterManager() {
    }

    public static BoosterManager getInstance() {
        return instance == null ? instance = new BoosterManager() : instance;
    }

    public void addUUIDBoosters(UUID uuid, Booster boosters) {
        if (!hasBoosters(uuid)) {
            uuidBoosters.put(uuid, boosters);
        }
    }

    public int getTime(UUID uuid) {
        if (hasBoosters(uuid)) {
            return uuidBoosters.get(uuid).getTime();
        }
        return -1;
    }

    public double getMultiplier(UUID uuid) {
        if (hasBoosters(uuid)) {
            return uuidBoosters.get(uuid).getMultiplier();
        }
        return -1;
    }

    public void setTime(UUID uuid, Booster boosters) {
        uuidBoosters.replace(uuid, boosters);
    }

    public boolean hasBoosters(UUID uuid) {
        return uuidBoosters.containsKey(uuid);
    }

    public void startBoosters(UUID uuid, Booster boosters) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!hasBoosters(uuid) && !isAlreadyStart(uuid)) {
                    addUUIDBoosters(uuid, boosters);
                    boosterTask.put(uuid, task);
                    Bukkit.getPlayer(uuid).sendMessage("§cTu vien de commencer ton booster !");
                    ItemStack itemStack = Bukkit.getPlayer(uuid).getInventory().getItemInMainHand();
                    Bukkit.getPlayer(uuid).getInventory().setItemInMainHand(new ItemBuilder(itemStack).setAmount(itemStack.getAmount() - 1).toItemStack());
                }
                int time = getTime(uuid);

                time--;
                Booster booster = new Booster();
                booster.setMultiplier(booster.getMultiplier());
                booster.setTime(time);
                setTime(uuid, booster);

                if (time == 0) {
                    stopBoosters(uuid);
                }
            }
        }.runTaskTimerAsynchronously(JobsCore.getInstance(), 0L, 20L);
    }

    public boolean isAlreadyStart(UUID uuid) {
        return boosterTask.containsKey(uuid) && !boosterTask.get(uuid).isCancelled();
    }

    public void stopBoosters(UUID uuid) {
        if (hasBoosters(uuid) && isAlreadyStart(uuid)) {
            if (getTime(uuid) > 0) {
                Booster booster = new Booster();
                booster.setMultiplier(getMultiplier(uuid));
                booster.setTime(getTime(uuid));
                Bukkit.getPlayer(uuid).getInventory().addItem(new BoosterItem().getItem(booster));
            }
            boosterTask.get(uuid).cancel();
        } else if (!hasBoosters(uuid) && isAlreadyStart(uuid)) {
            boosterTask.get(uuid).cancel();
        } else if (hasBoosters(uuid) && !isAlreadyStart(uuid)) {
            if (getTime(uuid) > 0) {
                Booster booster = new Booster();
                booster.setMultiplier(getMultiplier(uuid));
                booster.setTime(getTime(uuid));
                Bukkit.getPlayer(uuid).getInventory().addItem(new BoosterItem().getItem(booster));
            }
        }
        boosterTask.remove(uuid);
        uuidBoosters.remove(uuid);
    }
}