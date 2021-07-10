package fr.newzproject.njobs.jobs;

import fr.newzproject.njobs.interfaces.IJob;

public class Job implements IJob {
    private final String name;
    private final JobType type;
    private double xp;
    private int level;
    private final int maxLevel;

    public Job(String name, JobType type, double xp, int level, int maxLevel) {
        this.name = name;
        this.type = type;
        this.xp = xp;
        this.level = level;
        this.maxLevel = maxLevel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JobType getType() {
        return type;
    }

    @Override
    public double getXp() {
        return xp;
    }

    @Override
    public void setXp(double xp) {
        this.xp = xp;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
}
