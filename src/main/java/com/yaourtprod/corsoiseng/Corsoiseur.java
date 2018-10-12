package com.yaourtprod.corsoiseng;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Corsoiseur {
	private final String pseudo;
	private int combienkilest = 1;
	private int pietra = 0;
	private int terrine = 0;
	private int burger = 0;
	private int tiramisu = 0;
	
	public Corsoiseur(final String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getPseudo() {
		return pseudo;
	}

	@JsonProperty("count")
	public int getCombienKilEst() {
		return combienkilest;
	}

	@JsonProperty("pietra")
	public int getCombienKilNenveuLaPietra() {
		return pietra;
	}

	@JsonProperty("terrine")
	public int getCombienKilNenveuLaTerrine() {
		return terrine;
	}

	@JsonProperty("burger")
	public int getCombienKilNenveuLeBurger() {
		return burger;
	}

	@JsonProperty("tiramisu")
	public int getCombienKilNenveuLeTiramisu() {
		return tiramisu;
	}

	public void incCombienKilest() {
		this.combienkilest++;
	}

	public void decCombienKilest() {
		if(this.combienkilest > 0)
			this.combienkilest--;
	}
	
	public void incPietra() {
		this.pietra++;
	}

	public void decPietra() {
		if(this.pietra > 0)
			this.pietra--;
	}

	public void incTerrine() {
		this.terrine++;
	}
	
	public void decTerrine() {
		if(this.terrine > 0)
			this.terrine--;
	}

	public void incBurger() {
		this.burger++;
	}

	public void decBurger() {
		if(this.burger > 0)
			this.burger--;
	}

	public void incTiramisu() {
		this.tiramisu++;
	}

	public void decTiramisu() {
		if(this.tiramisu > 0)
			this.tiramisu--;
	}
}
