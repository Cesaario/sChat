package me.soldado.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.maxgamer.maxbans.MaxBans;
import org.maxgamer.maxbans.banmanager.Mute;

import mkremins.fanciful.FancyMessage;

public class Chats implements Listener {

	public Main plugin;
	
	public Chats(Main plugin)
	{
		this.plugin = plugin;
	}
	
	ArrayList<Player> delayglobal = new ArrayList<Player>();
	ArrayList<Player> mutados = new ArrayList<Player>();
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent event){
		if(!plugin.core.travado.get(Tipo.LOCAL)){
			Player p = event.getPlayer();
			String msg = event.getMessage();
			if(plugin.core.global.contains(p)){
				enviarMensagemGlobal(p, msg);
				event.getRecipients().clear();
				return;
			}
			if(mutado(p)){
				p.sendMessage(plugin.msg.estamutado);
				event.getRecipients().clear();
				return;
			}
			Prefs prp = plugin.prf.getPrefs(p);
			boolean destac = prp.isDestac();
			for(Player a : getJogadoresNoRaio(p)){
				String nm = "";
				Prefs pra = plugin.prf.getPrefs(a);
				
				if(pra.getBlocks().contains(p.getName())) continue;
				if(prp.getBlocks().contains(a.getName())) continue;
				
				if(plugin.core.checkCit(a, msg)){
					//nm = msg.replace(a.getName(), "§b@" + a.getName() + "§r");
					plugin.core.citar(a, p);
				}
				nm = msg;
				if(getJogadoresNoRaio(p).size() <= 1) p.sendMessage(plugin.msg.ninguemouviu);
				FancyMessage fmsg = plugin.core.mensagemChat(p, nm, Tipo.LOCAL);
				if(destac) a.sendMessage("");
				fmsg.send(a);
				if(destac) a.sendMessage("");
			}
		}else event.getPlayer().sendMessage(plugin.msg.chattravado);
		event.getRecipients().clear();
	}
	
	public void enviarMensagemGlobal(Player p, String msg){
		if(!plugin.core.travado.get(Tipo.GLOBAL)){
			if(!delayglobal.contains(p)){
				if(mutado(p)){
					p.sendMessage(plugin.msg.estamutado);
					return;
				}
				Prefs prp = plugin.prf.getPrefs(p);
				boolean destac = prp.isDestac();
				for(Player a : Bukkit.getServer().getOnlinePlayers()){
					String nm = "";
					
					Prefs pr = plugin.prf.getPrefs(a);
					if(!pr.isGlobal()) continue;
					
					if(pr.getBlocks().contains(p.getName())) continue;
					if(prp.getBlocks().contains(a.getName())) continue;
					
					if(plugin.core.checkCit(a, msg)){
						//nm = msg.replace(a.getName(), "§b@" + a.getName() + "§r");
						plugin.core.citar(a, p);
					}
					nm = msg;
					FancyMessage fmsg = plugin.core.mensagemChat(p, nm, Tipo.GLOBAL);
					if(destac) a.sendMessage("");
					fmsg.send(a);
					if(destac) a.sendMessage("");
				}
				if(!p.hasPermission("chat.global.semdelay")){
					delayglobal.add(p);
					new BukkitRunnable()
					{
						public void run()
						{
							delayglobal.remove(p);
						}
					}.runTaskLater(plugin, plugin.cfg.delayglobal * 20L);
				}
			}else p.sendMessage(plugin.msg.delay.replace("%segundos%", plugin.cfg.delayglobal+""));
		}else p.sendMessage(plugin.msg.chattravado);
	}
	
	List<Player> getJogadoresNoRaio(Player p){
		List<Player> jogadores = new ArrayList<Player>();
		
		for(Player a : Bukkit.getServer().getOnlinePlayers()){
			if(a.getLocation().getWorld().equals(p.getLocation().getWorld())){
				Location locp = p.getLocation();
				Location loca = a.getLocation();
				double dist = locp.distance(loca);
				if(dist < plugin.cfg.raiolocal) jogadores.add(a);
			}
		}
		return jogadores;
	}
	
	public boolean mutado(Player p){
		Mute mt = MaxBans.instance.getBanManager().getMute(p.getName());
		if(mt == null) return false;
		else return true;
	}
	
	public void mutar(Player p){
		if(!mutados.contains(p)) mutados.add(p);
	}
	public void desmutar(Player p){
		if(mutados.contains(p)) mutados.remove(p);
	}
}
