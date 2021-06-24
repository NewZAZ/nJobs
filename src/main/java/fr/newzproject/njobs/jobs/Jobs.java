package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.storage.JsonStorage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Jobs {
    private final File file;
    private final JsonStorage jsonStorage;

    private final Player player;
    private final JobsEnum jobsEnum;
    private final Jobs jobs;
    private int xp;
    private int currentLvl;

    public Jobs(Player player, JobsEnum jobsEnum) {
        this.file = new File("plugins/[EndSky] Jobs/dataPlayer/", player.getName() + ".json");
        this.jsonStorage = new JsonStorage(file);
        this.player = player;
        this.jobsEnum = jobsEnum;
        this.jobs = (Jobs) jsonStorage.getObject("jobs").get(jobsEnum.getJob());
        this.xp = jobs.getXp();
        this.currentLvl = jobs.getCurrentLvl();
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

    public boolean hasJobs(){
        return jobs != null;
    }

    public File getFile() {
        return file;
    }

    public JsonStorage getJsonStorage() {
        return jsonStorage;
    }

    public JobsEnum getJobs() {
        return jobsEnum;
    }

    public int getCurrentLvl() {
        return currentLvl;
    }
}
