package fr.newzproject.njobs.storage.json;

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

    private final JSONReader jsonReader;
    private final JSONWriter jsonWriter;

    public JsonStorage() {
        this.jsonReader = new JSONReader();
        this.jsonWriter = new JSONWriter();
    }

    public Jobs getJobs(Player player, JobsEnum jobsEnum){
        if(!new File("plugins//nJobs//jobData//" + player.getUniqueId().toString() + ".json").exists()){
            return new Jobs(player,jobsEnum,0,0);
        }
        final JSONArray jobArray = jsonReader.readFile(new File("plugins//nJobs//jobData//" + player.getUniqueId().toString() + ".json"));
        if(jobArray == null){
            return new Jobs(player,jobsEnum,0,0);
        }
        final JSONObject objPlayer = (JSONObject) jobArray.get(0);
        final JSONObject objJob = (JSONObject) objPlayer.get("jobs");
        if(objJob == null || objJob.get("["+jobsEnum.getJob()+".lvl]") == null){
            return new Jobs(player,jobsEnum,0,0);
        }
        return new Jobs(player,jobsEnum,Integer.parseInt(String.valueOf(objJob.get("["+jobsEnum.getJob()+".xp]"))),Integer.parseInt(String.valueOf(objJob.get("["+jobsEnum.getJob()+".lvl]"))));
    }

    public void saveJobs(Player player, List<Jobs> jobsList){
        final JSONObject objPlayer = new JSONObject();
        final JSONObject objJob = new JSONObject();
        for(Jobs jobs : jobsList) {
            objJob.put("[" + jobs.getJobs().getJob() + ".lvl]", jobs.getCurrentLvl());
            objJob.put("[" + jobs.getJobs().getJob() + ".xp]", jobs.getXp());
        }
        objPlayer.put("jobs", objJob);
        this.jsonWriter.addObject(objPlayer);
        new File("plugins//nJobs//jobData//").mkdirs();
        this.jsonWriter.writeFile(new File("plugins//nJobs//jobData//" + player.getUniqueId().toString() + ".json"));
    }
}
