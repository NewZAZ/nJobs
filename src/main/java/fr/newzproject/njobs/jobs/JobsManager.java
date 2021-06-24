package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import jdk.nashorn.internal.scripts.JO;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobsManager {
    private final JobsCore plugin;
    private final Player player;
    private final HashMap<Player, List<Jobs>> playerJobs;

    public JobsManager(JobsCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        playerJobs = plugin.getPlayerJobs();
    }

    public void init(){
        List<Jobs> jobs = new ArrayList<>();

        jobs.add(new Jobs(player,JobsEnum.AGRICULTEUR));
        jobs.add(new Jobs(player,JobsEnum.CHASSEUR));
        jobs.add(new Jobs(player,JobsEnum.MINEUR));
        plugin.getPlayerJobs().put(player, jobs);
    }

    public Jobs getJob(JobsEnum jobsEnum){
        if(playerJobs.containsKey(player)){
            for(Map.Entry<Player, List<Jobs>> entry : playerJobs.entrySet()){
                for (Jobs job : entry.getValue()) {
                    if(job.getJobs() == jobsEnum){
                        return job;
                    }
                }
            }
        }
        return null;
    }

    public int getJobXp(JobsEnum jobs){
        if(getJob(jobs) != null){
            return getJob(jobs).getXp();
        }
        return -1;
    }

    public int indexOfJob(JobsEnum jobsEnum){
        if(playerJobs.containsKey(player)){
            for(Map.Entry<Player, List<Jobs>> entry : playerJobs.entrySet()){
                for (Jobs job : entry.getValue()) {
                    if(job.getJobs() == jobsEnum){
                        return entry.getValue().indexOf(job);
                    }
                }
            }
        }
        return -1;
    }

    public void addJobXp(JobsEnum jobs, int xp) {
        if (getJob(jobs) != null) {
            Jobs job = getJob(jobs);
            job.setXp(job.getXp() + xp);
            playerJobs.get(player).set(indexOfJob(jobs), job);
            if (canLevelup(jobs)) {
                levelupJobs(jobs);
            }
        }
    }

    public void levelupJobs(JobsEnum jobs){
        if (getJob(jobs) != null) {
            Jobs job = getJob(jobs);
            job.setXp(job.getXp() - JobsXPEnum.getXpForLevelup(jobs));
            job.setCurrentLvl(job.getCurrentLvl() +1);
            playerJobs.get(player).set(indexOfJob(jobs), job);
        }
    }

    public boolean canLevelup(JobsEnum jobs){
        return getJob(jobs) != null && getJob(jobs).getXp() >= JobsXPEnum.getXpForLevelup(jobs);
    }

    public void save(){
        if(playerJobs.containsKey(player)){
            for(Map.Entry<Player, List<Jobs>> entry : playerJobs.entrySet()){
                for (Jobs job : entry.getValue()) {
                    job.getJsonStorage().save(job);
                }
            }
        }
    }
}