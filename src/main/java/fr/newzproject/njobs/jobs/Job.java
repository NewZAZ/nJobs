package fr.newzproject.njobs.jobs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class Job {

    @Getter
    @Setter
    private String jobName;

    @Getter
    @Setter
    private double xp;

    @Getter
    @Setter
    private int currentLvl;

    public Job() {
    }

}
