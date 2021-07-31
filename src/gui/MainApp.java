package gui;
import translator.Translator;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

/*
 * Hauptanwendung.
 * Hier wird der ausgewählte englische Text Satz für Satz präsentiert
 * und eine Übersetzung via Google Translate angeboten.
 * Die Übersetzung kann entsprechend angepasst werden.
 */

public class MainApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Translator translator;

	/**
	 * Launch the application.
	 */
	public static void startApp(Translator trans) {
		translator = trans;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp frame = new MainApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainApp() throws IOException {
		
		setTitle("Translate Easy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 748, 515);
		contentPane.add(panel);
		panel.setLayout(null);
	
		JTextArea txtAreaSource = new JTextArea();
		txtAreaSource.setEditable(false);
		txtAreaSource.setBounds(36, 55, 543, 149);
		panel.add(txtAreaSource);
		txtAreaSource.setText(translator.srcGetSentence());
		txtAreaSource.setLineWrap(true);
		txtAreaSource.setWrapStyleWord(true);
		
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(564, 55, 15, 149);
		panel.add(scrollBar);
		
		JTextArea txtAreaTranslation = new JTextArea();
		txtAreaTranslation.setBounds(31, 263, 548, 200);
		panel.add(txtAreaTranslation);
		txtAreaTranslation.setText(translator.getTranslation());
		txtAreaTranslation.setLineWrap(true);
		txtAreaTranslation.setWrapStyleWord(true);
		
		JButton btnNext = new JButton("weiter");
		btnNext.setBounds(591, 304, 90, 29);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				translator.saveAndNext(txtAreaTranslation.getText());
				txtAreaSource.setText(translator.srcGetSentence());
				txtAreaTranslation.setText(translator.getTranslation());
			}
		});
		panel.add(btnNext);
		
		JButton btnBack = new JButton("zurück");
		btnBack.setBounds(591, 263, 90, 29);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				translator.saveAndBack(txtAreaTranslation.getText());
				txtAreaSource.setText(translator.srcGetSentence());
				txtAreaTranslation.setText(translator.getTranslation());
			}
		});
		panel.add(btnBack);
		
		JButton btnQuit = new JButton("Speichern und Beenden");
		btnQuit.setBounds(553, 480, 189, 29);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					translator.saveAll(txtAreaTranslation.getText());
					System.exit(EXIT_ON_CLOSE);
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("Speichern fehlgeschlagen");
				}	
			}
		});
		panel.add(btnQuit);
		
		JLabel lblTranslateHere = new JLabel("Übersetzung");
		lblTranslateHere.setBounds(36, 235, 101, 16);
		panel.add(lblTranslateHere);
		
		JLabel lblSourceText = new JLabel("Original-Text");
		lblSourceText.setBounds(36, 32, 101, 16);
		panel.add(lblSourceText);
		
	}
}
