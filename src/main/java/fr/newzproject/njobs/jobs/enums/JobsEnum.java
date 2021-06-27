package fr.newzproject.njobs.jobs.enums;

import fr.newzproject.njobs.JobsCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public enum JobsEnum {
    AGRICULTEUR("Agriculteur", getJobName("Agriculteur"),0,getMaxLvlJob("Agriculteur")),
    MINEUR("Mineur", getJobName("Mineur"),0, getMaxLvlJob("Mineur")),
    CHASSEUR("Chasseur", getJobName("Chasseur"),0, getMaxLvlJob("Chasseur"));

    final String job;
    final String jobName;
    int currentLvl;
    final int maxLvl;

    JobsEnum(String job, String jobName, int currentLvl, int maxLvl) {
        this.job = job;
        this.jobName = jobName;
        this.currentLvl = currentLvl;
        this.maxLvl = maxLvl;

    }

    private static String getJobName(String job){
        return ChatColor.translateAlternateColorCodes('&', JobsCore.getInstance().getConfig().getString(job+".name"));
    }

    private static int getMaxLvlJob(String job){
        return JobsCore.getInstance().getConfig().getInt(job+".maxLvl");
    }

    public void setCurrentLvl(int currentLvl) {
        this.currentLvl = currentLvl;
    }

    public String getJob() {
        return job;
    }

    public String getJobName() {
        return jobName;
    }

    public int getCurrentLvl() {
        return currentLvl;
    }

    public int getMaxLvl() {
        return maxLvl;
    }
}
