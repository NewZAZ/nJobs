package fr.newzproject.njobs.command;

import fr.mrmicky.fastinv.FastInv;
import fr.newzproject.njobs.boosters.Booster;
import fr.newzproject.njobs.boosters.BoosterItem;
import fr.newzproject.njobs.boosters.BoosterManager;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.jobs.JobType;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.JobsXPManager;
import fr.newzproject.njobs.utils.AbstractCommand;
import fr.newzproject.njobs.utils.ItemBuilder;
import fr.newzproject.njobs.utils.compatibility.CompatibleMaterial;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JobsCommand extends AbstractCommand {

    public JobsCommand() {
        super("jobs", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        File file = new File("plugins/nJobs", "inventory.yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        BoosterManager boosterManager = BoosterManager.getInstance();
        JobsManager jobsManager = JobsManager.getInstance();
        JobsXPManager experienceManager = JobsXPManager.getInstance();
        if (args.length == 0) {
            Job mineur = jobsManager.getJob(player.getUniqueId(), JobType.MINEUR);
            Job agriculteur = jobsManager.getJob(player.getUniqueId(), JobType.AGRICULTEUR);
            Job chasseur = jobsManager.getJob(player.getUniqueId(), JobType.CHASSEUR);

            FastInv jobInventory = new FastInv(configuration.getInt("gui.size"), ChatColor.translateAlternateColorCodes('&', configuration.getString("gui.name")));

            for (int i = 0; i < configuration.getInt("gui.size"); i++) {
                if (configuration.getString("gui.inventory." + i + ".material") != null && configuration.getString("gui.inventory." + i + ".name") != null && configuration.getStringList("gui.inventory." + i + ".lore") == null) {
                    jobInventory.setItem(i, new ItemBuilder(CompatibleMaterial.getMaterial(configuration.getString("gui.inventory." + i + ".material")).getItem()).setName(configuration.getString("gui.inventory." + i + ".name")).toItemStack());
                } else if (configuration.getString("gui.inventory." + i + ".material") != null && configuration.getString("gui.inventory." + i + ".name") != null && configuration.getStringList("gui.inventory." + i + ".lore") != null) {
                    List<String> lore = new ArrayList<>();

                    configuration.getStringList("gui.inventory." + i + ".lore").forEach(each ->
                            lore.add(ChatColor.translateAlternateColorCodes('&', each.replaceAll("%mineur_level%", String.valueOf(mineur.getCurrentLvl())).replaceAll("%mineur_maxLvl%", String.valueOf(10))
                                    .replaceAll("%mineur_xp%", String.valueOf(mineur.getXp())).replaceAll("%mineur_xp_for_levelup%", String.valueOf(experienceManager.getXpForLevelup(JobType.MINEUR,mineur)))

                                    .replaceAll("%agriculteur_level%", String.valueOf(agriculteur.getCurrentLvl())).replaceAll("%agriculteur_maxLvl%", String.valueOf(10))
                                    .replaceAll("%agriculteur_xp%", String.valueOf(agriculteur.getXp())).replaceAll("%agriculteur_xp_for_levelup%", String.valueOf(experienceManager.getXpForLevelup(JobType.AGRICULTEUR,agriculteur)))

                                    .replaceAll("%chasseur_level%", String.valueOf(chasseur.getCurrentLvl())).replaceAll("%chasseur_maxLvl%", String.valueOf(10))
                                    .replaceAll("%chasseur_xp%", String.valueOf(chasseur.getXp())).replaceAll("%chasseur_xp_for_levelup%", String.valueOf(experienceManager.getXpForLevelup(JobType.CHASSEUR,chasseur))))));
                    jobInventory.setItem(i, new ItemBuilder(CompatibleMaterial.getMaterial(configuration.getString("gui.inventory." + i + ".material")).getItem()).setLore(lore).setName(ChatColor.translateAlternateColorCodes('&', configuration.getString("gui.inventory." + i + ".name"))).toItemStack());
                } else if (configuration.getString("gui.inventory." + i + ".material") != null) {
                    jobInventory.setItem(i, new ItemStack(CompatibleMaterial.getMaterial(configuration.getString("gui.inventory." + i + ".material")).getItem()));
                }
            }

            jobInventory.open(player);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                if (player.hasPermission("nJobs.command.jobs.args.*")) {
                    player.sendMessage("§c/jobs\n" +
                            "§c/jobs help\n" +
                            "§c/jobs boosters\n" +
                            "§c/jobs giveBoosters <multiplier> <time>");
                }
            } else if (args[0].equalsIgnoreCase("boosters")) {
                if (boosterManager.hasBoosters(player.getUniqueId()) && boosterManager.isAlreadyStart(player.getUniqueId())) {
                    player.sendMessage("§cBoosters :\n" +
                            "§cTemps restant : " + boosterManager.getTime(player.getUniqueId()) + "\n" +
                            "§cMultiplier : " + boosterManager.getMultiplier(player.getUniqueId()));
                }
            } else if (args[0].equalsIgnoreCase("author")) {
                player.sendMessage("§cPlugin made by NewZ_AZ#0001");
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("giveBoosters")) {
                if (player.hasPermission("nJobs.command.jobs.args.*")) {
                    if (isDouble(args[1]) && isInteger(args[2])) {
                        Booster booster = new Booster();
                        booster.setMultiplier(Double.parseDouble(args[1]));
                        booster.setTime(Integer.parseInt(args[2]));
                        player.getInventory().addItem(new BoosterItem().getItem(booster));
                    }
                }
            }
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
