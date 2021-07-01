package fr.newzproject.njobs;

import fr.newzproject.api.MainApi;
import fr.newzproject.api.utils.AdvancedLicense;
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

import javax.crypto.Cipher;
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

    public HashMap<JobsEnum, Double> priceBase = new HashMap<>();
    public HashMap<JobsEnum, Double> priceMulti = new HashMap<>();

    public String actionBarMessage;
    AdvancedLicense.ValidationType vt = new AdvancedLicense(getConfig().getString("License"),getWebSite(), this).setSecurityKey("OoOZEOIoIOKIEOAij45AZEAds5UHHaeL4451").isValid();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        if(vt == AdvancedLicense.ValidationType.KEY_NOT_FOUND){
            System.out.println("\u001B[31mLicense key not found !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        }else if(vt == AdvancedLicense.ValidationType.NOT_VALID_IP){
            System.out.println("\u001B[31mLicense key founded but it's not same ip !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        }else if(vt == AdvancedLicense.ValidationType.WRONG_RESPONSE){
            System.out.println("\u001BERROR !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        }else if(vt == AdvancedLicense.ValidationType.VALID) {
            instance = this;
            MainApi.setup(this);
            jobsRewards = new JobsRewards();
            jobsRewards.initRewards();
            try {
                initWorthFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            initConfig();
            AbstractCommand.registerCommands(this);
            registerListeners(new BlockListeners(this), new PlayerListeners(this), new JobListeners(this), new EntityListeners(this));
        }else {
            System.out.println("\u001BERROR !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        }
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

    private void initConfig(){
        priceBase.put(JobsEnum.CHASSEUR,getConfig().getDouble("Chasseur.priceBase"));
        priceBase.put(JobsEnum.MINEUR,getConfig().getDouble("Mineur.priceBase"));
        priceBase.put(JobsEnum.AGRICULTEUR,getConfig().getDouble("Agriculteur.priceBase"));

        priceMulti.put(JobsEnum.CHASSEUR,getConfig().getDouble("Chasseur.priceMulti"));
        priceMulti.put(JobsEnum.MINEUR,getConfig().getDouble("Mineur.priceMulti"));
        priceMulti.put(JobsEnum.AGRICULTEUR,getConfig().getDouble("Agriculteur.priceMulti"));

        actionBarMessage = getConfig().getString("messages.actionbar");
    }

    public String getWebSite(){
        return "https://newz-project.000webhostapp.com/verify.php";
    }
    public void initWorthFile() throws IOException {
        if(file.exists())return;
        fileConfiguration.set("Agriculteur.MELON_BLOCK.xp",10);
        fileConfiguration.set("Agriculteur.CARROT.xp",10);
        fileConfiguration.set("Agriculteur.POTATO.xp",10);
        fileConfiguration.set("Agriculteur.PUMPKIN.xp",10);
        fileConfiguration.set("Agriculteur.CACTUS.xp",10);
        fileConfiguration.set("Agriculteur.CROPS.xp",10);
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

    public String getMaterial(){
        String nmsver;
        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
        if(nmsver.startsWith("v1_13") || nmsver.startsWith("v1_14") || nmsver.startsWith("v1_15") || nmsver.startsWith("v1_16") || nmsver.startsWith("v1_17")){
            return "WHEAT_SEEDS";
        }
        return "CROPS";
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
