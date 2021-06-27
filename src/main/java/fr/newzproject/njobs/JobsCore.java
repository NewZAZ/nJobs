package fr.newzproject.njobs;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.newzproject.api.MainApi;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.JobsRewards;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.listeners.BlockListeners;
import fr.newzproject.njobs.listeners.EntityListeners;
import fr.newzproject.njobs.listeners.JobListeners;
import fr.newzproject.njobs.listeners.PlayerListeners;
import fr.newzproject.njobs.utils.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JobsCore extends JavaPlugin {

    private static JobsCore instance;
    private final HashMap<Player, List<Jobs>> playerJobs = new HashMap<>();
    private JobsRewards jobsRewards;

    private final File file = new File("plugins/nJobs","worth.yml");
    private final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
    @Override
    public void onEnable() {
        instance = this;
        MainApi.setup(this);
        jobsRewards = new JobsRewards();
        jobsRewards.initRewards();
        try {
            initWorthFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveDefaultConfig();
        AbstractCommand.registerCommands(this);
        registerListeners(new BlockListeners(this),new PlayerListeners(this), new JobListeners(), new EntityListeners(this));
    }

    private void registerListeners(Listener...listeners){
        Arrays.stream(listeners).iterator().forEachRemaining(listener -> Bukkit.getPluginManager().registerEvents(listener,this));
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()){
            new JobsManager(this,player).save();
        }
    }

    public void initWorthFile() throws IOException {
        if(file.exists())return;
        fileConfiguration.set("Agriculteur.MELON_BLOCK.xp",10);
        fileConfiguration.set("Agriculteur.CARROT.xp",10);
        fileConfiguration.set("Agriculteur.POTATO.xp",10);
        fileConfiguration.set("Agriculteur.PUMPKIN.xp",10);
        fileConfiguration.set("Agriculteur.CACTUS.xp",10);
        fileConfiguration.set("Agriculteur.WHEAT.xp",10);
        fileConfiguration.set("Agriculteur.COCOA.xp",10);
        fileConfiguration.set("Agriculteur.NETHER_WARTS.xp",10);
        fileConfiguration.set("Chasseur.SPIDER.xp",10);
        fileConfiguration.set("Chasseur.CAVE_SPIDER.xp",10);
        fileConfiguration.set("Chasseur.ENDERMAN.xp",10);
        fileConfiguration.set("Chasseur.PIG_ZOMBIE.xp",10);
        fileConfiguration.set("Chasseur.BLAZE.xp",10);
        fileConfiguration.set("Chasseur.CREEPER.xp",10);
        fileConfiguration.set("Chasseur.MAGMA_CUBE.xp",10);
        fileConfiguration.set("Chasseur.SKELETON.xp",10);
        fileConfiguration.set("Chasseur.SLIME.xp",10);
        fileConfiguration.set("Chasseur.WITCH.xp",10);
        fileConfiguration.set("Chasseur.ZOMBIE_VILLAGER.xp",10);
        fileConfiguration.set("Chasseur.GUARDIAN.xp",10);
        fileConfiguration.set("Chasseur.GHAST.xp",10);
        fileConfiguration.set("Chasseur.WITHER_SKELETON.xp",10);
        fileConfiguration.set("Chasseur.ZOMBIE.xp",10);
        fileConfiguration.set("Chasseur.PIG.xp",10);
        fileConfiguration.set("Chasseur.CHICKEN.xp",10);
        fileConfiguration.set("Chasseur.COW.xp",10);
        fileConfiguration.set("Chasseur.HORSE.xp",10);
        fileConfiguration.set("Chasseur.LLAMA.xp",10);
        fileConfiguration.set("Chasseur.POLAR_BEAR.xp",10);
        fileConfiguration.set("Chasseur.RABBIT.xp",10);
        fileConfiguration.set("Chasseur.SHEEP.xp",10);
        fileConfiguration.set("Chasseur.SQUID.xp",10);
        fileConfiguration.set("Chasseur.WOLF.xp",10);
        fileConfiguration.set("Mineur.STONE.xp",10);
        fileConfiguration.set("Mineur.COAL.xp",10);
        fileConfiguration.set("Mineur.IRON.xp",10);
        fileConfiguration.set("Mineur.GOLD.xp",10);
        fileConfiguration.set("Mineur.REDSTONE.xp",10);
        fileConfiguration.set("Mineur.LAPIS.xp",10);
        fileConfiguration.set("Mineur.DIAMOND.xp",10);
        fileConfiguration.set("Mineur.EMERALD.xp",10);
        fileConfiguration.set("Mineur.QUARTZ.xp",10);
        fileConfiguration.save(file);
    }

    public JobsRewards getJobsRewards() {
        return jobsRewards;
    }

    public static JobsCore getInstance() {
        return instance;
    }

    public HashMap<Player, List<Jobs>> getPlayerJobs() {
        return playerJobs;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
