package fr.newzproject.njobs.storage;

import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.storage.json.JSONReader;
import fr.newzproject.njobs.storage.json.JSONWriter;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.List;

public class JsonStorage {

    private JSONReader jsonReader;
    private JSONWriter jsonWriter;

    public JsonStorage() {
        this.jsonReader = new JSONReader();
        this.jsonWriter = new JSONWriter();
    }

    public Jobs getJobs(Player player, JobsEnum jobsEnum){
        final JSONArray array = jsonReader.readFile(new File("plugins//nJobs//jobData//" + player.getName() + ".json"));
        if(array == null){
            return new Jobs(player,jobsEnum,0,0);
        }
        final JSONObject jobjPlayer = (JSONObject) array.get(0);
        final JSONObject jobjJob = (JSONObject) jobjPlayer.get("jobs");
        if(jobjJob == null || jobjPlayer.get(player) == null || jobjJob.get("["+jobsEnum.getJob()+".lvl]") == null){
            return new Jobs(player,jobsEnum,0,0);
        }
        Jobs jobs = new Jobs(player,jobsEnum);

        jobs.setCurrentLvl(Integer.parseInt(String.valueOf(jobjJob.get("["+jobsEnum.getJob()+".lvl]"))));
        jobs.setXp(Integer.parseInt(String.valueOf(jobjJob.get("["+jobsEnum.getJob()+".xp]"))));

        return jobs;
    }

    public void saveJobs(Player player, List<Jobs> jobsList){
        for(Jobs jobs : jobsList) {
            final JSONObject objPlayer = new JSONObject();
            final JSONObject objStats = new JSONObject();
            objStats.put("[" + jobs.getJobs().getJob() + ".lvl]", jobs.getCurrentLvl());
            objStats.put("[" + jobs.getJobs().getJob() + ".xp]", jobs.getXp());

            objPlayer.put("jobs", objStats);
            this.jsonWriter.addObject(objPlayer);
            new File("plugins//nJobs//jobData//").mkdirs();
            this.jsonWriter.writeFile(new File("plugins//nJobs//jobData//" + player.getPlayer().getName() + ".json"));
        }
    }
}
