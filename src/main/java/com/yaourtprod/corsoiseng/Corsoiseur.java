package com.yaourtprod.corsoiseng;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Corsoiseur {
	private final String pseudo;
	private int combienkilest = 1;
	private int pietra = 0;
	private int terrine = 0;
	
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
}
