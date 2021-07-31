package translator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/*
 * Verwaltet Dateien,
 * kontrolliert Textausgabe und
 * Vor- und Zurückschalten zwischen den Sätzen
 */

public class Translator {
	private File storedData; //Zwischenspeicher, falls eine Übersetzung bereits begonnen wurde
	private File translationFile; //Datei in der Übersetzung gespeichert wird
	private List<PairedSentence> translation; //Interne Datenstruktur für Übersetzung und Originaltext
	private int index = 0; //Index für die Navigation zwischen Sätzen
	

	public Translator(String srcPath) throws IOException { 
		
		//interne Datenstruktur für Übersetzung initialisieren 
		translation = new ArrayList<PairedSentence>();
		
		//Erstellen der Dateien für Übersetzung und Zwischenspeicher
		String storedDataPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + ".tmp";
		storedData = new File(storedDataPath);
		String translationPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_translation.txt";
		translationFile = new File(translationPath);
		
		/*
		 * Übersetzung erstellen oder laden.
		 * Falls vorhanden, werden zwischengespeicherte Daten verwendet
		 */
		if (storedData.exists()) {
			getStoredData();
		}
		
		else {
			List<String> original = SentenceExtractor.getSentences(new File(srcPath));
			for (String sentence: original) {
				translation.add(new PairedSentence(sentence, ""));
			}
		}
	}
	
	// zwischengespeicherte Daten aus voriger Nutzung werden geladen
	private void getStoredData() throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(storedData));
		
		for (String line = bf.readLine();
				line != null;
				line = bf.readLine()) {
			translation.add(new PairedSentence(line, bf.readLine()));
			}
		bf.close();
		
	}
	
	//liefert englischen Satz zur Anzeige an Hauptfenster
	public String srcGetSentence() throws IndexOutOfBoundsException{
		return translation.get(index).getOriginal();
	}
	
	//liefert deutsche Übersetzung zur Anzeige an Hauptfenster
	public String getTranslation() {
		String trans = translation.get(index).getTranslation();
		if (trans.equals(""))
			try {
				trans = GoogleTranslator.translate(translation.get(index).getOriginal());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return trans;
	}
	
	//Speichern des Satzes (Übersetzung)
	public void saveSentence(String sentence) {
		translation.get(index).setTranslation(sentence);
	}
	
	//Speichern und zum weiter zum nächsten Satz 
	public void saveAndNext(String sentence) {
		saveSentence(sentence);
		
		if (index < translation.size()-1)
			index ++;
		else
			System.out.println("You've reached the end of the text.");
	}
	
	//Speichern und zurück zum vorherigen Satz
	public void saveAndBack(String sentence){
		saveSentence(sentence);
		
		if (index>0)
			index--;
	}
	
	public void back() {
		index--;
	}
	
	//Speichern der Daten, Erstellung der Übersetzungsdatei
	public void saveAll(String sentence) throws IOException {
		saveSentence(sentence);
		saveTranslation();
		saveData();
	}
	
	/*
	 * Speichern der gesamten (bisherigen) Übersetzung in Textdatei.
	 * Eventuell vorhandene Vorherige Versionen werden überschrieben.
	 */
	private void saveTranslation() throws IOException {
		FileWriter writer = new FileWriter(translationFile);
		for (PairedSentence c:translation) {
			String sentence = c.getTranslation();
			writer.write(sentence + " ");
		}
		writer.close();
	}
	
	/*
	 * Speichern von Original-Sätzen und Übersetzung in einer tmp-Datei.
	 * Dadurch werden bei erneuter Bearbeitung des gleichen Textes die
	 * bereits bearbeiteten, eigenen Übersetzungen angezeigt.
	 */
	private void saveData() throws IOException {
		PrintStream writer = new PrintStream(storedData);
		for (PairedSentence c: translation) {
			writer.println(c.getOriginal());
			writer.println(c.getTranslation());
		}
		writer.close();
	}
}
