package fr.newzproject.njobs.jobs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JobsRewards {
    private static JobsRewards instance;

    private final File file = new File("plugins/nJobs","rewards.yml");
    private final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    private final Map<JobType, Map<Integer, String>> jobsRewardHashMap = new HashMap<>();

    private JobsRewards() {
        instance = this;
    }

    /**
     * Init rewards on server's load
     */
    public void initRewards(){
        for(String key : configuration.getKeys(true)){
            if(key.contains("Mineur")){

                String[] keys = key.split("\\.");

                if(keys.length == 2) {
                    if (isInteger(keys[1])) {
                        jobsRewardHashMap.computeIfAbsent(JobType.MINEUR,jobType -> new HashMap<>()).put(Integer.parseInt(keys[1]),getCmd(keys[0], Integer.parseInt(keys[1])));
                    }
                }
            }else if(key.contains("Chasseur")){
                String[] keys = key.split("\\.");

                if(keys.length == 2) {
                    if (isInteger(keys[1])) {
                        jobsRewardHashMap.computeIfAbsent(JobType.CHASSEUR,jobType -> new HashMap<>()).put(Integer.parseInt(keys[1]),getCmd(keys[0], Integer.parseInt(keys[1])));
                    }
                }
            }else if(key.contains("Agriculteur")){
                String[] keys = key.split("\\.");

                if(keys.length == 2) {
                    if (isInteger(keys[1])) {
                        jobsRewardHashMap.computeIfAbsent(JobType.AGRICULTEUR,jobType -> new HashMap<>()).put(Integer.parseInt(keys[1]),getCmd(keys[0], Integer.parseInt(keys[1])));
                    }
                }
            }
        }
    }

    /**
     *
     * @param level reward's level.
     * @return reward's command.
     */

    public String getRewards(JobType type, int level){
        return jobsRewardHashMap.get(type).getOrDefault(level,"rien");
    }

    /**
     *
     * @param job reward's job.
     * @param level reward's level.
     * @return reward's command.
     */

    private String getCmd(String job, int level){
        return configuration.getString(job+"."+level+".cmd");
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static JobsRewards getInstance() {
        return instance == null ? instance = new JobsRewards() : instance;
    }
}
