package fr.newzproject.njobs.jobs.enums;

import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.Jobs;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import sun.applet.Main;

public enum JobsXPEnum {
    MELON_BLOCK(JobsEnum.AGRICULTEUR,getXP("Agriculteur","MELON_BLOCK"),Material.MELON_BLOCK),
    CARROT(JobsEnum.AGRICULTEUR,getXP("Agriculteur","CARROT"),Material.CARROT, (byte) 7),
    POTATO(JobsEnum.AGRICULTEUR,getXP("Agriculteur","POTATO"),Material.POTATO,(byte) 7),
    PUMPKIN(JobsEnum.AGRICULTEUR,getXP("Agriculteur","PUMPKIN"),Material.PUMPKIN),
    CACTUS(JobsEnum.AGRICULTEUR,getXP("Agriculteur","CACTUS"),Material.CACTUS),
    SUGAR_CANE(JobsEnum.AGRICULTEUR,getXP("Agriculteur","SUGAR_CANE_BLOCK"),Material.SUGAR_CANE_BLOCK),
    WHEAT(JobsEnum.AGRICULTEUR,getXP("Agriculteur","CROPS"),Material.CROPS,(byte)7),
    COCAO(JobsEnum.AGRICULTEUR,getXP("Agriculteur","COCOA"),Material.COCOA,(byte) 7),
    NETHER_WARTS(JobsEnum.AGRICULTEUR,getXP("Agriculteur","NETHER_WARTS"),Material.NETHER_WARTS,(byte) 3),

    SPIDER(JobsEnum.CHASSEUR,getXP("Chasseur","SPIDER"), EntityType.SPIDER),
    CAVE_SPIDER(JobsEnum.CHASSEUR,getXP("Chasseur","CAVE_SPIDER"), EntityType.CAVE_SPIDER),
    ENDERMAN(JobsEnum.CHASSEUR,getXP("Chasseur","ENDERMAN"), EntityType.ENDERMAN),
    PIG_ZOMBIE(JobsEnum.CHASSEUR,getXP("Chasseur","PIG_ZOMBIE"), EntityType.PIG_ZOMBIE),
    BLAZE(JobsEnum.CHASSEUR,getXP("Chasseur","BLAZE"), EntityType.BLAZE),
    CREEPER(JobsEnum.CHASSEUR,getXP("Chasseur","CREEPER"), EntityType.CREEPER),
    MAGMA_CUBE(JobsEnum.CHASSEUR,getXP("Chasseur","MAGMA_CUBE"), EntityType.MAGMA_CUBE),
    SKELETON(JobsEnum.CHASSEUR,getXP("Chasseur","SKELETON"), EntityType.SKELETON),
    SLIME(JobsEnum.CHASSEUR,getXP("Chasseur","SLIME"), EntityType.SLIME),
    WITCH(JobsEnum.CHASSEUR,getXP("Chasseur","WITCH"), EntityType.WITCH),
    ZOMBIE_VILLAGER(JobsEnum.CHASSEUR,getXP("Chasseur","ZOMBIE_VILLAGER"), EntityType.ZOMBIE_VILLAGER),
    GUARDIAN(JobsEnum.CHASSEUR,getXP("Chasseur","GUARDIAN"), EntityType.GUARDIAN),
    GHAST(JobsEnum.CHASSEUR,getXP("Chasseur","GHAST"), EntityType.GHAST),
    WITHER_SKELETON(JobsEnum.CHASSEUR,getXP("Chasseur","WITHER_SKELETON"), EntityType.WITHER_SKELETON),
    ZOMBIE(JobsEnum.CHASSEUR,getXP("Chasseur","ZOMBIE"), EntityType.ZOMBIE),
    PIG(JobsEnum.CHASSEUR,getXP("Chasseur","PIG"), EntityType.PIG),
    CHICKEN(JobsEnum.CHASSEUR,getXP("Chasseur","CHICKEN"), EntityType.CHICKEN),
    COW(JobsEnum.CHASSEUR,getXP("Chasseur","COW"), EntityType.COW),
    HORSE(JobsEnum.CHASSEUR,getXP("Chasseur","HORSE"), EntityType.HORSE),
    LLAMA(JobsEnum.CHASSEUR,getXP("Chasseur","LLAMA"), EntityType.LLAMA),
    POLAR_BEAR(JobsEnum.CHASSEUR,getXP("Chasseur","POLAR_BEAR"), EntityType.POLAR_BEAR),
    RABBIT(JobsEnum.CHASSEUR,getXP("Chasseur","RABBIT"), EntityType.RABBIT),
    SHEEP(JobsEnum.CHASSEUR,getXP("Chasseur","SHEEP"), EntityType.SHEEP),
    SQUID(JobsEnum.CHASSEUR,getXP("Chasseur","SQUID"), EntityType.SQUID),
    WOLF(JobsEnum.CHASSEUR,getXP("Chasseur","WOLF"), EntityType.WOLF),




    STONE(JobsEnum.MINEUR,getXP("Mineur","STONE"),Material.STONE),
    COAL(JobsEnum.MINEUR,getXP("Mineur","COAL_ORE"), Material.COAL_ORE),
    IRON(JobsEnum.MINEUR,getXP("Mineur","IRON_ORE"), Material.IRON_ORE),
    GOLD(JobsEnum.MINEUR,getXP("Mineur","GOLD_ORE"), Material.GOLD_ORE),
    REDSTONE(JobsEnum.MINEUR,getXP("Mineur","REDSTONE_ORE"), Material.REDSTONE_ORE),
    LAPIS(JobsEnum.MINEUR,getXP("Mineur","LAPIS_ORE"), Material.LAPIS_ORE),
    DIAMOND(JobsEnum.MINEUR,getXP("Mineur","DIAMOND_ORE"), Material.DIAMOND_ORE),
    EMERALD(JobsEnum.MINEUR,getXP("Mineur","EMERALD_ORE"), Material.EMERALD_ORE),
    QUARTZ(JobsEnum.MINEUR,getXP("Mineur","QUARTZ"),Material.QUARTZ_ORE);

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

    public static int getXP(String name, String what){
        return JobsCore.getInstance().getFileConfiguration().getInt(name+"."+what+".xp");
    }

    public static int getXpForLevelup(Jobs eJobs){
        return (int) (JobsCore.getInstance().priceBase.get(eJobs.getJobs()) * Math.pow(JobsCore.getInstance().priceMulti.get(eJobs.getJobs()), eJobs.getCurrentLvl()));
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
