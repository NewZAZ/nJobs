package fr.newzproject.njobs;

import fr.newzproject.njobs.utils.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class JobsCore extends JavaPlugin {

    private static JobsCore instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        AbstractCommand.registerCommands(this);
    }

    private void registerListeners(Listener...listeners){
        Arrays.stream(listeners).iterator().forEachRemaining(listener -> Bukkit.getPluginManager().registerEvents(listener,this));
    }

    public static JobsCore getInstance() {
        return instance;
    }
}
