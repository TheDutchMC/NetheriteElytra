package nl.thedutchmc.NetheriteElytra;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraItem;

public class CommandHandler implements CommandExecutor {
	
	private ElytraItem ei;
	
	public CommandHandler(NetheriteElytra plugin) {
		this.ei = new ElytraItem(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//Check if the command name is "elytra"
		if(!command.getName().equals("elytra")) return false;
		
		//Check if the sender has the permissions for the command
		if(!sender.hasPermission("elytra.use")) {
			sender.sendMessage(NetheriteElytra.getPrefix() + ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}
		
		//Create a new NetheriteElytra
		ItemStack is = ei.getElytra(1);
		
		//Give the newly created NetheriteElytra to the player
		((Player) sender).getInventory().addItem(is);
		
		//Inform them that we've given them the item
		sender.sendMessage(NetheriteElytra.getPrefix() + ChatColor.AQUA + "Elytra given!");
		
		return true;		
	}
	
}
