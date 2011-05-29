/*
 * FriendList
 * Copyright (C) 2010 Celeo <celeodor at gmail dot com>
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
	
	public void onPlayerJoin(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(null);
		
		for(Player p : event.getPlayer().getServer().getOnlinePlayers())
		{
			if(Util.friendList.get(p) != null)
			{
				if(Util.friendList.get(p).contains(player))
				{
					p.sendMessage(player + " has logged out.");
				}
			}
		}
	}
		
}