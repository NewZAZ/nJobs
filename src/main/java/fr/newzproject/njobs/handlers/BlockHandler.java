package fr.newzproject.njobs.handlers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import fr.newzproject.njobs.utils.Reflections;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
            sendActionBar(event.getPlayer(), "§a" + jobsEnum.getJobName() + ": " + jobsManager.getJobXp(jobsEnum) + "/" + JobsXPEnum.getXpForLevelup(jobs.getJobs()) + " (+" + JobsXPEnum.getMaterialWorth(event.getBlock().getType()) + ")");

        }else {
            if(JobsXPEnum.getMaterialData(event.getBlock().getType()) != event.getBlock().getData())return;
            JobsEnum jobsEnum = JobsXPEnum.getMaterialJobs(event.getBlock().getType());
            if (jobsEnum == null) return;
            Jobs jobs = new Jobs(player, jobsEnum);
            JobsManager jobsManager = new JobsManager(plugin,player);
            jobsManager.addJobXp(jobsEnum,JobsXPEnum.getMaterialWorth(event.getBlock().getType()));
            sendActionBar(event.getPlayer(), "§a" + jobsEnum.getJobName() + ": " + jobsManager.getJobXp(jobsEnum) + "/" + JobsXPEnum.getXpForLevelup(jobs.getJobs()) + " (+" + JobsXPEnum.getMaterialWorth(event.getBlock().getType()) + ")");
        }
    }

    private void sendActionBar(Player p, String message){
        Object text = Reflections.OBJECTS.getObject(Reflections.NMS.getClass("ChatComponentText"), ChatColor.translateAlternateColorCodes('&', message));
        Object object = Reflections.OBJECTS.getObject(Reflections.NMS.getClass("PacketPlayOutChat"),text ,(byte)2);
        Reflections.METHODS.invoke(Reflections.FIELDS.getValue(Reflections.METHODS.invoke(p, "getHandle"), "playerConnection"), "sendPacket", object);
    }

}
