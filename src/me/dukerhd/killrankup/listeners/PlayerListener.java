package me.dukerhd.killrankup.listeners;

import me.dukerhd.killrankup.KillRankup;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
	
	private KillRankup plugin = KillRankup.getInstance();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!addPlayer(p)) setName(p);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Entity ent = e.getEntity();
		if (ent instanceof Player) {
			Player p = (Player) ent;
			if (p.getKiller() != null) {
				Player k = p.getKiller();
				addKills(k, 1);
				for (String s : plugin.getConfig().getConfigurationSection("ranks").getKeys(false)) {
					if (plugin.getConfig().getInt("ranks." + s + ".kills") == getKills(k)) {
						int kills = plugin.getConfig().getInt("ranks." + s + ".kills");
						KillRankup.perms.playerAddGroup(k, s);
						if (plugin.getConfig().getBoolean("ranks." + s + ".message")) k.sendMessage(plugin.getPrefix() + plugin.getMessage().replace("%kills%", Integer.toString(kills)).replace("%rank%", s));
					}
				}
			}
		}
	}
	
	public boolean addPlayer(Player p) {
		if (!plugin.getConfig().getConfigurationSection("players").contains(p.getUniqueId().toString())) {
			plugin.getConfig().set("players." + p.getUniqueId().toString() + ".name", p.getName());
			plugin.getConfig().set("players." + p.getUniqueId().toString() + ".kills", 0);
			plugin.saveConfig();
			return true;
		}
		return false;
	}
	
	public int getKills(Player p) {
		if (plugin.getConfig().getConfigurationSection("players").contains(p.getUniqueId().toString()))
			return plugin.getConfig().getInt("players." + p.getUniqueId().toString() + ".kills");
		return 0;
	}
	
	public void setName(Player p) {
		if (plugin.getConfig().getConfigurationSection("players").contains(p.getUniqueId().toString())) {
			plugin.getConfig().set("players." + p.getUniqueId().toString() + ".name", p.getName());
			plugin.saveConfig();
		}
	}
	
	public void addKills(Player p, int amount) {
		if (plugin.getConfig().getConfigurationSection("players").contains(p.getUniqueId().toString())) {
			plugin.getConfig().set("players." + p.getUniqueId().toString() + ".kills", getKills(p) + amount);
			plugin.saveConfig();
		}
	}

}
