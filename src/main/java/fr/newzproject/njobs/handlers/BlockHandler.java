package fr.newzproject.njobs.handlers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;

import fr.newzproject.njobs.utils.compatibility.CompatibleMaterial;
import fr.newzproject.njobs.utils.compatibility.CompatibleMessage;
import org.bukkit.ChatColor;
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
        JobsManager jobsManager = new JobsManager(plugin,player);
        if(block.hasMetadata(player.getUniqueId().toString())){
            block.removeMetadata(player.getUniqueId().toString(),plugin);
            return;
        }

        if(block.getType() != CompatibleMaterial.WHEAT_SEEDS.getBlockMaterial() && block.getType() != CompatibleMaterial.CARROT.getBlockMaterial() && block.getType() != CompatibleMaterial.POTATO.getBlockMaterial() && block.getType() != CompatibleMaterial.NETHER_WART.getBlockMaterial()) {
            JobsEnum jobsEnum = JobsXPEnum.getMaterialJobs(block.getType());

            if(jobsEnum == null)return;


            Jobs jobs = jobsManager.getJob(jobsEnum);
            new JobsManager(plugin,player).addJobXp(jobsEnum, JobsXPEnum.getMaterialWorth(block.getType()));
            CompatibleMessage.sendActionBar(event.getPlayer(), ChatColor.translateAlternateColorCodes('&',plugin.actionBarMessage.replaceAll("%job_name%",jobsEnum.getJobName()).replaceAll("%job_xp%",String.valueOf(jobsManager.getJobXp(jobsEnum))).replaceAll("%cost_for_levelup%",String.valueOf(JobsXPEnum.getXpForLevelup(jobs)).replaceAll("%xp%",String.valueOf(JobsXPEnum.getMaterialWorth(block.getType()))))));

        }else {
            if(JobsXPEnum.getMaterialData(event.getBlock().getType()) != event.getBlock().getData())return;
            JobsEnum jobsEnum = JobsXPEnum.getMaterialJobs(event.getBlock().getType());
            if (jobsEnum == null) return;
            Jobs jobs = jobsManager.getJob(jobsEnum);
            jobsManager.addJobXp(jobsEnum,JobsXPEnum.getMaterialWorth(event.getBlock().getType()));
            CompatibleMessage.sendActionBar(event.getPlayer(), ChatColor.translateAlternateColorCodes('&',plugin.actionBarMessage.replaceAll("%job_name%",jobsEnum.getJobName()).replaceAll("%job_xp%",String.valueOf(jobsManager.getJobXp(jobsEnum))).replaceAll("%cost_for_levelup%",String.valueOf(JobsXPEnum.getXpForLevelup(jobs)).replaceAll("%xp%",String.valueOf(JobsXPEnum.getMaterialWorth(block.getType()))))));
        }
    }


}
