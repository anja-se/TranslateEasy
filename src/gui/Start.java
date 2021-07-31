package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import translator.Translator;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

//Dateiauswahl-Dialog
public class Start {

	private JFrame frmStart;
	private JTextField txtBoxChooseSource;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frmStart.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStart = new JFrame();
		frmStart.setTitle("TranslateEasy");
		frmStart.setBounds(100, 100, 584, 134);
		frmStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmStart.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		txtBoxChooseSource = new JTextField();
		txtBoxChooseSource.setBounds(20, 23, 402, 26);
		txtBoxChooseSource.setEditable(false);
		panel.add(txtBoxChooseSource);
		txtBoxChooseSource.setColumns(10);
		
		
		//Datei zum Übersetzen auswählen
		JButton btnSelectSource = new JButton("Datei wählen");
		btnSelectSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int open = chooser.showOpenDialog(null);
				if (open == JFileChooser.APPROVE_OPTION) {
					txtBoxChooseSource.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnSelectSource.setBounds(441, 23, 120, 29);
		panel.add(btnSelectSource);
		
		//Prüfen ob die Datei gültig ist, dann Wechsel zur Hauptanwendung
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!txtBoxChooseSource.getText().endsWith(".txt")) {
					//TODO: Fehlermeldung
					System.out.println("Ungültiges Dateiformat");
					return;
				}
				
				if (new File(txtBoxChooseSource.getText()).length() == 0) {
					//TODO: Fehlermeldung oder button vorher deaktivieren
					System.out.println("Bitte Datei auswählen");
					return;
				}
				
				else {
					Translator trans;
					try {
						trans = new Translator(txtBoxChooseSource.getText());
						MainApp.startApp(trans);
						frmStart.setVisible(false);
					} catch (IOException e1) {
						// TODO Fehlermeldung
						e1.printStackTrace();
					}

				}
			}		
		});
		btnStart.setBounds(441, 58, 120, 29);
		panel.add(btnStart);
	}
}
