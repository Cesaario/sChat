package me.soldado.chat;

import java.util.List;

import org.bukkit.OfflinePlayer;

public class Prefs {

	OfflinePlayer p;
	boolean tell;
	boolean destac;
	boolean global;
	List<String> blocks;
	
	public Prefs(OfflinePlayer p, boolean tell, boolean destac, boolean global, List<String> blocks){
		this.p = p;
		this.tell = tell;
		this.destac = destac;
		this.global = global;
		this.blocks = blocks;
	}

	public OfflinePlayer getP() {
		return p;
	}

	public void setP(OfflinePlayer p) {
		this.p = p;
	}

	public boolean isTell() {
		return tell;
	}

	public void setTell(boolean tell) {
		this.tell = tell;
	}

	public boolean isDestac() {
		return destac;
	}

	public void setDestac(boolean destac) {
		this.destac = destac;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public List<String> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<String> blocks) {
		this.blocks = blocks;
	}
	
}
