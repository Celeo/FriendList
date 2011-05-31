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
import org.bukkit.plugin.PluginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Server;

import java.util.ArrayList;
import java.util.logging.Logger;

public class FriendList extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public LoginListener loginListener = new LoginListener(this);
	public QuitListener quitListener = new QuitListener(this);
	
	public static ChatColor cgreen = ChatColor.GREEN;
	public static ChatColor cwhite = ChatColor.WHITE;
	public static ChatColor cred = ChatColor.RED;
	
	@Override
	public void onDisable() {
		log.info("[Friend List] plugin <disabled>");
		
		//save master list
		for(Player player : getServer().getOnlinePlayers())
		{
			Util.config.setProperty("friend." + player.getDisplayName(), Util.friendList.get(player.getDisplayName()));
			Util.config.setProperty("enemy." + player.getDisplayName(), Util.enemyList.get(player.getDisplayName()));
		}
		Util.config.save();
	}
	
	@Override
	public void onEnable() {
		log.info("[Friend List] plugin <enabled>");
		Util.load(this);
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_JOIN, this.loginListener, Event.Priority.Normal, this);
		mngr.registerEvent(Event.Type.PLAYER_QUIT, this.quitListener, Event.Priority.Normal, this);
	}
	
	public boolean isPlayerOnline(Player player, Server server) {
		for(Player p : server.getOnlinePlayers())
		{
			if(p.equals(player))
				return true;
			break;
		}
		return false;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Server server = sender.getServer();
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			
			// === Friends ===
			if((commandLabel.equalsIgnoreCase("friend") || commandLabel.equalsIgnoreCase("friendlist")
					|| commandLabel.equalsIgnoreCase("friends")) && args.length >= 0)
			{
				//-see / -view / -show
				if(args[0].equalsIgnoreCase("-see") || args[0].equalsIgnoreCase("-view") || args[0].equalsIgnoreCase("-show") || args[0].equalsIgnoreCase("-list"))
				{
					if(Util.friendList.get(player.getDisplayName()) != null)
					{
						String list = cgreen + "Friend list: " + cwhite;
						for(String str : Util.friendList.get(player.getDisplayName()))
						{
							if(isPlayerOnline(server.getPlayer(str), server))
								list += cgreen + str + " ";
							else
								list += cwhite + str + " ";
						}
						if(list != "" || list != " ")
							player.sendMessage(list);
						else
							player.sendMessage(cred + "You don't have any people in your friend list.");
					}
					else
					{
						player.sendMessage(cred + "You don't have any people in your friend list.");
					}
				}
				//-a|dd
				if(args[0].equalsIgnoreCase("-a") || args[0].equalsIgnoreCase("-add"))
				{
					if(args.length >= 1)
					{
						if(Util.enemyList.get(player.getDisplayName()).contains(args[1]))
						{
							player.sendMessage(cred + "You cannot add an enemy to your friend list.");
						}
						else
						{
							if(!Util.friendList.containsKey(player.getDisplayName()))
							{
								ArrayList<String> temp = new ArrayList<String>();
								Util.friendList.put(player.getDisplayName(), temp);
							}
							ArrayList<String> temp = Util.friendList.get(player.getDisplayName());
							ArrayList<String> playersAdded = new ArrayList<String>();
							if(temp != null)
							{
								for(int i = 1; i < args.length; i++)
							{
								temp.add(args[i]);
								playersAdded.add(args[i]);
							}
							Util.friendList.put(player.getDisplayName(), temp);
							player.sendMessage(ChatColor.GRAY + (playersAdded + " added to your friends list."));
							}
						}
					}
				}
				//-rem|ove
				if(args[0].equalsIgnoreCase("-rem") || args[0].equalsIgnoreCase("-remove"))
				{
					if(args.length >= 1)
					{
						if(!Util.friendList.containsKey(player.getDisplayName()))
						{
							ArrayList<String> temp = new ArrayList<String>();
							Util.friendList.put(player.getDisplayName(), temp);
							player.sendMessage(cred + "You do not have anyone in your friend list.");
						}
						else
						{
							ArrayList<String> temp = Util.friendList.get(player.getDisplayName());
							ArrayList<String> playersRemoved = new ArrayList<String>();
							if(temp != null)
							{
								for(int i = 1; i < args.length; i++)
							{
								temp.remove(args[i]);
								playersRemoved.add(args[i]);
							}
							Util.friendList.put(player.getDisplayName(), temp);
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
						Util.friendList.put(player.getDisplayName(), null);
						player.sendMessage(cred + "Your friend list has been cleared.");
					}
					catch (Exception ex)
					{
						player.sendMessage(cred + "You do not have anyone in your friend list.");
					}
				}
			}
			
			//=== Enemies ===
			if((commandLabel.equalsIgnoreCase("enemy") || commandLabel.equalsIgnoreCase("enemylist")
					|| commandLabel.equalsIgnoreCase("enemies")) && args.length >= 0)
			{
				//-see / -view / -show
				if(args[0].equalsIgnoreCase("-see") || args[0].equalsIgnoreCase("-view") || args[0].equalsIgnoreCase("-show") || args[0].equalsIgnoreCase("-list"))
				{
					if(Util.enemyList.get(player.getDisplayName()) != null)
					{
						String list = cred + "Enemy list: " + cwhite;
						for(String str : Util.enemyList.get(player.getDisplayName()))
						{
							if(isPlayerOnline(server.getPlayer(str), server))
								list += cred + str + " ";
							else
								list += cwhite + str + " ";
						}
						if(list != "" || list != " ")
							player.sendMessage(list);
						else
							player.sendMessage(cred + "You don't have any people in your enemy list.");
					}
					else
					{
						player.sendMessage(cred + "You don't have any people in your enemy list.");
					}
				}
				//-a|dd
				if(args[0].equalsIgnoreCase("-a") || args[0].equalsIgnoreCase("-add"))
				{
					if(args.length >= 1)
					{
						if(Util.friendList.get(player.getDisplayName()).contains(args[1]))
						{
							player.sendMessage(cred + "You cannot add an friend to your enemy list.");
						}
						else
						{
							if(!Util.enemyList.containsKey(player.getDisplayName()))
							{
								ArrayList<String> temp = new ArrayList<String>();
								Util.enemyList.put(player.getDisplayName(), temp);
							}
							ArrayList<String> temp = Util.enemyList.get(player.getDisplayName());
							ArrayList<String> playersAdded = new ArrayList<String>();
							if(temp != null)
							{
								for(int i = 1; i < args.length; i++)
							{
								temp.add(args[i]);
								playersAdded.add(args[i]);
							}
							Util.enemyList.put(player.getDisplayName(), temp);
							player.sendMessage(ChatColor.GRAY + (playersAdded + " added to your enemy list."));
							}
						}
					}
				}
				//-rem|ove
				if(args[0].equalsIgnoreCase("-rem") || args[0].equalsIgnoreCase("-remove"))
				{
					if(args.length >= 1)
					{
						if(!Util.enemyList.containsKey(player.getDisplayName()))
						{
							ArrayList<String> temp = new ArrayList<String>();
							Util.enemyList.put(player.getDisplayName(), temp);
							player.sendMessage(cred + "You do not have anyone in your enemy list.");
						}
						else
						{
							ArrayList<String> temp = Util.enemyList.get(player.getDisplayName());
							ArrayList<String> playersRemoved = new ArrayList<String>();
							if(temp != null)
							{
								for(int i = 1; i < args.length; i++)
							{
								temp.remove(args[i]);
								playersRemoved.add(args[i]);
							}
							Util.enemyList.put(player.getDisplayName(), temp);
							player.sendMessage(ChatColor.GRAY + (playersRemoved + " removed to your enemy list."));
							}
						}
					}
				}
				//-clear
				if(args[0].equalsIgnoreCase("-clear"))
				{
					try
					{
						Util.enemyList.put(player.getDisplayName(), null);
						player.sendMessage(cred + "Your enemy list has been cleared.");
					}
					catch (Exception ex)
					{
						player.sendMessage(cred + "You do not have anyone in your enemy list.");
					}
				}
			}
			
		}
		return true;
	}

}