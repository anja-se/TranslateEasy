package translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
 * Übersetzt eine Text-Sequenz via Google Translate
 * 
 * Hinweis: Die Umsetzung basiert auf einer Nutzerantwort auf Stack Overflow.
 * Der Quellcode an dieser Stelle wurde mit geringfügigen Anpassungen übernommen.
 * https://stackoverflow.com/a/48159904
 */

public class GoogleTranslator {
	
	private static String langTo = "de";
	private static String langFrom = "en";

	public static String translate(String sentence) throws IOException  {

		String myGoogleUrl = "https://script.google.com/macros/s/AKfycbyIpLGHHy63hp1ikV7N8jZvRjPW_JnWc-T0Ui9bfp-_9NcwdFqJ/exec";
		String urlString = myGoogleUrl +
                "?q=" + URLEncoder.encode(sentence, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;

		URL url = new URL(urlString);
		StringBuilder response = new StringBuilder();
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Übersetzung kann nicht geladen werden. Bitte Internetverbindung überprüfen.");
		}
		
		return response.toString();
	}

}
