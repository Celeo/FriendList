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

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {
	
	public static HashMap<String, ArrayList<String>> friendList = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ArrayList<String>> enemyList = new HashMap<String, ArrayList<String>>();
	
	public static Configuration config;
	protected static File dataFolder;
	
	public static FriendList plugin;
	
	public Util(FriendList instance) {
		plugin = instance;
	}
	
	public static void load(FriendList plugin) {
		config = plugin.getConfiguration();
		dataFolder = plugin.getDataFolder();
	}
	
}