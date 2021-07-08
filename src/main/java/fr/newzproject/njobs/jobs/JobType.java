package fr.newzproject.njobs.jobs;

public enum JobType {
    AGRICULTEUR("Agriculteur"),
    MINEUR("Mineur"),
    CHASSEUR("Chasseur");

    String job;

    JobType(String job) {
        this.job = job;
    }

    public String getName() {
        return job;
    }
}
