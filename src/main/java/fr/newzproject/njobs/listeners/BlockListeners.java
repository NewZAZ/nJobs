package fr.newzproject.njobs.listeners;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.handlers.BlockHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListeners implements Listener {
    private final JobsCore plugin;

    public BlockListeners(JobsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event){
        if(Bukkit.getPluginManager().isPluginEnabled("SuperiorSkyblock2")){
            new BlockHandler(plugin).superiorSkyBlockBlockBreak(event);
        }
    }
}
