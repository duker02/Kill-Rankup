package me.dukerhd.killrankup;

import me.dukerhd.killrankup.listeners.PlayerListener;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class KillRankup extends JavaPlugin {
	
	private static KillRankup instance;
	
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
		if (!getConfig().getString("version").equals(getDescription().getVersion())) {
			getConfig().set("version", getDescription().getVersion());
			getConfig().set("entities.players", true);
			getConfig().set("entities.zombies", false);
			getConfig().set("entities.skeletons", false);
			getConfig().set("entities.creepers", false);
			getConfig().set("entities.spiders", false);
			getConfig().set("entities.iron-golems", false);
			getConfig().set("entities.cave-spiders", false);
			getConfig().set("entities.bats", false);
			getConfig().set("entities.silverfish", false);
			getConfig().set("entities.slimes", false);
			getConfig().set("entities.witches", false);
			getConfig().set("entities.pig-zombies", false);
			getConfig().set("entities.blazes", false);
			getConfig().set("entities.ghasts", false);
			getConfig().set("entities.magma-cubes", false);
			getConfig().set("entities.withers", false);
			getConfig().set("entities.giant-zombies", false);
			getConfig().set("entities.enderdragons", false);
			getConfig().set("entities.animals", false);
			saveConfig();
		} else if (getConfig().getString("version").equals(null)) {
			getConfig().set("version", getDescription().getVersion());
			saveConfig();
		}
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.prefix"));
		congratulations_message = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.congratulations"));
	}
	
	public void onDisable() {
		// do nothing
	}
	
	public static KillRankup getInstance() {
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
