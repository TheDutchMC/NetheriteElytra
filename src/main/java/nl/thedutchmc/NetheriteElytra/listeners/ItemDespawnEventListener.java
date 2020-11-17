package nl.thedutchmc.NetheriteElytra.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

import nl.thedutchmc.NetheriteElytra.ConfigHandler;
import nl.thedutchmc.NetheriteElytra.NetheriteElytra;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraItem;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraObject;

public class ItemDespawnEventListener implements Listener {

	private ElytraItem ei;
	
	public ItemDespawnEventListener(NetheriteElytra plugin) {
		this.ei = new ElytraItem(plugin);
	}
	
	@EventHandler
	public void onItemDespawnEvent(ItemDespawnEvent event) {
		
		//Check if the user enabled preventDespawn
		if(!ConfigHandler.preventDespawn) return;
		
		//Get the ElytraObject. Can be null
		ElytraObject eo = ei.getNetheriteElytra(event.getEntity().getItemStack());
		
		//Check if it isnt null
		//If true, cancel the event
		if(eo != null) {
			event.setCancelled(true);
		}
	}
}
