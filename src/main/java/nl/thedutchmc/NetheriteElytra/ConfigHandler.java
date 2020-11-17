package nl.thedutchmc.NetheriteElytra;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {
	private File file;
	private FileConfiguration config;
	private NetheriteElytra plugin;
	
	public static boolean isElytraWearerFireProof, preventDespawn, isElytraItemFireProof, isCraftable;
	
	public ConfigHandler(NetheriteElytra plugin) {
		this.plugin = plugin;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public void loadConfig() {
		file = new File(plugin.getDataFolder(), "config.yml");
		
		//Check if the config file exists. If not, create the directory and
		//copy the config file from the jar to the folder
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			plugin.saveResource("config.yml", false);
		}
		
		config = new YamlConfiguration();
		
		//Attempt to load and read the file
		try {
			config.load(file);
			readConfig();
		} catch (InvalidConfigurationException e) {
			NetheriteElytra.logWarn("Invalid config.yml!");
		} catch (IOException e) {
			NetheriteElytra.logWarn(e.getMessage());
		}
	}
	
	public void readConfig() {
		isElytraWearerFireProof = config.getBoolean("isElytraWearerFireProof");
		preventDespawn = config.getBoolean("preventDespawn");
		isElytraItemFireProof = config.getBoolean("isElytraItemFireProof");
		isCraftable = config.getBoolean("isCraftable");
	}
}
