package net.thedarktide.celeo.friendlist;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerListener;

public class DamageListener extends PlayerListener {
	
	Entity attacker = null;
	Entity defender = null;
	Player p1 = null;
	Player p2 = null;
	
	public final FriendList plugin;
	
	public DamageListener(FriendList instance) {
		plugin = instance;
	}
	
	public void onEntityDamage(EntityDamageEvent event) {
		if(event instanceof EntityDamageByEntityEvent)
		{
			attacker = ((EntityDamageByEntityEvent)event).getDamager();
			defender = event.getEntity();
			
			FriendList.log.info(attacker.toString() + "|" + defender.toString());
			
			if(Util.friendList.get(p1.getDisplayName()).contains(p2.getDisplayName()))
			{
				event.setCancelled(true);
			}
		}
	}
	
}
