package fr.newzproject.njobs.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.handlers.BlockBreakHandler;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.jobs.JobsXPManager;
import fr.newzproject.njobs.utils.compatibility.CompatibleMaterial;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Crops;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Collection;
import java.util.Objects;

public class BlockListeners implements Listener {
    private final JobsCore plugin;

    public BlockListeners(JobsCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.isCancelled())return;
        Block block = event.getBlockPlaced();
        if(SuperiorSkyblockAPI.getIslandAt(event.getBlock().getLocation()) != null&& !Objects.requireNonNull(SuperiorSkyblockAPI.getIslandAt(event.getBlock().getLocation())).hasPermission(SuperiorSkyblockAPI.getPlayer(event.getPlayer()), IslandPrivilege.getByName("BUILD")))return;
        if(CompatibleMaterial.getMaterial(block) == null)return;
        Collection<JobType> types = JobsXPManager.getInstance().getJobType(CompatibleMaterial.getMaterial(block));
        if (types == null || types.size() == 0) return;
        if(block.getState().getData() instanceof Crops || CompatibleMaterial.getMaterial(block) == CompatibleMaterial.NETHER_WART)return;
            event.getBlockPlaced().setMetadata(event.getPlayer().getUniqueId().toString(),new FixedMetadataValue(plugin,true));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event){
        if(Bukkit.getPluginManager().isPluginEnabled("SuperiorSkyblock2")){
            new BlockBreakHandler(plugin).superiorSkyBlockBlockBreak(event);
            System.out.println("test");
        }else{
            System.out.println("ERROR : NEED SuperiorSkyblock2");
        }
    }
}
