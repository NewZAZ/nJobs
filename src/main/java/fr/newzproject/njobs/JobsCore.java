package fr.newzproject.njobs;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.listeners.BlockListeners;
import fr.newzproject.njobs.listeners.PlayerListeners;
import fr.newzproject.njobs.utils.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JobsCore extends JavaPlugin {

    private static JobsCore instance;
    private final HashMap<Player, List<Jobs>> playerJobs = new HashMap<>();
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        AbstractCommand.registerCommands(this);
        registerListeners(new BlockListeners(this),new PlayerListeners(this));
    }

    private void registerListeners(Listener...listeners){
        Arrays.stream(listeners).iterator().forEachRemaining(listener -> Bukkit.getPluginManager().registerEvents(listener,this));
    }

    public static JobsCore getInstance() {
        return instance;
    }

    public HashMap<Player, List<Jobs>> getPlayerJobs() {
        return playerJobs;
    }
}
