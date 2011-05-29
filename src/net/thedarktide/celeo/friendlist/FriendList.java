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

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FriendList extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	protected static PermissionHandler Permissions = null;
	
	public LoginListener loginListener = new LoginListener(this);
	public QuitListener quitListener = new QuitListener(this);
	
	@Override
	public void onDisable() {
		log.info("[Friend List] plugin <disabled>");
	}

	@Override
	public void onEnable() {
		log.info("[Friend List] plugin <enabled>");
		setupPermissions();
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_JOIN, this.loginListener, Event.Priority.Normal, this);
		mngr.registerEvent(Event.Type.PLAYER_QUIT, this.quitListener, Event.Priority.Normal, this);
	}
	
	public void setupPermissions(){
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
	
	public void sendMessage(String[] recepients){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(commandLabel.equalsIgnoreCase("friendlist") && args.length >= 0)
			{
				if(args[0].equalsIgnoreCase("-see") || args[0].equalsIgnoreCase("-view"))
				{
					if(Util.friendList.get(player) != null)
					{
						String list = null;
						for(String str : Util.friendList.get(player))
						{
							list += str + " ";
						}
						if(list != "")
							player.sendMessage(list);
					}
					else
					{
						player.sendMessage("You don't have any people in your friend list.");
					}
				}
				
				if(args[0].equalsIgnoreCase("-a") || args[0].equalsIgnoreCase("-add"))
				{
					if(args.length >= 1)
					{
						ArrayList<String> temp = Util.friendList.get(player);
						for(int i = 3; i < args.length; i++)
						{
							temp.add(args[i]);
						}
						Util.friendList.put(player, temp);
					}
				}
				
				if(args[0].equalsIgnoreCase("-rem") || args[0].equalsIgnoreCase("-remove"))
				{
					if(args.length >= 1)
					{
						ArrayList<String> temp = Util.friendList.get(player);
						for(int i = 3; i < args.length; i++)
						{
							temp.remove(args[i]);
						}
					}
				}
				if(args[0].equalsIgnoreCase("-clear") && args.length == 1)
				{
					if(args[1].equalsIgnoreCase("-yes")) //-yes is simply an extra conformation from the user 
					{
						ArrayList<String> temp = null;
						try
						{
							Util.friendList.put(player, temp);
						}
						catch (Exception ex)
						{
							player.sendMessage("Could not process your command, please try again or submit a Redmine entry");
						}
					}
				}
			}
		}
		return true;
	}

}