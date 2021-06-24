package fr.newzproject.njobs.jobs.enums;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum JobsXPEnum {
    MELON_BLOCK(JobsEnum.AGRICULTEUR,2,Material.MELON_BLOCK),
    CARROT(JobsEnum.AGRICULTEUR,2,Material.CARROT, (byte) 7),
    POTATO(JobsEnum.AGRICULTEUR,2,Material.POTATO,(byte) 7),
    PUMPKIN(JobsEnum.AGRICULTEUR,2,Material.PUMPKIN),
    CACTUS(JobsEnum.AGRICULTEUR,1,Material.CACTUS),
    SUGAR_CANE(JobsEnum.AGRICULTEUR,1,Material.SUGAR_CANE_BLOCK),
    WHEAT(JobsEnum.AGRICULTEUR,3,Material.CROPS,(byte)7),
    COCAO(JobsEnum.AGRICULTEUR,3,Material.COCOA,(byte) 7),
    NETHER_WARTS(JobsEnum.AGRICULTEUR,3,Material.NETHER_WARTS,(byte) 7),

    SPIDER(JobsEnum.CHASSEUR,3, EntityType.SPIDER),
    CAVE_SPIDER(JobsEnum.CHASSEUR,3, EntityType.CAVE_SPIDER),
    ENDERMAN(JobsEnum.CHASSEUR,15, EntityType.ENDERMAN),
    PIG_ZOMBIE(JobsEnum.CHASSEUR,5, EntityType.PIG_ZOMBIE),
    BLAZE(JobsEnum.CHASSEUR,5, EntityType.BLAZE),
    CREEPER(JobsEnum.CHASSEUR,2, EntityType.CREEPER),
    MAGMA_CUBE(JobsEnum.CHASSEUR,4, EntityType.MAGMA_CUBE),
    SKELETON(JobsEnum.CHASSEUR,1, EntityType.SKELETON),
    SLIME(JobsEnum.CHASSEUR,4, EntityType.SLIME),
    WITCH(JobsEnum.CHASSEUR,3, EntityType.WITCH),
    ZOMBIE_VILLAGER(JobsEnum.CHASSEUR,1, EntityType.ZOMBIE_VILLAGER),
    GUARDIAN(JobsEnum.CHASSEUR,5, EntityType.GUARDIAN),
    GHAST(JobsEnum.CHASSEUR,5, EntityType.GHAST),
    WITHER_SKELETON(JobsEnum.CHASSEUR,25, EntityType.WITHER_SKELETON),
    ZOMBIE(JobsEnum.CHASSEUR,1, EntityType.ZOMBIE),
    PIG(JobsEnum.CHASSEUR,1, EntityType.PIG),
    CHICKEN(JobsEnum.CHASSEUR,1, EntityType.CHICKEN),
    COW(JobsEnum.CHASSEUR,1, EntityType.COW),
    HORSE(JobsEnum.CHASSEUR,3, EntityType.HORSE),
    LLAMA(JobsEnum.CHASSEUR,3, EntityType.LLAMA),
    POLAR_BEAR(JobsEnum.CHASSEUR,5, EntityType.POLAR_BEAR),
    RABBIT(JobsEnum.CHASSEUR,1, EntityType.RABBIT),
    SHEEP(JobsEnum.CHASSEUR,1, EntityType.SHEEP),
    SQUID(JobsEnum.CHASSEUR,5, EntityType.SQUID),
    WOLF(JobsEnum.CHASSEUR,30, EntityType.WOLF),




    STONE(JobsEnum.MINEUR,1,Material.STONE),
    COAL(JobsEnum.MINEUR,2, Material.COAL_ORE),
    IRON(JobsEnum.MINEUR,3, Material.IRON_ORE),
    GOLD(JobsEnum.MINEUR,4, Material.GOLD_ORE),
    REDSTONE(JobsEnum.MINEUR,5, Material.REDSTONE_ORE),
    LAPIS(JobsEnum.MINEUR,6, Material.LAPIS_ORE),
    DIAMOND(JobsEnum.MINEUR,7, Material.DIAMOND_ORE),
    EMERALD(JobsEnum.MINEUR,8, Material.EMERALD_ORE);

    final JobsEnum jobsEnum;
    final int worth;
    Material material;
    EntityType entityType;
    byte data;

    JobsXPEnum(JobsEnum jobsEnum, int worth, Material material) {
        this.jobsEnum = jobsEnum;
        this.worth = worth;
        this.material = material;
    }

    JobsXPEnum(JobsEnum jobsEnum, int worth, Material material, byte data) {
        this.jobsEnum = jobsEnum;
        this.worth = worth;
        this.material = material;
        this.data = data;
    }

    JobsXPEnum(JobsEnum jobsEnum, int worth, EntityType entityType) {
        this.jobsEnum = jobsEnum;
        this.worth = worth;
        this.entityType = entityType;
    }

    public static int getXpForLevelup(JobsEnum eJobs){
        return (int) (1000 * Math.pow(1.025, eJobs.getCurrentLvl()));
    }

    public static JobsEnum getMaterialJobs(Material material){
        for(JobsXPEnum value : values()){
            if(material == value.getMaterial())
            {
                return value.getJobs();
            }
        }
        return null;
    }

    public static byte getMaterialData(Material material){
        for(JobsXPEnum value : values()){
            if(material == value.getMaterial())
            {
                return value.getData();
            }
        }
        return 0;
    }
    public static int getMaterialWorth(Material material){
        for(JobsXPEnum value : values()){
            if(material == value.getMaterial()){
                return value.getWorth();
            }
        }
        return 0;
    }

    public static int getEntityWorth(EntityType entityType){
        for(JobsXPEnum value : values()){
            if(entityType == value.getEntityType()){
                return value.getWorth();
            }
        }
        return 0;
    }

    public int getWorth() {
        return worth;
    }

    public JobsEnum getJobs() {
        return jobsEnum;
    }

    public Material getMaterial() {
        return material;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public byte getData() {
        return data;
    }
}
