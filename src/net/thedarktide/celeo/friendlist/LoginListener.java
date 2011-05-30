/*
 * FriendList
 * Copyright (C) 2011 Celeo <celeodor@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

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
		
		//load player information from config.yml
		try
		{
			ArrayList<String> allFriends = null;
			allFriends = (ArrayList<String>) Util.config.getStringList("list." + name, allFriends);
			Util.friendList.put(player.getDisplayName(), allFriends);
		}
		catch (Exception ex)
		{
			FriendList.log.info("Error with the loading of " + name + "'s friend list.");
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