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

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import java.util.ArrayList;
import java.util.logging.Logger;

public class FriendList extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	protected static PermissionHandler Permissions = null;
	
	public LoginListener loginListener = new LoginListener(this);
	public QuitListener quitListener = new QuitListener(this);
	
	@Override
	public void onDisable() {
		log.info("[Friend List] plugin <disabled>");
		//save info
	}

	@Override
	public void onEnable() {
		log.info("[Friend List] plugin <enabled>");
		//setupPermissions();
		Util.load(this);
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_JOIN, this.loginListener, Event.Priority.Normal, this);
		mngr.registerEvent(Event.Type.PLAYER_QUIT, this.quitListener, Event.Priority.Normal, this);
	}
	
	public static void sendMsg(Player player, String message) {
		player.sendMessage(message);
	}
	
	public void setupPermissions() {
		Plugin test = getServer().getPluginManager().getPlugin("Permissions");
		if(Permissions == null)
			if(test != null)
			{
				getServer().getPluginManager().enablePlugin(test);
				Permissions = ((Permissions)test).getHandler();
			}
			else
			{
				log.info("[Friend List] requires Permissions.");
				getServer().getPluginManager().disablePlugin(this);
			}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if((commandLabel.equalsIgnoreCase("friend") || commandLabel.equalsIgnoreCase("friendlist")) && args.length >= 0)
			{
				//-see / -view / -show
				if(args[0].equalsIgnoreCase("-see") || args[0].equalsIgnoreCase("-view") || args[0].equalsIgnoreCase("show"))
				{
					if(Util.friendList.get(player) != null)
					{
						String list = "";
						for(String str : Util.friendList.get(player))
						{
							list += str + " ";
						}
						if(list != "")
							player.sendMessage(list);
					}
					else
					{
						player.sendMessage(ChatColor.RED + "You don't have any people in your friend list.");
					}
				}
				//-a|dd
				if(args[0].equalsIgnoreCase("-a") || args[0].equalsIgnoreCase("-add"))
				{
					if(args.length >= 1)
					{
						if(!Util.friendList.containsKey(player))
						{
							ArrayList<String> temp = new ArrayList<String>();
							Util.friendList.put(player, temp);
						}
						ArrayList<String> temp = Util.friendList.get(player);
						ArrayList<String> playersAdded = new ArrayList<String>();
						if(temp != null)
						{
							for(int i = 1; i < args.length; i++)
						{
							temp.add(args[i]);
							playersAdded.add(args[i]);
						}
						Util.friendList.put(player, temp);
						player.sendMessage(ChatColor.GRAY + (playersAdded + " added to your friends list."));
						}
					}
				}
				//-rem|ove
				if(args[0].equalsIgnoreCase("-rem") || args[0].equalsIgnoreCase("-remove"))
				{
					if(args.length >= 1)
					{
						if(!Util.friendList.containsKey(player))
						{
							ArrayList<String> temp = new ArrayList<String>();
							Util.friendList.put(player, temp);
							player.sendMessage(ChatColor.RED + "You do not have anyone in your friend list.");
						}
						else
						{
							ArrayList<String> temp = Util.friendList.get(player);
							ArrayList<String> playersRemoved = new ArrayList<String>();
							if(temp != null)
							{
								for(int i = 1; i < args.length; i++)
							{
								temp.remove(args[i]);
								playersRemoved.add(args[i]);
							}
							Util.friendList.put(player, temp);
							player.sendMessage(ChatColor.GRAY + (playersRemoved + " removed to your friends list."));
							}
						}
					}
				}
				//-clear
				if(args[0].equalsIgnoreCase("-clear"))
				{
					try
					{
						Util.friendList.put(player, null);
						player.sendMessage(ChatColor.RED + "Your friend list has been cleared.");
					}
					catch (Exception ex)
					{
						player.sendMessage(ChatColor.RED + "You do not have anyone in your friend list.");
					}
				}
				//-save
				if(args[0].equalsIgnoreCase("-save"))// && Permissions.has(player, "friendlist.save"))
				{
					Util.saveList();
					player.sendMessage(ChatColor.GRAY + "Friend Lists saved.");
				}
				
				//-load
				if(args[0].equalsIgnoreCase("-load"))// && Permissions.has(player, "friendlist.load"))
				{
					Util.loadList(sender.getServer());
					player.sendMessage(ChatColor.GRAY + "Friend Lists loaded.");
				}
			}
		}
		return true;
	}

}