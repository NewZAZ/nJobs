package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.JobsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {
    private final JobsCore plugin;

    public PlayerListeners(JobsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        new JobsManager(plugin,event.getPlayer()).init();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        new JobsManager(plugin,event.getPlayer()).save();
    }
}
