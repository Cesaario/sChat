package me.soldado.chat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CorePrefs {

	public Main plugin;
	
	public CorePrefs(Main plugin)
	{
		this.plugin = plugin;
	}
	
	ArrayList<Prefs> preferencias = new ArrayList<Prefs>();
	
	File prefsFile;
	FileConfiguration prefs;
	
	public Prefs getPrefs(OfflinePlayer p){
		
		for(Prefs pr : preferencias){
			if(pr == null) continue;
				if(pr.getP().equals(p)){
					return pr;
				}
		}
		List<String> bs = new ArrayList<String>();
		Prefs newpref = new Prefs(p, true, false, true, bs);
		preferencias.add(newpref);
		return newpref;
	}
	
	public void bloquear(Player p, String s){
		
		Prefs pr = getPrefs(p);
		List<String> bs = pr.getBlocks();
		if(!bs.contains(s)) bs.add(s);
		p.sendMessage(plugin.msg.bloc.replace("%jogador%", s));
		
	}
	
	public void desbloquear(Player p, String s){
		
		Prefs pr = getPrefs(p);
		List<String> blocks = pr.getBlocks();
		if(blocks.contains(s)){
			blocks.remove(s);
			pr.setBlocks(blocks);
			p.sendMessage(plugin.msg.desbloc.replace("%jogador%", s));
		}else p.sendMessage(plugin.msg.naobloc.replace("%jogador%", s));
		
	}
	
	public void carregar(){
		if(prefs.getStringList("prefs") != null){
			List<String> s = prefs.getStringList("prefs");
			for(String str : s){
				Prefs p = desserializar(str);
				preferencias.add(p);
			}
		}
	}

	public void salvar(){

		if(prefs.getStringList("prefs") != null){
			List<String> s = prefs.getStringList("prefs");
			s.clear();
			for(Prefs p : preferencias){
				if(p != null){
					String str = serializar(p);
					s.add(str);
				}
			}
			prefs.set("prefs", s);
			try {
				prefs.save(prefsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void iniciarPrefs(){

		if (prefsFile == null) {
			prefsFile = new File(plugin.getDataFolder(), "preferencias.dat");
		}
		if (!prefsFile.exists()) {
			plugin.saveResource("preferencias.dat", false);
		}
		prefs = YamlConfiguration.loadConfiguration(prefsFile);
		carregar();
	}
	
	public String serializar(Prefs p){
		String s = "";
		String nome = p.getP().getName();
		String tell = p.isTell()+"";
		String destac = p.isDestac()+"";
		String global = p.isGlobal()+"";
		String blocks = "";
		for(int i = 0; i < p.getBlocks().size(); i++){
			String bs = p.getBlocks().get(i);
			if(i == 0){
				blocks = bs;
			}else{
				blocks += ":"+bs;
			}
		}
		s = nome+";"+tell+";"+destac+";"+global+";"+blocks;
		return s;
	}
	
	public Prefs desserializar(String s){
		String[] param = s.split(";");
		if(param.length != 5) return null;
		String snome = param[0];
		String stell = param[1];
		String sdestac = param[2];
		String sglobal = param[3];
		String sblocks = param[4];
		String[] ablocks = sblocks.split(":");
		
		try{
			@SuppressWarnings("deprecation")
			OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(snome);
			boolean tell = Boolean.parseBoolean(stell);
			boolean destac = Boolean.parseBoolean(sdestac);
			boolean global = Boolean.parseBoolean(sglobal);
			List<String> blocks = new LinkedList<String>(Arrays.asList(ablocks));
			return new Prefs(p, tell, destac, global, blocks);
		}catch(Exception e){
			plugin.getLogger().info("ERRO AO CARREGAR PREFERENCIA");
			return null;
		}
	}
}
