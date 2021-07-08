package fr.newzproject.njobs;

import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.jobs.JobsXPManager;
import fr.newzproject.njobs.utils.compatibility.CompatibleMaterial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;

public class Config {
    private final FileConfiguration fileConfiguration;

    public Config() {
        File file = new File("plugins/nJobs", "worth.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void initWorth() {
        JobsXPManager experienceManager = JobsXPManager.getInstance();

        System.out.println("============ nJobs - Worth File ============");
        for (String key : fileConfiguration.getKeys(true)) {
            if (key.contains("Mineur")) {

                String[] keys = key.split("\\.");

                if (keys.length == 2) {
                    if (CompatibleMaterial.getBlockMaterial(keys[1]) != null) {
                        experienceManager.addMaterialWorth(CompatibleMaterial.getBlockMaterial(keys[1]), JobType.MINEUR, getXP("Mineur", CompatibleMaterial.getBlockMaterial(keys[1]).name()));
                        System.out.println("\u001B[32mMineur block register : " + CompatibleMaterial.getBlockMaterial(keys[1]).name() + " xp : " + getXP("Mineur", CompatibleMaterial.getBlockMaterial(keys[1]).name()) + "\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mMineur block cannot register : " + keys[1] + "\u001B[0m");
                    }
                }
            } else if (key.contains("Chasseur")) {
                String[] keys = key.split("\\.");

                if (keys.length == 2) {
                    if (EntityType.fromName(keys[1]) != null) {
                        experienceManager.addEntityTypeWorth(EntityType.fromName(keys[1]), JobType.CHASSEUR, getXP("Chasseur", EntityType.fromName(keys[1]).name()));
                        System.out.println("\u001B[32mChasseur Entity register : " + EntityType.fromName(keys[1]).name() + " xp : " + getXP("Chasseur", EntityType.fromName(keys[1]).name()) + "\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mChasseur entity cannot register : " + keys[1] + "\u001B[0m");
                    }
                }
            } else if (key.contains("Agriculteur")) {
                String[] keys = key.split("\\.");

                if (keys.length == 2) {
                    if (CompatibleMaterial.getBlockMaterial(keys[1]) != null) {
                        experienceManager.addMaterialWorth(CompatibleMaterial.getBlockMaterial(keys[1]), JobType.AGRICULTEUR, getXP("Agriculteur", CompatibleMaterial.getBlockMaterial(keys[1]).name()));
                        System.out.println("\u001B[32mAgriculteur block register : " + CompatibleMaterial.getBlockMaterial(keys[1]).name() + " xp : " + getXP("Agriculteur", CompatibleMaterial.getBlockMaterial(keys[1]).name()) + "\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mAgriculteur block cannot register : " + keys[1] + "\u001B[0m");
                    }
                }
            }
        }
        System.out.println("============================================");
    }

    private int getXP(String job, String name) {
        return fileConfiguration.getInt(job + "." + name + ".xp");
    }
}
