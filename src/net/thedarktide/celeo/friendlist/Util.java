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

import java.util.ArrayList;
import java.util.HashMap;

public class Util {
	
	public final FriendList plugin;
	
	public static HashMap<Player, ArrayList<String>> friendList = new HashMap<Player, ArrayList<String>>();
	
	public Util(FriendList instance){
		plugin = instance;
	}
	
}
