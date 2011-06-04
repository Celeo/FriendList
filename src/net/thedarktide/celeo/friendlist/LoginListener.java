package net.thedarktide.celeo.friendlist;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class LoginListener extends PlayerListener {
	
	public final FriendList plugin;
	
	public LoginListener(FriendList instance) {
		plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getDisplayName();
		event.setJoinMessage(null);
		
		//load friends from config.yml
		try
		{
			ArrayList<String> allFriends = null;
			allFriends = (ArrayList<String>) Util.config.getStringList("friend." + name, allFriends);
			Util.friendList.put(player.getDisplayName(), allFriends);
		}
		catch (Exception ex)
		{
			FriendList.log.info("Error with the loading of " + name + "'s friend list.");
		}
		
		//load enemies from config.yml
		try
		{
			ArrayList<String> allEnemies = null;
			allEnemies = (ArrayList<String>) Util.config.getStringList("enemy." + name, allEnemies);
			Util.enemyList.put(player.getDisplayName(), allEnemies);
		}
		catch (Exception ex)
		{
			FriendList.log.info("Error with the loading of " + name + "'s enemy list.");
		}
		
		//tell everyone who has this person in their friend list that he/she logged in
		for(Player playersOnline : event.getPlayer().getServer().getOnlinePlayers())
		{
			if(Util.friendList.get(playersOnline.getDisplayName()) != null)
			{
				if(Util.friendList.get(playersOnline.getDisplayName()).contains(player.getDisplayName()))
				{
					playersOnline.sendMessage(player.getDisplayName() + " has logged in.");
				}
			}
		}
		
	}
		
}