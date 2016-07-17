package ch.reaamz.grades.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.reaamz.grades.GradeManager;

public class GradeJoinListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e)
	{
		Player p = e.getPlayer();

		if (GradeManager.playerGrades.containsKey(p.getUniqueId()))
		{
			String grade = GradeManager.playerGrades.get(p.getUniqueId());
			
			grade = ChatColor.translateAlternateColorCodes('$', grade);
			
			p.setCustomName(ChatColor.WHITE + "[" + grade + ChatColor.WHITE + "] " + ChatColor.WHITE + p.getCustomName());
			p.setDisplayName(ChatColor.WHITE + "[" + grade + ChatColor.WHITE + "] " + ChatColor.WHITE + p.getDisplayName());
			p.setPlayerListName(ChatColor.WHITE + "[" + grade + ChatColor.WHITE + "] " + ChatColor.WHITE + p.getPlayerListName());
		}
	}
}
