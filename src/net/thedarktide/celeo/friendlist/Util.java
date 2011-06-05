package net.thedarktide.celeo.friendlist;

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {
	
	public static HashMap<String, ArrayList<String>> friendList = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ArrayList<String>> enemyList = new HashMap<String, ArrayList<String>>();
	
	public static boolean canTeleport = false;
	public static boolean friendlyFireBlock = false;
	
	public static Configuration config;
	protected static File dataFolder;
	
	public static FriendList plugin;
	
	public Util(FriendList instance) {
		plugin = instance;
	}
	
	public static void load(FriendList plugin) {
		config = plugin.getConfiguration();
		dataFolder = plugin.getDataFolder();
		canTeleport = config.getBoolean("teleport.", canTeleport);
		friendlyFireBlock = config.getBoolean("friendlyFire.", friendlyFireBlock);
	}
	
}