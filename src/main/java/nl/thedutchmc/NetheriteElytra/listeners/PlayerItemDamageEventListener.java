package nl.thedutchmc.NetheriteElytra.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import nl.thedutchmc.NetheriteElytra.NetheriteElytra;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraItem;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraObject;

public class PlayerItemDamageEventListener implements Listener {

	private ElytraItem ei;
	
	public PlayerItemDamageEventListener(NetheriteElytra plugin) {
		this.ei = new ElytraItem(plugin);
	}
	
	@EventHandler
	public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
		
		//Get the ElytraObject. Can be null, if true, return
		ElytraObject eo = ei.getNetheriteElytra(event.getItem());		
		if(eo == null) return;
		
		//Cancel the damage event. We'll be doing it ourselves
		event.setCancelled(true);
		
		//50% chance that it should take damage
		if(eo.shouldItemTakeDamage()) {			
			//Get the ItemMeta's Damageable and add 1 to the damage
			Damageable dam = (Damageable) event.getItem().getItemMeta();
			dam.setDamage(dam.getDamage() + 1);
			
			//Apply the new ItemMeta to the ItemStack
			event.getItem().setItemMeta((ItemMeta) dam);
		}
	}
}
