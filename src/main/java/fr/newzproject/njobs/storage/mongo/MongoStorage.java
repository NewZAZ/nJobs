package fr.newzproject.njobs.storage.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import fr.newzproject.njobs.entity.JPlayer;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.storage.mongo.services.JobsMongoService;

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

        player.getJobs().add(set("Mineur", JobType.MINEUR, 0, 0, 10));
        player.getJobs().add(set("Chasseur", JobType.CHASSEUR, 0, 0, 10));
        player.getJobs().add(set("Agriculteur", JobType.AGRICULTEUR, 0, 0, 10));
        return jobsOptional.orElse(player);
    }

    public void save(JPlayer jPlayer) {
        try {
            jobsMongoService.set(jPlayer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Job set(String name, JobType jobType, double xp, int level, int maxLevel) {
        Job job = new Job(name, jobType, xp, level, maxLevel);
        return job;
    }
}
