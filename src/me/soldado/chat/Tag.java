package me.soldado.chat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Tag {

	public Main plugin;
	
	public Tag(Main plugin)
	{
		this.plugin = plugin;
	}
	
	File tagFile;
	FileConfiguration tag;
	
	public String getTag(Player p, int index){
		String tg = tag.getString(p.getName()+"."+index);
		if(tg == null) return " ";
		else return tg;
	}
	
	public void setTag(OfflinePlayer a, int index, String tg){
		if(tg.contains("null")) tg = null;
		tag.set(a.getName()+"."+index, tg);
	}
	
	public void salvarTags(){
		try {
			tag.save(tagFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarTags(){

		if (tagFile == null) {
			tagFile = new File(plugin.getDataFolder(), "tags.yml");
		}
		if (!tagFile.exists()) {
			plugin.saveResource("tags.yml", false);
		}
		tag = YamlConfiguration.loadConfiguration(tagFile);
	}
	
}
