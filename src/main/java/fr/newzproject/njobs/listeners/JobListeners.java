package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.custom.JobLevelupEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobListeners implements Listener {

    @EventHandler
    public void onJobLevelup(JobLevelupEvent event){
        Player player = event.getPlayer();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),event.getRewards());
        player.sendMessage("GG");
    }
}
