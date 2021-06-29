package fr.newzproject.njobs.custom;

import fr.newzproject.njobs.jobs.enums.JobsEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class JobLevelupEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final JobsEnum jobsEnum;
    private final String rewards;
    private final int lastLevel;
    private final int newLevel;

    public JobLevelupEvent(Player player, JobsEnum jobsEnum, String rewards, int lastLevel, int newLevel) {
        this.player = player;
        this.jobsEnum = jobsEnum;
        this.rewards = rewards;
        this.lastLevel = lastLevel;
        this.newLevel = newLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public JobsEnum getJobsEnum() {
        return jobsEnum;
    }

    public String getRewards() {
        return rewards;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
