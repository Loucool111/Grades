package ch.reaamz.grades.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.reaamz.grades.GradeManager;
import ch.reaamz.grades.Utils;

public class GradeCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player p = (Player) sender;

			if (command.getName().equalsIgnoreCase("grade") && p.isOp())
			{
				if (args.length >= 1)
				{
					if (args[0].equalsIgnoreCase("set"))
					{
						if (args.length >= 2)
						{
							Player target = Bukkit.getPlayer(args[1]);
							if (target != null)
							{
								if (args.length >= 3)
								{
									GradeManager.setPlayerGrade(target, args[2]);
									Utils.sendCustomMessage(p, ChatColor.GREEN + "Le grade de " + target.getName() + " est désormais '" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('$', args[2]) + ChatColor.WHITE +  "'");
									Utils.sendCustomMessage(p, ChatColor.GREEN + "Il prendra effet à la prochaine connection.");
									if (!p.getName().equalsIgnoreCase(target.getName()))
									{
										Utils.sendCustomMessage(p, ChatColor.GREEN + "Votre grade à été modifié. il prendra effet à la prochaine connection.");
									}
								}
								else
									sendHelp(p, "La sous-commande 'set' requiert un troisième arguement !");
							}
							else
								sendHelp(p, ChatColor.RED + args[1] + " n'est pas un joueur !");
						}
						else
							sendHelp(p, "La sous-commande 'set' requiert plus d'arguements !");
					}
					else if (args[0].equalsIgnoreCase("get"))
					{
						if (args.length >= 2)
						{
							String argument = args[1];
							Player pArg = null;

							try
							{
								pArg = Bukkit.getPlayer(UUID.fromString(argument));
							}
							catch (IllegalArgumentException e)
							{
								pArg = null;
							}

							if (pArg == null)
							{
								pArg = Bukkit.getPlayer(argument);
							}

							if (pArg != null)
							{
								String grade = GradeManager.getPlayerGrade(pArg);
								if (grade == "GRADE_NOT_FOUND_ERROR")
									Utils.sendCustomMessage(p, ChatColor.RED + "Le joueur " + ChatColor.AQUA + pArg.getName() + ChatColor.RED + " ne possède pas de grade !");
								else
									Utils.sendCustomMessage(p, ChatColor.GOLD + "Le joueur " + ChatColor.AQUA + pArg.getName() + ChatColor.GOLD + " possède le grade : " + ChatColor.translateAlternateColorCodes('$', grade));
							}
							else
								sendHelp(p, ChatColor.RED + args[1] + " n'est pas un joueur !");
						}
						else
							sendHelp(p, "La sous-commande 'get' requiert un second arguement !");
					}
					else if (args[0].equalsIgnoreCase("remove"))
					{
						if (args.length >= 2)
						{
							Player target = Bukkit.getPlayer(args[1]);
							if (target != null)
							{
								boolean success = GradeManager.removePlayerGrade(target);
								if (success)
									Utils.sendCustomMessage(p, ChatColor.GREEN + "Le grade du joueur " + ChatColor.AQUA + target.getName() + ChatColor.GREEN + " à été retiré avec succès.");
								else
									Utils.sendCustomMessage(p, ChatColor.RED + "Le joueur " + ChatColor.AQUA + target.getName() + ChatColor.RED + " ne possède pas de grade !");
							}
							else
								sendHelp(p, ChatColor.RED + args[1] + " n'est pas un joueur !");
						}
						else
							sendHelp(p, "La sous-commande 'remove' requiert un second arguement !");
					}
					else
						sendHelp(p, "Premier argument incorrect !");
				}
				else
					sendHelp(p, "La commande Grade requiert des arguments ! voir ci-dessous.");
			}
			return true;
		}
		return false;
	}

	private void sendHelp(Player p, String message)
	{
		Utils.sendCustomMessage(p, ChatColor.RED + message);
		Utils.sendCustomMessage(p, ChatColor.AQUA + "Synthaxe de la commande :");
		Utils.sendCustomMessage(p, ChatColor.AQUA + "/grade set <PlayerName> <Grade>");
		Utils.sendCustomMessage(p, ChatColor.AQUA + "/grade get <PlayerName | UUID>");
		Utils.sendCustomMessage(p, ChatColor.AQUA + "/grade remove <PlayerName>");
	}
}
