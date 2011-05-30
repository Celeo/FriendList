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
		String name = player.getName();
		event.setQuitMessage(null);
		
		try
		{
			//save player's information
			Util.config.setProperty("list." + name, Util.friendList.get(player));
			Util.config.save();
			
			//remove player from master list
			Util.friendList.remove(player.getName());
		}
		catch (Exception ex)
		{
			FriendList.log.info("Error with the saving of " + name + "'s friend list.");
		}
		
		//tell everyone who has this person in their friend list that he/she logged out
		for(Player playersOnline : event.getPlayer().getServer().getOnlinePlayers())
		{
			if(Util.friendList.get(playersOnline) != null)
			{
				if(Util.friendList.get(playersOnline).contains(player))
				{
					playersOnline.sendMessage(player + " has logged out.");
				}
			}
		}
	}
	
}