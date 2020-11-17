package nl.thedutchmc.NetheriteElytra.elytra;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.NetheriteElytra.NetheriteElytra;
import nl.thedutchmc.NetheriteElytra.StorageHandler;

public class ElytraItem {

	private NetheriteElytra plugin;
	private NamespacedKey key;
	
	public ElytraItem(NetheriteElytra plugin) {
		this.plugin = plugin;
		key = new NamespacedKey(plugin, "netherite_elytra");
	}
	
	/**
	 * Used to create a NetheriteElytra item. Will also create an ElytraObject and add it to the elytras List
	 * @param quantity How many should be made. Should be just 1
	 * @return Returns the newly created ItemStack
	 */
	public ItemStack getElytra(int quantity) {
		ItemStack is = new ItemStack(Material.ELYTRA, quantity);
		ItemMeta im = is.getItemMeta();
		
		int elytraId = getRandomId();

		im.setLore(Arrays.asList(new String[] {"Makes you immune to lava and fire damage and lasts twice as long!"}));
		im.setDisplayName(ChatColor.AQUA + "Netherite Elytra");
		im.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, elytraId);
		im.setCustomModelData(100);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		is.setItemMeta(im);
		
		ElytraObject eo = new ElytraObject(elytraId);
		plugin.elytras.add(eo);
		
		StorageHandler s = new StorageHandler(plugin);
		s.write(eo);
		
		return is;
	}
	
	//Used to generate a random 6-digit number 
	private int getRandomId() {
		final Random rnd = new Random();
		final int n = 100000 + rnd.nextInt(900000);

		return n;
	}
	
	public ElytraObject getNetheriteElytra(ItemStack is) {
		//Check if the item has an ItemMeta
		if(!is.hasItemMeta()) return null;
		
		ItemMeta im = is.getItemMeta();
		
		//Check if the Item has the integer we use for ID
		if(!im.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return null;
		
		//Get the ID from the persistent data container.
		int elytraId = im.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
		
		//Loop over the elytra objects we know, and find the one with the same ID.
		for(ElytraObject eo : plugin.elytras) {
			if(eo.getId() == elytraId) return eo;
		}
		
		return null;
	}
}
