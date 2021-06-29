package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.custom.JobLevelupEvent;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import fr.newzproject.njobs.storage.json.JsonStorage;
import org.bukkit.Bukkit;
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
        JsonStorage storage = new JsonStorage();
        jobs.add(storage.getJobs(player,JobsEnum.AGRICULTEUR));
        jobs.add(storage.getJobs(player,JobsEnum.CHASSEUR));
        jobs.add(storage.getJobs(player,JobsEnum.MINEUR));
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

    public int getJobXp(JobsEnum jobsEnum){
        if(getJob(jobsEnum) != null){
            return getJob(jobsEnum).getXp();
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

    public void addJobXp(JobsEnum jobsEnum, int xp) {
        if (getJob(jobsEnum) != null) {
            Jobs jobs = getJob(jobsEnum);
            jobs.setXp(jobs.getXp() + xp);
            playerJobs.get(player).set(indexOfJob(jobsEnum), jobs);
            if (canLevelup(jobsEnum)) {
                levelupJobs(jobsEnum);
            }
        }
    }

    public void levelupJobs(JobsEnum jobsEnum){
        if (getJob(jobsEnum) != null) {
            Jobs jobs = getJob(jobsEnum);
            jobs.setXp(jobs.getXp() - JobsXPEnum.getXpForLevelup(jobs));
            jobs.setCurrentLvl(jobs.getCurrentLvl() +1);
            playerJobs.get(player).set(indexOfJob(jobsEnum), jobs);
            Bukkit.getPluginManager().callEvent(new JobLevelupEvent(player,jobsEnum,plugin.getJobsRewards().getRewards(jobsEnum,jobs.getCurrentLvl()),jobs.getCurrentLvl(),jobs.getCurrentLvl() +1));
        }
    }

    public boolean canLevelup(JobsEnum jobsEnum){
        return getJob(jobsEnum) != null && getJob(jobsEnum).getXp() >= JobsXPEnum.getXpForLevelup(getJob(jobsEnum));
    }

    public void save(){
        new JsonStorage().saveJobs(player, playerJobs.get(player));

    }
}
