package org.luca.VampireS.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.luca.VampireS.VampireCreator;
import org.luca.VampireS.VampireSPlugin;
import org.luca.VampireS.events.VampireBitingPlayerEvent;

import java.util.Random;

public class VampireTransformingPlayer implements Listener{
	
	private final VampireSPlugin plugin;

	//VampireBitingEvent
	public VampireTransformingPlayer(VampireSPlugin plugin) {
		this.plugin = plugin;
	}

	
	@EventHandler
	public void onVampirePlayerBitingNonVampire (EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player  && e.getEntity() instanceof Player) {
			Player p = (Player) e.getDamager();
			Player damageTaker = (Player) e.getEntity();
			if(e.getCause() == DamageCause.ENTITY_ATTACK 
					&& plugin.getVampires().contains(p.getUniqueId()) 
					&& !plugin.getVampires().contains(damageTaker.getUniqueId())
					&& p.getInventory().getItemInMainHand().getType() == Material.AIR) {
				Random random = new Random();
				int result = random.nextInt((250 - 1) + 1);
				if(result == 1) {
					VampireBitingPlayerEvent event = new VampireBitingPlayerEvent(p, damageTaker);
					Bukkit.getPluginManager().callEvent(event);
					if(event.isCancelled()) {
						return;
					}
					p.getWorld().playSound(p.getLocation(), "vampires.vampirebiteplayer", SoundCategory.MASTER, 1.0F, 1.0F);
					VampireCreator.setVampire(plugin, damageTaker);
				}
			}
			
			
		}
	}

}
