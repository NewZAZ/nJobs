package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.boosters.BoosterManager;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.JobsXPManager;
import fr.newzproject.njobs.utils.compatibility.CompatibleMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListeners implements Listener {
    private final JobsCore plugin;

    public EntityListeners(JobsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (event.getEntity() != null && event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            JobsManager jobsManager = JobsManager.getInstance();
            JobsXPManager experienceManager = JobsXPManager.getInstance();
            Job job = jobsManager.getJob(player.getUniqueId(), JobType.CHASSEUR);
            jobsManager.addJobXp(player.getUniqueId(), JobType.CHASSEUR, experienceManager.getEntityTypeWorth(event.getEntityType(),JobType.CHASSEUR) * BoosterManager.getInstance().getMultiplier(player.getUniqueId()));

            CompatibleMessage.sendActionBar(player, ChatColor.translateAlternateColorCodes('&',plugin.actionBarMessage.replaceAll("%job_name%", JobType.CHASSEUR.getName()).replaceAll("%job_xp%",String.valueOf(jobsManager.getJobXp(player.getUniqueId(), JobType.CHASSEUR))).replaceAll("%cost_for_levelup%",String.valueOf(experienceManager.getXpForLevelup(JobType.CHASSEUR, job))).replaceAll("%xp%",String.valueOf(experienceManager.getEntityTypeWorth(event.getEntityType(),JobType.CHASSEUR)))));
        }
    }
}
