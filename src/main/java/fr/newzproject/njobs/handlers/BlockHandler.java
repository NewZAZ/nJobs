package fr.newzproject.njobs.handlers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockHandler {

    private final JobsCore plugin;

    public BlockHandler(JobsCore plugin) {
        this.plugin = plugin;
    }

    public void superiorSkyBlockBlockBreak(BlockBreakEvent event){
        if(event.isCancelled())return;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        if(SuperiorSkyblockAPI.getIslandAt(location) == null)return;
        if(!SuperiorSkyblockAPI.getPlayer(player).hasIsland())return;
        if(SuperiorSkyblockAPI.getPlayer(player).getIsland() != SuperiorSkyblockAPI.getIslandAt(location))return;

        if(block.hasMetadata(player.getUniqueId().toString()))return;

        if(block.getType() != Material.CROPS && block.getType() != Material.CARROT && block.getType() != Material.POTATO && block.getType() != Material.NETHER_WARTS) {
            JobsEnum jobsEnum = JobsXPEnum.getMaterialJobs(block.getType());

            if(jobsEnum == null)return;
            new JobsManager(plugin,player).addJobXp(jobsEnum, JobsXPEnum.getMaterialWorth(block.getType()));
        }
    }
}
