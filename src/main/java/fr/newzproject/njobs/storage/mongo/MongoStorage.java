package fr.newzproject.njobs.storage.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import fr.newzproject.njobs.entity.JPlayer;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.storage.mongo.services.JobsMongoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MongoStorage {

    JobsMongoService jobsMongoService = new JobsMongoService();

    public JPlayer load(UUID uuid) {
        BasicDBObject dbObject = new BasicDBObject("uuid", uuid);
        try {
            System.out.println(jobsMongoService.get());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Optional<JPlayer> jobsOptional = jobsMongoService.get(dbObject);
        JPlayer player = new JPlayer(uuid);

        player.getJobs().add(set("Mineur",0,0));
        player.getJobs().add(set("Chasseur",0,0));
        player.getJobs().add(set("Agriculteur",0,0));
        return jobsOptional.orElse(player);
    }

    public void save(JPlayer jPlayer) {
        try {
            jobsMongoService.set(jPlayer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Job set(String name, int level, double xp){
        Job job = new Job();
        job.setJobName(name);
        job.setXp(xp);
        job.setCurrentLvl(level);
        return job;
    }
}
