package nl.thedutchmc.NetheriteElytra.elytra;

import java.io.Serializable;
import java.util.Random;

public class ElytraObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id, damage;
	
	public ElytraObject(int id) {
		this.id = id;
		this.damage = 0;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public boolean shouldItemTakeDamage() {
		//Item has 50% chance of taking damage
		Random r = new Random();
		return r.nextBoolean();
	}
}
