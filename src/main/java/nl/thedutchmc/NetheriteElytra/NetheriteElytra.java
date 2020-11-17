package nl.thedutchmc.NetheriteElytra;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraObject;
import nl.thedutchmc.NetheriteElytra.listeners.EntityDamageEventListener;
import nl.thedutchmc.NetheriteElytra.listeners.ItemDespawnEventListener;
import nl.thedutchmc.NetheriteElytra.listeners.PlayerItemDamageEventListener;
import nl.thedutchmc.NetheriteElytra.recipe.Recipe;

public class NetheriteElytra extends JavaPlugin {
	
	public List<ElytraObject> elytras = new ArrayList<>();
	private static NetheriteElytra INSTANCE;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		
		logInfo("Welcome to NetheriteElytra v" + this.getDescription().getVersion() + " by TheDutchMC");
		logInfo("To use this plugin's custom textures, add this link: https://github.com/TheDutchMC/NetheriteElytra/raw/master/NetheriteElytra-resource-pack.zip to your server.properties file, at resource-pack.");
		
		//Register the /elytra command
		this.getCommand("elytra").setExecutor(new CommandHandler(this));
		
		//Load configuration file
		new ConfigHandler(this).loadConfig();
		
		//Register all event listeners
		Bukkit.getPluginManager().registerEvents(new PlayerItemDamageEventListener(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageEventListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ItemDespawnEventListener(this), this);
		
		//Check if the NetheriteElytra should be craftable.
		//If yes, register the Recipe
		if(ConfigHandler.isCraftable) {
			Bukkit.addRecipe(new Recipe(this).getNetheriteElytraRecipe());
		}
		
		//Read all existing elytras into the elytras List
		StorageHandler s = new StorageHandler(this);
		elytras = s.read();
	}
	
	@Override
	public void onDisable() {
		logInfo("Thank you for using NetheriteElytra by TheDutchMC. Check out my other work: https://github.com/TheDutchMC");
	}
	
	public static void logInfo(Object log) {
		INSTANCE.getLogger().info(String.valueOf(log));
	}
	
	public static void logWarn(Object log) {
		INSTANCE.getLogger().warning(String.valueOf(log));
	}
	
	public static String getPrefix() {
		return ChatColor.GRAY + "[" + ChatColor.RED + "NE" + ChatColor.GRAY + "] " + ChatColor.RESET;
	}
}
