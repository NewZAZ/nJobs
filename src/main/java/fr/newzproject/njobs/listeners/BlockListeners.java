package fr.newzproject.njobs.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.handlers.BlockHandler;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class BlockListeners implements Listener {
    private final JobsCore plugin;

    public BlockListeners(JobsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.isCancelled())return;
        if(SuperiorSkyblockAPI.getIslandAt(event.getBlock().getLocation()) != null&& !Objects.requireNonNull(SuperiorSkyblockAPI.getIslandAt(event.getBlock().getLocation())).hasPermission(SuperiorSkyblockAPI.getPlayer(event.getPlayer()), IslandPrivilege.getByName("BUILD")))return;
        JobsEnum jobsEnum = JobsXPEnum.getMaterialJobs(event.getBlock().getType());
        if (jobsEnum == null) return;
        if(event.getBlockPlaced().getType() == Material.CROPS || event.getBlockPlaced().getType() == Material.CARROT || event.getBlockPlaced().getType() == Material.POTATO || event.getBlockPlaced().getType() == Material.NETHER_WARTS)return;
        if(JobsXPEnum.getMaterialJobs(event.getBlockPlaced().getType()) != null){
            event.getBlockPlaced().setMetadata(event.getPlayer().getUniqueId().toString(),new FixedMetadataValue(plugin,true));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event){
        if(Bukkit.getPluginManager().isPluginEnabled("SuperiorSkyblock2")){
            new BlockHandler(plugin).superiorSkyBlockBlockBreak(event);
        }
    }
}
