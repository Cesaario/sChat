package me.soldado.chat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

public class Main extends JavaPlugin {
	
	Core core;
	Comandos cmd;
	Config cfg;
	Mensagens msg;
	Chats cht;
	TellChat tell;
	ClanChat cch;
	StaffChat stf;
	CorePrefs prf;
	PrefsGUI pgui;
	Tag tag;
	
	public SimpleClans sc;
	public Economy econ = null;
	
	public void onEnable(){
		core = new Core(this);
		cmd = new Comandos(this);
		cfg = new Config(this);
		msg = new Mensagens(this);
		cht = new Chats(this);
		tell = new TellChat(this);
		cch = new ClanChat(this);
		stf = new StaffChat(this);
		prf = new CorePrefs(this);
		pgui = new PrefsGUI(this);
		tag = new Tag(this);
		
		cfg.iniciarConfig();
		msg.iniciarMensagens();
		cmd.iniciarComandos();
		prf.iniciarPrefs();
		core.setValoresTravados();
		tag.iniciarTags();

		Bukkit.getServer().getPluginManager().registerEvents(cht, this);
		Bukkit.getServer().getPluginManager().registerEvents(pgui, this);
		
		sc = (SimpleClans) Bukkit.getServer().getPluginManager().getPlugin("SimpleClans");

		this.getLogger().info("sChat ativado!!!");
		this.getLogger().info("Autor: Soldado_08");
		
		if (!iniciarEconomia()){
			getLogger().severe(
					String.format("[%s] - Desabilitado por falta do plugin Vault!", 
							new Object[] { getDescription().getName() }));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
	}
	
	public void onDisable(){
		this.getLogger().info("sChat desativado!!!");
		this.getLogger().info("Autor: Soldado_08");
		prf.salvar();
		tag.salvarTags();
	}
	
	private boolean iniciarEconomia(){
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		this.econ = ((Economy)rsp.getProvider());
		return this.econ != null;
	}
}
