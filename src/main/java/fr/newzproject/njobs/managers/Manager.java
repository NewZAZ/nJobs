package fr.newzproject.njobs.managers;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.entity.JPlayer;
import fr.newzproject.njobs.events.JobLevelupEvent;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.storage.mongo.MongoStorage;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Manager {
    private static Manager instance;
    private final HashMap<UUID, JPlayer> playerJobs = new HashMap<>();

    public Manager() {
        instance = this;
    }

    /**
     * Load a player on MySQL DB in order to put it in the cache.
     *
     * @param uuid user's {@link UUID}.
     */

    public void init(UUID uuid) {
        playerJobs.remove(uuid);
        MongoStorage mongoStorage = new MongoStorage();

        playerJobs.put(uuid, mongoStorage.load(uuid));
        Bukkit.getScheduler().runTaskTimer(JobsCore.getInstance(), () -> save(uuid), 20L * 60L * 5L, 20L * 60L * 5L);
    }

    /**
     * @param uuid user's {@link UUID}.
     * @return a player's job.
     */

    public Job getJob(UUID uuid, JobType type) {
        if (playerJobs.containsKey(uuid)) {
            for (Map.Entry<UUID, JPlayer> entry : playerJobs.entrySet()) {
                for (Job job : entry.getValue().getJobs()) {
                    if (job.getName().equalsIgnoreCase(type.getName())) {
                        return job;
                    }
                }
            }
        }
        Job job = new Job(type.getName(), type, 0, 0, 0);
        return job;
    }

    /**
     * @param uuid user's {@link UUID}.
     * @return a player's job xp.
     */

    public double getJobXp(UUID uuid, JobType type) {
        if (getJob(uuid, type) != null) {
            return getJob(uuid, type).getXp();
        }
        return -1;
    }

    /**
     * Add xp on player's job.
     *
     * @param uuid user's {@link UUID}.
     * @param xp   {@link Integer}
     */

    public void addJobXp(UUID uuid, JobType type, double xp) {
        if (getJob(uuid, type) != null) {
            Job job = getJob(uuid, type);
            job.setXp(job.getXp() + xp);
            playerJobs.get(uuid).getJobs().add(job);
            if (canLevelup(uuid, type)) {
                levelupJobs(uuid, type);
            }
        }
    }

    /**
     * Levelup player's job
     *
     * @param uuid user's {@link UUID}.
     * @param type
     */

    public void levelupJobs(UUID uuid, JobType type) {
        if (getJob(uuid, type) != null) {
            Job job = getJob(uuid, type);
            job.setXp(job.getXp() - WorthManager.getInstance().getXpForLevelup(type, job));
            job.setLevel(job.getLevel() + 1);
            playerJobs.get(uuid).getJobs().add(job);
            Bukkit.getPluginManager().callEvent(new JobLevelupEvent(uuid, type, RewardsManager.getInstance().getRewards(type, job.getLevel()), job.getLevel(), job.getLevel() + 1));
        }
    }

    /**
     * @param uuid user's {@link UUID}.
     * @return If player can levelup her job
     */

    public boolean canLevelup(UUID uuid, JobType type) {
        return getJob(uuid, type) != null && getJob(uuid, type).getXp() >= WorthManager.getInstance().getXpForLevelup(type, getJob(uuid, type));
    }

    /**
     * Save player's job
     *
     * @param uuid user's {@link UUID}.
     */

    public void save(UUID uuid) {
        new MongoStorage().save(playerJobs.get(uuid));
        playerJobs.remove(uuid);
    }


    public static Manager getInstance() {
        return instance == null ? instance = new Manager() : instance;
    }
}
