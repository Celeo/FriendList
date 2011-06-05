package net.thedarktide.celeo.friendlist;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class DamageListener extends EntityListener {
	
	Entity attacker = null;
	Entity defender = null;
	Player p1 = null;
	Player p2 = null;
	
	public final FriendList plugin;
	
	public DamageListener(FriendList instance) {
		plugin = instance;
	}
	
	public void onEntityDamage(EntityDamageEvent event) {
		if(event instanceof EntityDamageByEntityEvent && Util.friendlyFireBlock == true)
		{
			attacker = ((EntityDamageByEntityEvent)event).getDamager();
			defender = event.getEntity();
			
			FriendList.log.info(attacker.toString() + "|" + defender.toString());
			
			if(attacker instanceof Player && defender instanceof Player)
			{
				p1 = (Player)attacker;
				p2 = (Player)defender;
				if(Util.friendList.get(p1.getDisplayName()).contains(p2.getDisplayName()))
				{
					event.setCancelled(true);
					p1.sendMessage(FriendList.cred + "You cannot attack a friend.");
				}
			}
		}
	}
	
}