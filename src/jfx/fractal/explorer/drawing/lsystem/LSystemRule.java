package jfx.fractal.explorer.drawing.lsystem;

public class LSystemRule {
	public String key;
	public String replacement;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getReplacement() {
		return replacement;
	}
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	
	@Override
	protected LSystemRule clone() {
		LSystemRule rule = new LSystemRule();
		rule.setKey(key);
		rule.setReplacement(replacement);
		return rule;
	}
}
