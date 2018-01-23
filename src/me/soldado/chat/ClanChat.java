package me.soldado.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import mkremins.fanciful.FancyMessage;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;

public class ClanChat {

	public Main plugin;
	
	public ClanChat(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public void enviarMensagemClan(Player p, String msg){
		if(!plugin.core.travado.get(Tipo.CLAN)){
			Clan c = plugin.core.getClan(p);
			if(c != null){
				for(ClanPlayer cpl : c.getOnlineMembers()){
					String nm = "";
					Player a = cpl.toPlayer();
					if(plugin.core.checkCit(a, msg)){
						nm = msg.replace(a.getName(), "§b@" + a.getName() + "§r");
						plugin.core.citar(a, p);
					}else nm = msg;
					FancyMessage fmsg = plugin.core.mensagemChat(p, nm, Tipo.CLAN);
					fmsg.send(a);
				}aliados(p);
			}else p.sendMessage(plugin.msg.semfac);
		}else p.sendMessage(plugin.msg.chattravado);
	}
	
	public void enviarMensagemAliados(Player p, String msg){
		if(!plugin.core.travado.get(Tipo.ALIADOS)){
			Clan c = plugin.core.getClan(p);
			if(c != null){
				for(Player a : aliados(p)){
					String nm = "";
					if(plugin.core.checkCit(a, msg)){
						nm = msg.replace(a.getName(), "§b@" + a.getName() + "§r");
						plugin.core.citar(a, p);
					}else nm = msg;
					FancyMessage fmsg = plugin.core.mensagemChat(p, nm, Tipo.ALIADOS);
					fmsg.send(a);
				}
			}else p.sendMessage(plugin.msg.semfac);
		}else p.sendMessage(plugin.msg.chattravado);
	}
	
	List<Player> aliados(Player p){
		List<Player> al = new ArrayList<Player>();
		Clan c = plugin.core.getClan(p);
		Set<ClanPlayer> cpls = c.getAllAllyMembers();
		for(ClanPlayer cpl : cpls){
			Player a = cpl.toPlayer();
			al.add(a);
		}
		al.add(p);
		return al;
	}
}
