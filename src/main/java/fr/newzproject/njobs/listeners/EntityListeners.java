package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import fr.newzproject.njobs.utils.Reflections;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.IOException;

public class EntityListeners implements Listener {
    private final JobsCore plugin;

    public EntityListeners(JobsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (event.getEntity() != null && event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            Reflections reflections = new Reflections();
            JobsManager jobsManager = new JobsManager(plugin,player);

            JobsEnum jobsEnum = JobsEnum.CHASSEUR;
            Jobs jobs = jobsManager.getJob(jobsEnum);
            new JobsManager(plugin,player).addJobXp(jobsEnum, JobsXPEnum.getEntityWorth(event.getEntityType()));
            reflections.sendActionBar(player, "§a" + jobsEnum.getJobName() + ": " + jobsManager.getJobXp(jobsEnum) + "/" + JobsXPEnum.getXpForLevelup(jobs) + " (+" + JobsXPEnum.getEntityWorth(event.getEntityType()) + ")");
        }
    }
}
