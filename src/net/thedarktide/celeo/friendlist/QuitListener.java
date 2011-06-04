package net.thedarktide.celeo.friendlist;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends PlayerListener {
	
	public final FriendList plugin;
	
	public QuitListener(FriendList instance) {
		plugin = instance;
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String name = player.getDisplayName();
		event.setQuitMessage(null);
		
		//friend list
		try
		{
			//save player's information
			Util.config.setProperty("friend." + name, Util.friendList.get(player.getDisplayName()));
			Util.config.save();
			
			//remove player from master list
			Util.friendList.remove(player.getDisplayName());
		}
		catch (Exception ex)
		{
			FriendList.log.info("Error with the saving of " + name + "'s friend list.");
		}
		
		//enemy list
		try
		{
			//save player's information
			Util.config.setProperty("enemy." + name, Util.enemyList.get(player.getDisplayName()));
			Util.config.save();
			
			//remove player from master list
			Util.enemyList.remove(player.getDisplayName());
		}
		catch (Exception ex)
		{
			FriendList.log.info("Error with the saving of " + name + "'s enemy list.");
		}
		
		//tell everyone who has this person in their friend list that he/she logged out
		for(Player playersOnline : event.getPlayer().getServer().getOnlinePlayers())
		{
			if(Util.friendList.get(playersOnline.getDisplayName()) != null)
			{
				if(Util.friendList.get(playersOnline.getDisplayName()).contains(player.getDisplayName()))
				{
					playersOnline.sendMessage(player.getDisplayName() + " has logged out.");
				}
			}
		}
	}
	
}