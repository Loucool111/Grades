package ch.reaamz.grades;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.reaamz.grades.command.GradeCommand;
import ch.reaamz.grades.completer.GradeCompleter;
import ch.reaamz.grades.listener.GradeJoinListener;

public class Grades extends JavaPlugin
{
	public static Plugin instance;
	
	@Override
	public void onEnable()
	{
		instance = this;
		
		saveConfig();
		
		GradeManager.setup();
		
		GradeCommand grade = new GradeCommand();
		getCommand("grade").setExecutor(grade);
		GradeCompleter completer = new GradeCompleter();
		getCommand("grade").setTabCompleter(completer);
		
		Bukkit.getPluginManager().registerEvents(new GradeJoinListener(), this);
		
		Utils.logInfo("Grades v1 initialisé.");
	}
	
	@Override
	public void onDisable()
	{
		saveConfig();
		
		GradeManager.saveGrades();
		
		Utils.logInfo("Grades v1 sauvegardé.");
	}
}
