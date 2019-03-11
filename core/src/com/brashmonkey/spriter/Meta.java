package com.brashmonkey.spriter;

public class Meta {

	public Var[] varline;
	public TagLine tagLine;
    public String[] tagName;

	public Meta(int varKey, int tagKey ) {
	    this.varline = new Var[varKey];
	    this.tagLine = new TagLine(tagKey);
    }

	static class Var {
		int id;
		String name;
		Value def;
		Key[] keys;

		public Key get(long time) {
			for (Key key: this.keys)
				if (key.time == time)
					return key;
			return null;
		}

		public boolean has(long time) {
			return this.get(time) != null;
		}

		public String getName() {
			return this.name;
		}

		public int getId() {
			return this.id;
		}

		public Value getDefault() {
			return this.def;
		}
	}

	public Var getVar(long time) {
		for (Var var: this.varline)
			if (var.get(time) != null)
				return var;
		return null;
	}
}
