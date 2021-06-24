package fr.newzproject.njobs.handlers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;

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

        if(block.hasMetadata(player.getUniqueId().toString())){
            block.removeMetadata(player.getUniqueId().toString(),plugin);
            return;
        }

        if(block.getType() != Material.CROPS && block.getType() != Material.CARROT && block.getType() != Material.POTATO && block.getType() != Material.NETHER_WARTS) {
            JobsEnum jobsEnum = JobsXPEnum.getMaterialJobs(block.getType());

            if(jobsEnum == null)return;
            Jobs jobs = new Jobs(player, jobsEnum);
            JobsManager jobsManager = new JobsManager(plugin,player);
            new JobsManager(plugin,player).addJobXp(jobsEnum, JobsXPEnum.getMaterialWorth(block.getType()));
            sendActionbar(event.getPlayer(), "§a" + jobsEnum.getJobName() + ": " + jobsManager.getJobXp(jobsEnum) + "/" + JobsXPEnum.getXpForLevelup(jobs.getJobs()) + " (+" + JobsXPEnum.getMaterialWorth(event.getBlock().getType()) + ")");

        }else {
            if(JobsXPEnum.getMaterialData(event.getBlock().getType()) != event.getBlock().getData())return;
            JobsEnum jobsEnum = JobsXPEnum.getMaterialJobs(event.getBlock().getType());
            if (jobsEnum == null) return;
            Jobs jobs = new Jobs(player, jobsEnum);
            JobsManager jobsManager = new JobsManager(plugin,player);
            jobsManager.addJobXp(jobsEnum,JobsXPEnum.getMaterialWorth(event.getBlock().getType()));
            sendActionbar(event.getPlayer(), "§a" + jobsEnum.getJobName() + ": " + jobsManager.getJobXp(jobsEnum) + "/" + JobsXPEnum.getXpForLevelup(jobs.getJobs()) + " (+" + JobsXPEnum.getMaterialWorth(event.getBlock().getType()) + ")");
        }
    }

    public void sendActionbar(Player player, String message) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(icbc, ChatMessageType.GAME_INFO);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
