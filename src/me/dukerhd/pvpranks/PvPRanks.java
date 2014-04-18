package me.dukerhd.pvpranks;

import me.dukerhd.pvpranks.listeners.PlayerListener;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPRanks extends JavaPlugin {
	
	private static PvPRanks instance;
	
	private String prefix;
	private String congratulations_message;
	
	public static Permission perms = null;
	
	public void onEnable() {
		instance = this;
		if (!setupPermissions()) {
			System.out.println("[" + getDescription().getName() + "] Disabled due to Vault not being installed.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.prefix"));
		congratulations_message = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.congratulations"));
	}
	
	public void onDisable() {
		// do nothing
	}
	
	public static PvPRanks getInstance() {
		return instance;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getMessage() {
		return congratulations_message;
	}
	
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

}
