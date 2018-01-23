package me.soldado.chat;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Mensagens {
	
	public Main plugin;
	
	public Mensagens(Main plugin)
	{
		this.plugin = plugin;
	}
	
	File msgFile;
	FileConfiguration msgs;

	String semperm;
	String chatativado;
	String chatdesativado;
	String chattravado;
	String citado;
	String ninguemouviu;
	String semfac;
	String tellativado;
	String telldesativado;
	String jogadoroff;
	String limparchat;
	String limparchatglobal;
	String naobloc;
	String bloc;
	String desbloc;
	String delay;
	String mutado;
	String desmutado;
	String estamutado;
	String semblocks;
	String autoblock;
	String foiblock;
	String deublock;
	String automsg;
	String alternouglobal;
	String alternoulocal;
	
	private void iniciarValores(){
		semperm = getString("SemPermissao");
		chatativado = getString("GlobalAtivado");
		chatdesativado = getString("GlobalDesativado");
		chattravado = getString("GlobalTravado");
		citado = getString("JogadorCitado");
		ninguemouviu = getString("NinguemOuviu");
		semfac = getString("SemClan");
		tellativado = getString("AtivouTell");
		telldesativado = getString("DesativouTell");
		jogadoroff = getString("JogadorOff");
		limparchat = getString("ChatLimpo");
		limparchatglobal = getString("ChatGlobalLimpo");
		naobloc = getString("JogadorNaoBloqueado");
		bloc = getString("JogadorBloqueado");
		desbloc = getString("JogadorDesbloqueado");
		delay = getString("DelayGlobal");
		mutado = getString("JogadorMutado");
		desmutado = getString("JogadorDesmutado");
		estamutado = getString("JogadorEstaMutado");
		semblocks = getString("SemJogadoresBloqueados");
		autoblock = getString("BloquearSiMesmo");
		foiblock = getString("VoceEstaBloqueado");
		deublock = getString("VoceBloqueou");
		automsg = getString("AutoMensagem");
		alternouglobal = getString("AlternadoGlobal");
		alternoulocal = getString("AlternadoLocal");
	}
	
	public void iniciarMensagens(){

		if (msgFile == null) {
			msgFile = new File(plugin.getDataFolder(), "mensagens.yml");
		}
		if (!msgFile.exists()) {
			plugin.saveResource("mensagens.yml", false);
		}
		msgs = YamlConfiguration.loadConfiguration(msgFile);
		iniciarValores();
	}
	
	public String getString(String s){
		return msgs.getString(s).replace("&", "§");
	}
}
