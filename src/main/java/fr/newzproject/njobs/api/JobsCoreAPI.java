package fr.newzproject.njobs.api;

import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.managers.Manager;
import fr.newzproject.njobs.managers.RewardsManager;
import fr.newzproject.njobs.managers.WorthManager;

import java.util.UUID;

public class JobsCoreAPI {

    public static Manager getManager() {
        return Manager.getInstance();
    }

    public static WorthManager getWorthManager() {
        return WorthManager.getInstance();
    }

    public static RewardsManager getRewards() {
        return RewardsManager.getInstance();
    }

    public static Job get(UUID uuid, JobType type) {
        return getManager().getJob(uuid, type);
    }

    public static double getXP(UUID uuid, JobType type) {
        return getManager().getJobXp(uuid, type);
    }

    public static String getCommandRewards(JobType type, int level) {
        return getRewards().getRewards(type, level);
    }

}
