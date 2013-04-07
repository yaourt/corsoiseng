package com.yaourtprod.corsoiseng;

import java.util.Set;

public class OthersAndMe {
	final Set<Corsoiseur> others;
	final Corsoiseur me;
	final Sum sum;

	public class Sum {
		int count = 0;
		int pietra = 0;
		int terrine = 0;

		public int getCount() {
			return count;
		}

		public int getPietra() {
			return pietra;
		}

		public int getTerrine() {
			return terrine;
		}
	}

	public OthersAndMe(final Corsoiseur me, final Set<Corsoiseur> others) {
		this.me = me;
		this.others = others;
		this.sum = new Sum();
		if (null != me) {
			this.sum.count += this.me.getCombienKilEst();
			this.sum.pietra += this.me.getCombienKilNenveuLaPietra();
			this.sum.terrine += this.me.getCombienKilNenveuLaTerrine();
		}
		if (null != others) {
			for (final Corsoiseur c : this.others) {
				this.sum.count += c.getCombienKilEst();
				this.sum.pietra += c.getCombienKilNenveuLaPietra();
				this.sum.terrine += c.getCombienKilNenveuLaTerrine();
			}
		}
	}

	public Corsoiseur getMe() {
		return me;
	}

	public Set<Corsoiseur> getOthers() {
		return others;
	}

	public Sum getSum() {
		return sum;
	}
}
