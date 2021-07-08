package fr.newzproject.njobs.events;

import fr.newzproject.njobs.jobs.JobType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class JobLevelupEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final UUID uuid;
    private final JobType type;
    private final String rewards;
    private final int lastLevel;
    private final int newLevel;

    public JobLevelupEvent(UUID uuid, JobType type, String rewards, int lastLevel, int newLevel) {
        this.uuid = uuid;
        this.type = type;
        this.rewards = rewards;
        this.lastLevel = lastLevel;
        this.newLevel = newLevel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public JobType getType() {
        return type;
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
