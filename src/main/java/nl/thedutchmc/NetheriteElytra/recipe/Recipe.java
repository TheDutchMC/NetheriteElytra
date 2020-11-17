package nl.thedutchmc.NetheriteElytra.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.SmithingRecipe;

import nl.thedutchmc.NetheriteElytra.NetheriteElytra;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraItem;

public class Recipe {

	private NetheriteElytra plugin;
	private ElytraItem ei;
	
	public Recipe(NetheriteElytra plugin) {
		this.plugin = plugin;
		ei = new ElytraItem(plugin);
	}
	
	public SmithingRecipe getNetheriteElytraRecipe() {
		NamespacedKey key = new NamespacedKey(plugin, "netherite_elytra");
		
		ItemStack result = ei.getElytra(1);
		
		MaterialChoice base = new MaterialChoice(Material.ELYTRA);
		MaterialChoice addition = new MaterialChoice(Material.NETHERITE_INGOT);
		
		SmithingRecipe rec = new SmithingRecipe(key, result, base, addition);
		
		return rec;
	}
}
