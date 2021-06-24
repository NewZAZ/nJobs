package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.storage.JsonStorage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Jobs {

    private final Player player;
    private final JobsEnum jobsEnum;
    private int xp;
    private int currentLvl;

    public Jobs(Player player, JobsEnum jobsEnum) {
        this.player = player;
        this.jobsEnum = jobsEnum;
        this.xp = new JsonStorage().getJobs(player,jobsEnum).getXp();
        this.currentLvl = new JsonStorage().getJobs(player,jobsEnum).getCurrentLvl();
    }

    public Jobs(Player player, JobsEnum jobsEnum, int xp, int currentLvl) {
        this.player = player;
        this.jobsEnum = jobsEnum;
        this.xp = xp;
        this.currentLvl = currentLvl;
    }

    public void setCurrentLvl(int lvl){
        this.currentLvl = lvl;
    }

    public void setXp(int xp){
        this.xp = xp;
    }

    public int getXp(){
        return xp;
    }

    public Player getPlayer() {
        return player;
    }


    public JobsEnum getJobs() {
        return jobsEnum;
    }

    public int getCurrentLvl() {
        return currentLvl;
    }
}
