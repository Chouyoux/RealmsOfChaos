package io.github.chouyoux.realmsofchaos.data_handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YMLFiles {
	
	public static HashMap<String, File> files;
	public static HashMap<String, FileConfiguration> customFiles;
	
	public YMLFiles() {
		files = new HashMap<String, File>();
		customFiles = new HashMap<String, FileConfiguration>();
	}
	
	public static void setup(String fileName) {
		File file = new File(Bukkit.getServer().getPluginManager().getPlugin("RealmsOfChaos").getDataFolder(), fileName+".yml");
		files.put(fileName, file);
		
		if (!file.exists()) {
			try{
				file.createNewFile();
			}catch (IOException e) {
				System.out.println("Couldn't create file "+fileName);
			}
		}
		FileConfiguration customFile = YamlConfiguration.loadConfiguration(file);
		customFiles.put(fileName, customFile);
	}
	
	public static FileConfiguration get(String fileName) {
		return customFiles.get(fileName);
	}
	
	public static void save(String fileName) {
		try{
			customFiles.get(fileName).save(files.get(fileName));
		}catch (IOException e){
			System.out.println("Couldn't save file "+fileName);
		}
	}
	
	public static void reload(String fileName) {
		FileConfiguration newCustomFile = YamlConfiguration.loadConfiguration(files.get(fileName));
		customFiles.put(fileName, newCustomFile);
	}

}