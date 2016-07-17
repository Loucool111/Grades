package ch.reaamz.grades.completer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class GradeCompleter implements TabCompleter
{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		if (command.getName().equalsIgnoreCase("grade"))
		{
			if (args.length > 1)
			{
				Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
				
				List<String> playerNames = new ArrayList<>();
				
				for (Player p : players)
				{
					playerNames.add(p.getName());
				}
				
				return playerNames;
			}
			return Arrays.asList("set", "get", "remove");
		}
		return null;
	}
}
