package fr.newzproject.njobs.api;

import fr.newzproject.njobs.jobs.*;

import java.util.UUID;

public class JobsCoreAPI {

    public static JobsManager getManager() {
        return JobsManager.getInstance();
    }

    public static JobsXPManager getXPManager() {
        return JobsXPManager.getInstance();
    }

    public static JobsRewards getRewards() {
        return JobsRewards.getInstance();
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
