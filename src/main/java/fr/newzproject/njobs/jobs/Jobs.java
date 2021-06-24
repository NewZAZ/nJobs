package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.JobsCore;
import org.bukkit.ChatColor;

public enum Jobs {
    AGRICULTEUR("Agriculteur", getJobName("Agriculteur"),getMaxLvlJob("Agriculteur")),
    MINEUR("Mineur", getJobName("Mineur"), getMaxLvlJob("Mineur")),
    CHASSEUR("Chasseur", getJobName("Chasseur"), getMaxLvlJob("Chasseur"));

    final String job;
    final String jobName;
    final int maxLvl;

    Jobs(String job, String jobName, int maxLvl) {
        this.job = job;
        this.jobName = jobName;
        this.maxLvl = maxLvl;
    }

    private static String getJobName(String job){
        return ChatColor.translateAlternateColorCodes('&', JobsCore.getInstance().getConfig().getString(job+".name"));
    }

    private static int getMaxLvlJob(String job){
        return JobsCore.getInstance().getConfig().getInt(job+".maxLvl");
    }

    public String getJob() {
        return job;
    }

    public String getJobName() {
        return jobName;
    }

    public int getMaxLvl() {
        return maxLvl;
    }
}
