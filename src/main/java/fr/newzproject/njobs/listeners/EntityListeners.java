package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.boosters.BoosterManager;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.managers.Manager;
import fr.newzproject.njobs.managers.WorthManager;
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
            Manager manager = Manager.getInstance();
            WorthManager experienceManager = WorthManager.getInstance();
            Job job = manager.getJob(player.getUniqueId(), JobType.CHASSEUR);
            manager.addJobXp(player.getUniqueId(), JobType.CHASSEUR, experienceManager.getEntityTypeWorth(event.getEntityType(), JobType.CHASSEUR) * BoosterManager.getInstance().getMultiplier(player.getUniqueId()));

            CompatibleMessage.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', plugin.actionBarMessage.replaceAll("%job_name%", JobType.CHASSEUR.getName()).replaceAll("%job_xp%", String.valueOf(manager.getJobXp(player.getUniqueId(), JobType.CHASSEUR))).replaceAll("%cost_for_levelup%", String.valueOf(experienceManager.getXpForLevelup(JobType.CHASSEUR, job))).replaceAll("%xp%", String.valueOf(experienceManager.getEntityTypeWorth(event.getEntityType(), JobType.CHASSEUR)))));
        }
    }
}
