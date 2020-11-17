package nl.thedutchmc.NetheriteElytra.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;

import nl.thedutchmc.NetheriteElytra.ConfigHandler;
import nl.thedutchmc.NetheriteElytra.NetheriteElytra;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraItem;
import nl.thedutchmc.NetheriteElytra.elytra.ElytraObject;

public class EntityDamageEventListener implements Listener {

	private ElytraItem ei;
	private Random ran;

	private final double elytraDamageChance = 0.1D;
	
	public EntityDamageEventListener(NetheriteElytra plugin) {
		ei = new ElytraItem(plugin);
		ran = new Random();
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		
		//Check the damage cause. Must be lava, fire or fire tick
		if(!(cause.equals(DamageCause.LAVA) || cause.equals(DamageCause.FIRE) || cause.equals(DamageCause.FIRE_TICK))) return;
		
		//Check if the Entity is a Player
		//Also check if the ElytraWearer (the Player) should be fire proof
		Entity e = event.getEntity();
		if(e instanceof Player && ConfigHandler.isElytraWearerFireProof) {
			Player p = (Player) e;
			
			//Get the ElytraObject. Can be null
			ElytraObject eo = ei.getNetheriteElytra(p.getInventory().getChestplate());
			
			//Check if the ElytraObject isn't null. Meaning it's a valid NetheriteElytra
			if(eo != null) {
				//Cancel the event, since the player should not burn
				event.setCancelled(true);
				
				//Set the remaining fire ticks to 5.
				//No point in burning the player since they wont take damage anyways
				p.setFireTicks(5);
				
				//There's a 10% chance that the NetheriteElytra will take 1 damage.
				double randomD = ran.nextDouble();
				if(randomD <= elytraDamageChance) {
					Bukkit.getPluginManager().callEvent(new PlayerItemDamageEvent(p, p.getInventory().getChestplate(), 1));
				}
			}
		}
		
		//Check if the Entity is an Item
		//Also check if the item should be fire proof
		if(e instanceof Item && ConfigHandler.isElytraItemFireProof) {
			//Get the ElytraObject. Can be null
			ElytraObject eo = ei.getNetheriteElytra(((Item) e).getItemStack());
			
			//Check if it isnt null
			//If it isnt null, cancel the event, 
			//because it is a valid NetheriteElytra
			if(eo != null) {
				event.setCancelled(true);
			}
		}
	}
}
