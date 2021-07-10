package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.events.JobLevelupEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobListeners implements Listener {
    private final JobsCore plugin;

    public JobListeners(JobsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJobLevelup(JobLevelupEvent event) {
        Player player = Bukkit.getPlayer(event.getUuid());

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.getRewards().replace("%player%", player.getName()));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.jobLevelup").replaceAll("%player%", player.getName()).replaceAll("%job_name%", event.getType().getName())));
    }
}
