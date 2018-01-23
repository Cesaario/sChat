package me.soldado.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import mkremins.fanciful.FancyMessage;

public class StaffChat {
	
	public Main plugin;
	
	public StaffChat(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public void enviarMensagemStaff(Player p, String msg){
		for(Player a : staff()){
			String nm = "";
			if(plugin.core.checkCit(a, msg)){
				nm = msg.replace(a.getName(), "§b@" + a.getName() + "§r");
				plugin.core.citar(a, p);
			}else nm = msg;
			FancyMessage fmsg = plugin.core.mensagemChat(p, nm, Tipo.STAFF);
			fmsg.send(a);
		}
	}

	List<Player> staff(){
		List<Player> staff = new ArrayList<Player>();
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			if(p.hasPermission("chat.staff")){
				staff.add(p);
			}
		}
		return staff;
	}
}
