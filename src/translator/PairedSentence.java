package translator;

/*
 * Zusammengehöredne Text-Paare bestehend aus je einem Originalsatz
 * und zugehöriger Übersetzung
 */

public class PairedSentence {
	private String original;
	private String translation;
	
	PairedSentence(String orig, String trans){
		original = orig;
		translation = trans;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
}
