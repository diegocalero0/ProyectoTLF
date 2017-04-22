package logic;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainWindows;

/**
 * En esta clase se inicia el programa
 * @author Diego Calero
 *
 */
public class Launcher {
	
	/**
	 * Metodo principal de la clase Launcher
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindows mw = new MainWindows();
		mw.setVisible(true);
	}

}
