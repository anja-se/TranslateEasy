package translator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Enthält die Methode getSentences(), welche einen Text aus Datei extahiert,
 * in Sätze gliedert und als Liste zurück gibt.
 */

public class SentenceExtractor {
	
	public static List<String> getSentences(File textFile) throws IOException {
		String text;
		List<String> sentenceList = new ArrayList<String>();
		
		//Lese Text aus Datei
		BufferedReader bf = new BufferedReader(new FileReader(textFile));
		StringBuilder sb = new StringBuilder();
		for (String line = bf.readLine();
				line != null;
				line = bf.readLine()) {
			sb.append(line + "\n");
		}
		bf.close();
		text = sb.toString();
		
		//Analyse des Text nach Satzende und Erstellen der Liste
		boolean stop = false;
		for(int i = 0, start = 0; i < text.length(); i++) {
			//Zeilenumbruch ist Satzende
			if (text.charAt(i) == '\n')
				stop = true;
			// ! oder ? sind Satzende, wenn darauf ein Leerzeichen folgt 
			else if (text.charAt(i) == ('!'|'?')) {
				if (i == text.length()-1)
					stop = true;
				else if (text.charAt(i + 1) == ' ')
					stop = true;
				else stop = false;
			}
			//Bestimmen ob ein Punkt Satzende ist:
			else if (text.charAt(i) == '.') {
				//Ja wenn Textende (fast) erreicht ist
				if (i == text.length()-2)
					stop = true;
				//Nein, wenn Punkt nicht das letzte Satzzeichen ist
				else if (text.charAt(i+1) != ' ')
					stop = false;
				//Nein, wenn nach dem Punkt kleingeschrieben wird
				else if (Character.isLowerCase(text.charAt(i+2)))
						stop = false;
				//Nein, wenn vor dem Punkt eine Zahl steht
				else if (Character.isDigit(text.charAt(i-1)))
					stop = false;
				//Nein bei Präfixen und Abkürzungen
				else if(text.substring(0, i).endsWith("Mr.|Mrs.|Ms.|Dr.")) 
						stop = false;
				//Wenn nichts davon zutrifft, Satzende
				else
						stop = true;
				}
			//Kein Satzzeichen = kein Satzende
			else
				stop = false;
			//Wenn Satzende, der Liste hinzufügen und neuen Anfang für den nächsten Satz setzen.
			if (stop == true) {
				//Satzende und neuer Satzanfang abhängig davon, ob letztes Zeichen ein Umbruch war
				if(text.charAt(i) == '\n') { 
					sentenceList.add(text.substring(start, i));
					start = i+1;
				}
				else {
					sentenceList.add(text.substring(start, i+1));
					start = i+2;
					i++;
				}
			}
		}
		
		//Leere Elemente löschen
		List<String> delete = new ArrayList<String>();
		for(String sentence: sentenceList) {
			if (sentence.trim().length() == 0)
				delete.add(sentence);			
		}
		sentenceList.removeAll(delete);
				
		return sentenceList;
	}

}

