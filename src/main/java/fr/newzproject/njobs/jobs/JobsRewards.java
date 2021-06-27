package fr.newzproject.njobs.jobs;

import fr.newzproject.api.items.ItemBuilder;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JobsRewards {
    private final File file = new File("plugins/nJobs","rewards.yml");
    private final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    private final HashMap<JobsEnum, HashMap<Integer, String>> jobsRewardHashMap = new HashMap<>();

    public void initRewards(){
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String key : configuration.getKeys(true)){
            System.out.println(key);
            if(key.contains("Mineur")){

                String[] keys = key.split("\\.");

                if(keys.length == 2) {
                    if (isInteger(keys[1])) {
                        HashMap<Integer, String> reward = new HashMap<>();
                        reward.put(Integer.parseInt(keys[1]), getCmd(keys[0], Integer.parseInt(keys[1])));
                        jobsRewardHashMap.putIfAbsent(JobsEnum.MINEUR, reward);
                        System.out.println("good");
                        System.out.println(getCmd(keys[0], Integer.parseInt(keys[1])));
                    }
                }
            }else if(key.contains("Chasseur")){
                String[] keys = key.split("\\.");

                if(keys.length == 2) {
                    if (isInteger(keys[1])) {
                        HashMap<Integer, String> reward = new HashMap<>();
                        reward.put(Integer.parseInt(keys[1]), getCmd(keys[0], Integer.parseInt(keys[1])));
                        jobsRewardHashMap.putIfAbsent(JobsEnum.CHASSEUR, reward);
                        System.out.println("good");
                        System.out.println(getCmd(keys[0], Integer.parseInt(keys[1])));
                    }
                }
            }else if(key.contains("Agriculteur")){
                String[] keys = key.split("\\.");

                if(keys.length == 2) {
                    if (isInteger(keys[1])) {
                        HashMap<Integer, String> reward = new HashMap<>();
                        reward.put(Integer.parseInt(keys[1]), getCmd(keys[0], Integer.parseInt(keys[1])));
                        jobsRewardHashMap.putIfAbsent(JobsEnum.AGRICULTEUR, reward);
                        System.out.println("good");
                        System.out.println(getCmd(keys[0], Integer.parseInt(keys[1])));
                    }
                }
            }
        }
    }

    public String getRewards(JobsEnum jobsEnum, int level){
        return jobsRewardHashMap.get(jobsEnum).getOrDefault(level,"rien");
    }

    private String getCmd(String job, int level){
        return configuration.getString(job+"."+level+".cmd");
    }

    private boolean isInteger(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
}
