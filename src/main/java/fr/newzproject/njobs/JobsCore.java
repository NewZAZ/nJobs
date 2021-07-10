package fr.newzproject.njobs;

import fr.mrmicky.fastinv.FastInvManager;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.listeners.BlockListeners;
import fr.newzproject.njobs.listeners.EntityListeners;
import fr.newzproject.njobs.listeners.JobListeners;
import fr.newzproject.njobs.listeners.PlayerListeners;
import fr.newzproject.njobs.managers.Manager;
import fr.newzproject.njobs.managers.RewardsManager;
import fr.newzproject.njobs.managers.WorthManager;
import fr.newzproject.njobs.storage.mongo.DBCredentials;
import fr.newzproject.njobs.storage.mongo.Mongo;
import fr.newzproject.njobs.utils.AbstractCommand;
import fr.newzproject.njobs.utils.AdvancedLicense;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobsCore extends JavaPlugin {

    private static JobsCore instance;
    public final HashMap<JobType, Double> priceBase = new HashMap<>();
    public final HashMap<JobType, Double> priceMulti = new HashMap<>();
    private final AdvancedLicense.ValidationType vt = new AdvancedLicense(getConfig().getString("License"), "https://newz-project.000webhostapp.com/verify.php", this).setSecurityKey("OoOZEOIoIOKIEOAij45AZEAds5UHHaeL4451").isValid();
    public String actionBarMessage;

    public static JobsCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (vt == AdvancedLicense.ValidationType.KEY_NOT_FOUND) {
            System.out.println("\u001B[31mLicense key not found !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        } else if (vt == AdvancedLicense.ValidationType.NOT_VALID_IP) {
            System.out.println("\u001B[31mLicense key founded but it's not same ip !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        } else if (vt == AdvancedLicense.ValidationType.WRONG_RESPONSE) {
            System.out.println("\u001BERROR !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        } else if (vt == AdvancedLicense.ValidationType.VALID) {
            instance = this;
            FastInvManager.register(this);

            saveResource("worth");
            saveResource("rewards");
            saveResource("inventory");

            System.setProperty("DB.TRACE", "true");
            Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
            WorthManager.getInstance().initWorth();

            Mongo.getInstance().connect(new DBCredentials(getConfig().getString("DB.host"), getConfig().getString("DB.user"), getConfig().getString("DB.pass"), getConfig().getString("DB.dbName")));

            registerListeners(new BlockListeners(this), new PlayerListeners(), new JobListeners(this), new EntityListeners(this));

            AbstractCommand.registerCommands(this);
            initConfig();
            RewardsManager.getInstance().initRewards();
        } else {
            System.out.println("\u001B[31mERROR !\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void saveResource(String child) {
        if (!new File("plugins/nJobs", child + ".yml").exists()) {
            saveResource(child + ".yml", false);
            System.out.println("\u001B[32mFile " + child + ".yml Created\u001B[0m");
        }
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).iterator().forEachRemaining(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Manager.getInstance().save(player.getUniqueId());
        }
    }

    private void initConfig() {
        priceBase.put(JobType.CHASSEUR, getConfig().getDouble("Chasseur.priceBase"));
        priceBase.put(JobType.MINEUR, getConfig().getDouble("Mineur.priceBase"));
        priceBase.put(JobType.AGRICULTEUR, getConfig().getDouble("Agriculteur.priceBase"));

        priceMulti.put(JobType.CHASSEUR, getConfig().getDouble("Chasseur.priceMulti"));
        priceMulti.put(JobType.MINEUR, getConfig().getDouble("Mineur.priceMulti"));
        priceMulti.put(JobType.AGRICULTEUR, getConfig().getDouble("Agriculteur.priceMulti"));

        actionBarMessage = getConfig().getString("messages.actionbar");
    }

}
