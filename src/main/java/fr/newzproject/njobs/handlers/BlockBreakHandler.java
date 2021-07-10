package fr.newzproject.njobs.handlers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.boosters.BoosterManager;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.managers.Manager;
import fr.newzproject.njobs.managers.WorthManager;
import fr.newzproject.njobs.utils.compatibility.CompatibleMaterial;
import fr.newzproject.njobs.utils.compatibility.CompatibleMessage;
import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

import java.util.Collection;

public class BlockBreakHandler {

    private final JobsCore plugin;

    public BlockBreakHandler(JobsCore plugin) {
        this.plugin = plugin;
    }

    /**
     * Listen when block is broken on an island.
     *
     * @param event {@link BlockBreakEvent}.
     */
    public void superiorSkyBlockBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        if (SuperiorSkyblockAPI.getIslandAt(location) == null) return;
        if (!SuperiorSkyblockAPI.getPlayer(player).hasIsland()) return;
        if (SuperiorSkyblockAPI.getPlayer(player).getIsland() != SuperiorSkyblockAPI.getIslandAt(location)) return;
        if (CompatibleMaterial.getMaterial(block) == null) return;
        if (WorthManager.getInstance().getJobType(CompatibleMaterial.getMaterial(block)) == null) return;

        WorthManager experienceManager = WorthManager.getInstance();
        Manager manager = Manager.getInstance();

        if (CompatibleMaterial.getMaterial(block) == CompatibleMaterial.NETHER_WART) {
            NetherWarts netherWarts = (NetherWarts) block.getState().getData();
            if (netherWarts.getState() != NetherWartsState.RIPE) return;
        } else if (block.getState().getData() instanceof Crops) {
            if (((Crops) block.getState().getData()).getState() != CropState.RIPE) return;
        }
        if (block.hasMetadata(player.getUniqueId().toString())) {
            block.removeMetadata(player.getUniqueId().toString(), plugin);
            return;
        }

        Collection<JobType> types = experienceManager.getJobType(CompatibleMaterial.getMaterial(block));

        if (types == null) return;

        types.forEach(type -> {
            Job job = manager.getJob(player.getUniqueId(), type);
            manager.addJobXp(player.getUniqueId(), type, experienceManager.getMaterialWorth(CompatibleMaterial.getMaterial(block), type) * BoosterManager.getInstance().getMultiplier(player.getUniqueId()));
            CompatibleMessage.sendActionBar(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', plugin.actionBarMessage.replaceAll("%job_name%", type.getName()).replaceAll("%job_xp%", String.valueOf(manager.getJobXp(player.getUniqueId(), type))).replaceAll("%cost_for_levelup%", String.valueOf(experienceManager.getXpForLevelup(type, job))).replaceAll("%xp%", String.valueOf(experienceManager.getMaterialWorth(CompatibleMaterial.getMaterial(block), type)))));
        });

    }


}
