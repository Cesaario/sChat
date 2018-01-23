package me.soldado.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import mkremins.fanciful.FancyMessage;

public class TellChat {

	public Main plugin;
	
	public TellChat(Main plugin)
	{
		this.plugin = plugin;
	}
	
	void enviarTell(Player p, Player a, String msg){

		if(p.equals(a)){
			
			return;
		}
		
		Prefs prp = plugin.prf.getPrefs(p);
		Prefs pra = plugin.prf.getPrefs(a);
		
		if(pra.getBlocks().contains(p.getName())){
			p.sendMessage(plugin.msg.foiblock);
			return;
		}
		if(prp.getBlocks().contains(a.getName())){
			p.sendMessage(plugin.msg.deublock);
			return;
		}
		
		FancyMessage paraemissor = getMensagemTell(a, msg, "para");
		FancyMessage parareceptor = getMensagemTell(p, msg, "de");
		paraemissor.send(p);
		parareceptor.send(a);
		a.playSound(a.getLocation(), Sound.ITEM_PICKUP, 1, 1);
		
	}
	
	FancyMessage getMensagemTell(Player p, String msg, String aux){
		
		List<String> lista = plugin.core.getInfoPlayer(p);
		lista.add("§7Clique para responder");
		
		FancyMessage fm = new FancyMessage("§8[Mensagem " + aux + " ");
		if(!aux.equals("para")) fm.tooltip("§7Clique para responder");
		fm.suggest("/tell " + p.getName() + " ")
		.then("§8" + p.getName())
		.tooltip(lista)
		.suggest("/tell " + p.getName() + " ")
		.then("§8] §6" + msg);
		
		return fm;
	}
}
