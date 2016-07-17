package ch.reaamz.grades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

public class GradeManager
{
	private final static String LIST_NAME = "PlayerGrades";
	public static HashMap<UUID, String> playerGrades = Maps.newHashMap();
	
	private static File gradeFile = new File(Grades.instance.getDataFolder(), "PlayerGrades.yml");
	private static YamlConfiguration gradeList = new YamlConfiguration();

	public static void setup()
	{
		if (!gradeFile.exists())
		{
			firstRun();
		}
		
		try
		{
			gradeList.load(gradeFile);
			
			@SuppressWarnings("unchecked")
			List<String> rawData = (List<String>) gradeList.getList(LIST_NAME);
			
			for (String entry : rawData)
			{
				String[] parts = entry.split(":");
				playerGrades.put(UUID.fromString(parts[0]), parts[1]);
			}
			
		}
		catch (IOException | InvalidConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	public static void saveGrades()
	{
		try
		{
			gradeList.save(gradeFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void firstRun()
	{
		try
		{
			gradeFile.createNewFile();
			gradeList.load(gradeFile);
			List<String> template = new ArrayList<>();
			template.add("069a79f4-44e9-4726-a5be-fca90e38aaf5:$4LORD");
			gradeList.set(LIST_NAME, template);
			gradeList.save(gradeFile);
		}
		catch (IOException | InvalidConfigurationException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setPlayerGrade(Player p, String grade)
	{
		if (playerGrades.containsKey(p.getUniqueId()))
		{
			playerGrades.remove(p.getUniqueId());
			playerGrades.put(p.getUniqueId(), grade);
		}
		else
		{
			playerGrades.put(p.getUniqueId(), grade);
		}
		
		List<String> data = getDataToWrite(playerGrades);
		gradeList.set(LIST_NAME, data);
		
		saveGrades();
	}
	
	public static boolean removePlayerGrade(Player p)
	{
		if (playerGrades.containsKey(p.getUniqueId()))
		{
			playerGrades.remove(p.getUniqueId());
		}
		else return false;
		
		List<String> data = getDataToWrite(playerGrades);
		gradeList.set(LIST_NAME, data);
		
		saveGrades();
		
		return true;
	}
	
	public static String getPlayerGrade(Player p)
	{
		if (playerGrades.containsKey(p.getUniqueId()))
		{
			return playerGrades.get(p.getUniqueId());
		}
		else return "GRADE_NOT_FOUND_ERROR";
	}
	
	private static List<String> getDataToWrite(HashMap<UUID, String> grades)
	{
		List<String> list = new ArrayList<>();
		
		for (Entry<UUID, String> entry : grades.entrySet())
		{
			list.add(entry.getKey().toString() + ":" + entry.getValue());
		}
		
		return list;
	}
}
