package com.yaourtprod.corsoiseng;

import java.util.Set;

public class OthersAndMe {
	final Set<Corsoiseur> others;
	final Corsoiseur me;
	
	public OthersAndMe(final Corsoiseur me, final Set<Corsoiseur> others) {
		this.me = me;
		this.others = others;
	}
	
	public Corsoiseur getMe() {
		return me;
	}
	
	public Set<Corsoiseur> getOthers() {
		return others;
	}
}
