package fr.newzproject.njobs.command;

import fr.newzproject.api.MainApi;
import fr.newzproject.api.inventory.NInventory;
import fr.newzproject.api.items.ClickableItem;
import fr.newzproject.api.items.ItemBuilder;
import fr.newzproject.njobs.JobsCore;
import fr.newzproject.njobs.jobs.Jobs;
import fr.newzproject.njobs.jobs.JobsManager;
import fr.newzproject.njobs.jobs.enums.JobsEnum;
import fr.newzproject.njobs.jobs.enums.JobsXPEnum;
import fr.newzproject.njobs.utils.AbstractCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class JobsCommand extends AbstractCommand {

    private final JobsCore plugin;
    public JobsCommand(JobsCore plugin){
        super("jobs",false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0){
            NInventory jobInventory = MainApi.getInventoryManager().setSize(3).setTitle("nJobs - GUI").create();

            jobInventory.setItem(0, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setDisplayName("").setDurability((short) 4).build()));
            jobInventory.setItem(1, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setDisplayName("").setDurability((short) 4).build()));
            jobInventory.setItem(9, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setDisplayName("").setDurability((short) 4).build()));

            Jobs mineur = new JobsManager(plugin,player).getJob(JobsEnum.MINEUR);
            Jobs agriculteur = new JobsManager(plugin,player).getJob(JobsEnum.AGRICULTEUR);
            Jobs chasseur = new JobsManager(plugin,player).getJob(JobsEnum.CHASSEUR);
            jobInventory.setItem(11, ClickableItem.empty(new ItemBuilder(Material.DIAMOND_PICKAXE).setDisplayName("§9Mineur").setLoreWithList(Arrays.asList("§9Niveau : "+mineur.getCurrentLvl(),"§9Xp: "+mineur.getXp()+"/"+ JobsXPEnum.getXpForLevelup(mineur))).build()));
            jobInventory.setItem(13, ClickableItem.empty(new ItemBuilder(Material.GOLD_HOE).setDisplayName("§aAgriculteur").setLoreWithList(Arrays.asList("§aNiveau : "+agriculteur.getCurrentLvl(),"§aXp: "+agriculteur.getXp()+"/"+ JobsXPEnum.getXpForLevelup(agriculteur))).build()));
            jobInventory.setItem(15, ClickableItem.empty(new ItemBuilder(Material.DIAMOND_SWORD).setDisplayName("§cChasseur").setLoreWithList(Arrays.asList("§cNiveau : "+chasseur.getCurrentLvl(),"§cXp: "+chasseur.getXp()+"/"+ JobsXPEnum.getXpForLevelup(chasseur))).build()));

            jobInventory.setItem(17, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setDisplayName("").setDurability((short) 4).build()));
            jobInventory.setItem(25, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setDisplayName("").setDurability((short) 4).build()));
            jobInventory.setItem(26, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setDisplayName("").setDurability((short) 4).build()));
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")){
                if(player.hasPermission("nJobs.command.jobs.args.*")){
                    player.sendMessage("");
                }
            }
        }
    }
}
