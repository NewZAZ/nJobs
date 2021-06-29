package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.jobs.enums.JobsEnum;
import org.bukkit.entity.Player;

public class Jobs {

    private final Player player;
    private final JobsEnum jobsEnum;
    private int xp;
    private int currentLvl;


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

    public int getMaxLvl(){
        return jobsEnum.getMaxLvl();
    }

    public int getCurrentLvl() {
        return currentLvl;
    }
}
