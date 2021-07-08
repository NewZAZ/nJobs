package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.utils.compatibility.CompatibleMaterial;
import org.bukkit.entity.EntityType;

import java.util.*;

public class JobsXPManager {
    private static JobsXPManager instance;

    private final Map<CompatibleMaterial,Map<JobType,Double>> materialWorth = new HashMap<>();
    private final Map<EntityType, Map<JobType,Double>> entityWorth = new HashMap<>();

    private JobsXPManager() {
        instance = this;
    }


    public void addMaterialWorth(CompatibleMaterial compatibleMaterial, JobType type, double xp){
        materialWorth.computeIfAbsent(compatibleMaterial,material -> new HashMap<>()).put(type,xp);
    }


    public void addEntityTypeWorth(EntityType entityType, JobType type, double xp){
        entityWorth.computeIfAbsent(entityType, entity -> new HashMap<>()).put(type,xp);
    }

    public Collection<JobType> getJobType(CompatibleMaterial compatibleMaterial){
        return materialWorth.computeIfAbsent(compatibleMaterial, material -> new HashMap<>()).keySet();
    }

    public double getMaterialWorth(CompatibleMaterial compatibleMaterial, JobType jobType){
        return materialWorth.get(compatibleMaterial).get(jobType);
    }

    public double getEntityTypeWorth(EntityType entityType, JobType jobType){
        return entityWorth.get(entityType).get(jobType);
    }

    public double getXpForLevelup(JobType jobType, Job job){
        return (JobsCore.getInstance().priceBase.get(jobType) * Math.pow(JobsCore.getInstance().priceMulti.get(jobType), job.getCurrentLvl()));
    }

    public static JobsXPManager getInstance() {
        return instance == null ? instance = new JobsXPManager() : instance;
    }
}
