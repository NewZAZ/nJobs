package fr.newzproject.njobs.jobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum JobsXP {
    STONE(Jobs.MINEUR, Material.STONE,1.0),
    COBBLESTONE(Jobs.MINEUR, Material.COBBLESTONE,1.2)
    ;

    final Jobs jobs;
    Material material;
    byte data;

    EntityType entityType;
    final double xp;

    JobsXP(Jobs jobs, Material material, double xp) {
        this.jobs = jobs;
        this.material = material;
        this.xp = xp;
    }

    JobsXP(Jobs jobs, Material material, byte data, double xp) {
        this.jobs = jobs;
        this.material = material;
        this.data = data;
        this.xp = xp;
    }

    JobsXP(Jobs jobs, EntityType entityType, double xp) {
        this.jobs = jobs;
        this.entityType = entityType;
        this.xp = xp;
    }


    public Jobs getJobs() {
        return jobs;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public double getXp() {
        return xp;
    }
}
