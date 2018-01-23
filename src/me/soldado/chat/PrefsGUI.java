package me.soldado.chat;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mkremins.fanciful.FancyMessage;

public class PrefsGUI implements Listener {

	public Main plugin;
	
	public PrefsGUI(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void clicarGui(InventoryClickEvent event){
		Player p = (Player) event.getWhoClicked();
		if(event.getInventory().getTitle().contains("Preferências do Chat")){
			int click = event.getSlot();
			if(click == 1){
				alternarTell(p);
				p.closeInventory();
			}else if(click == 3){
				if(p.hasPermission("chat.destaque")){
					alternarDestaque(p);
					p.closeInventory();
				}else p.sendMessage(plugin.msg.semperm);
			}else if(click == 5){
				alternarGlobal(p);
				p.closeInventory();
			}else if(click == 7){
				mostrarBloqueados(p);
				p.closeInventory();
			}
			event.setCancelled(true);
		}
	}
	
	public void abrirPrefs(Player p){
		
		Prefs pr = plugin.prf.getPrefs(p);
		
		Inventory gui = Bukkit.getServer().createInventory(null, 9, "§aPreferências do Chat");
		
		String alternar = "§7§oClique para alternar";
		
		boolean t = pr.isTell();
		ItemStack tell = new ItemStack(Material.INK_SACK, 1, (byte) getData(t));
		ItemMeta tellm = tell.getItemMeta();
		if(t) tellm.setDisplayName("§aMensagens Privadas - Ligadas");
		else tellm.setDisplayName("§cMensagens Privadas - Desligadas");
		tellm.setLore(Arrays.asList(alternar));
		tell.setItemMeta(tellm);
		
		ItemStack destac = new ItemStack(Material.INK_SACK, 1, (byte) getData(pr.isDestac()));
		ItemMeta destacm = destac.getItemMeta();
		if(t) destacm.setDisplayName("§aMensagens Destacadas - Ligadas");
		else destacm.setDisplayName("§cMensagens Destacadas - Desligadas");
		destacm.setLore(Arrays.asList(alternar));
		destac.setItemMeta(destacm);
		
		ItemStack global = new ItemStack(Material.INK_SACK, 1, (byte) getData(pr.isGlobal()));
		ItemMeta globalm = global.getItemMeta();
		if(t) globalm.setDisplayName("§aMensagens Globais - Ligadas");
		else globalm.setDisplayName("§cMensagens Globais - Desligadas");
		globalm.setLore(Arrays.asList(alternar));
		global.setItemMeta(globalm);
		
		ItemStack block = new ItemStack(Material.BARRIER);
		ItemMeta blockm = block.getItemMeta();
		blockm.setDisplayName("§cClique para ver os jogadores bloqueados.");
		block.setItemMeta(blockm);

		gui.setItem(1, tell);
		gui.setItem(3, destac);
		gui.setItem(5, global);
		gui.setItem(7, block);
		
		p.openInventory(gui);
	}
	
	public int getData(boolean aux){
		if(aux){
			return 10;
		}else return 8;
	}
	
	public void alternarTell(Player p){
		Prefs pr = plugin.prf.getPrefs(p);
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
		if(pr.isTell()){
			pr.setTell(false);
			Bukkit.getServer().dispatchCommand(p, "tellchatsoldadooff");
			//p.sendMessage(ChatColor.RED + "Mensagens Privadas - Desligadas");
		}else{
			pr.setTell(true);
			Bukkit.getServer().dispatchCommand(p, "tellchatsoldadoon");
			//p.sendMessage(ChatColor.GREEN + "Mensagens Privadas - Ligadas");
		}
	}
	
	public void alternarDestaque(Player p){
		Prefs pr = plugin.prf.getPrefs(p);
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
		if(pr.isDestac()){
			pr.setDestac(false);
			p.sendMessage(ChatColor.RED + "Mensagens Destacadas - Desligadas");
		}else{
			pr.setDestac(true);
			p.sendMessage(ChatColor.GREEN + "Mensagens Destacadas - Ligadas");
		}
	}
	
	public void alternarGlobal(Player p){
		Prefs pr = plugin.prf.getPrefs(p);
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
		if(pr.isGlobal()){
			pr.setGlobal(false);
			p.sendMessage(ChatColor.RED + "Mensagens Globais - Desligadas");
		}else{
			pr.setGlobal(true);
			p.sendMessage(ChatColor.GREEN + "Mensagens Globais - Ligadas");
		}
	}
	
	public void mostrarBloqueados(Player p){
		Prefs pr = plugin.prf.getPrefs(p);
		List<String> blocks = pr.getBlocks();
		if(blocks.size() == 0){
			p.sendMessage(plugin.msg.semblocks);
		}else{
			p.sendMessage(ChatColor.RED + "Jogadores Bloqueados");
			for(String s : blocks){
				new FancyMessage(s)
				.color(ChatColor.RED)
				.tooltip(ChatColor.GOLD + "Clique para desbloquear")
				.suggest("/desbloquear " + s)
				.send(p);
			}
		}
	}
}
