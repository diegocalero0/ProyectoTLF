package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.AnalizadorLexico;
import logic.FileHandler;
import logic.Token;

/**
 * Esta clase es un JFrame que sera la ventana principal
 * @author Diego Calero
 *
 */
public class MainWindows extends JFrame implements ActionListener{

	/**
	 * Código serial en caso de querer serializar la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Color verde informativo
	 */
	private static final Color greenInf = new Color(0, 153, 0);
	/**
	 * Color rojo informativo
	 */
	private static final Color redInf = new Color(204, 0, 0);
	/**
	 * El texto que se encuentra en el archivo abierto
	 */
	private String text;
	/**
	 * Objeto que se encarga de analizar el texto
	 */
	private AnalizadorLexico analyzeLex;
	/**
	 * Área donde aparecerán los errores en el momento de analizar
	 */
	private JTextArea txtErr;
	/**
	 * Etiqueta informativa
	 */
	private JLabel lblInf;
	/**
	 * En este Scroll va el área del texto SSQL.
	 */
	private JScrollPane scrollSSQL;
	/**
	 * En este Scroll va la tabla de Tokens.
	 */
	private JScrollPane scrollTokens;
	/**
	 * En este Scroll va el área de errores
	 */
	private JScrollPane scrollErr;
	/**
	 * En esta tabla se mostrará la informacion de los tokens.
	 */
	private JTable tblTokens;
	/**
	 * Modelo de tabla donde irán los tokens.
	 */
	private DefaultTableModel tblModel;
	/**
	 * Cabecera de la tabla
	 */
	private String header[];
	/**
	 * Panel en el cual irá el menú.
	 */
	private JPanel pnlMenu;
	/**
	 * Panel en el cual irá el texto y la tabla que informa de los tokens.
	 */
	private JPanel pnlMain;
	/**
	 * Barra de menú.
	 */
	private JMenuBar mnubarMenu;
	/**
	 * Menús principales de la barra de menú.
	 */
	private JMenu mnuFile, mnuAnalyze;
	/**
	 * Items del menú archivo y del menu analizar.
	 */
	private JMenuItem itmOpen, itmSave, itmSaveAs, itmExit, itmAnalyze;
	/**
	 * Interfaz encargada de abrir o guardar un archivo.
	 */
	private JFileChooser fileChooser;
	/**
	 * Objeto encargado del manejo de archivos.
	 */
	private FileHandler fileHandler;
	/**
	 * Área donde se escribirá el codigo SSQL.
	 */
	private JTextArea txtSSQL;
	/**
	 * Enumeracion del txtSSQl
	 */
	private TextLineNumbers tln;
	/**
	 * Metodo constructor de la clase MainWindows.
	 */
	public MainWindows(){
		this.setTitle("Analizados léxico - TLF");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		header = new String[3];
		header[0] = "Token";
		header[1] = "Lexema";
		header[2] = "Línea";
		fileHandler = new FileHandler();
		analyzeLex = new AnalizadorLexico();

		initComponents();
	}
	
	/**
	 * Metodo donde se inicializan las componentes graficas
	 * de la ventana principal.
	 */
	public void initComponents(){
		pnlMenu = new JPanel();
		pnlMain = new JPanel();
		pnlMenu.setLayout(new BorderLayout());
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		mnubarMenu = new JMenuBar();
		mnuFile = new JMenu("Archivo");
		mnuAnalyze = new JMenu("Funciones");
		mnubarMenu.add(mnuFile);
		mnubarMenu.add(mnuAnalyze);
		itmOpen = new JMenuItem("Abrir");
		itmSave = new JMenuItem("Guardar");
		itmSaveAs = new JMenuItem("Guardar como");
		itmExit = new JMenuItem("Salir");
		itmAnalyze = new JMenuItem("Analizar");
		mnuFile.add(itmOpen);
		mnuFile.add(itmSave);
		mnuFile.add(itmSaveAs);
		mnuFile.add(itmExit);
		mnuAnalyze.add(itmAnalyze);
		
		itmOpen.addActionListener(this);
		itmSave.addActionListener(this);
		itmSaveAs.addActionListener(this);
		itmExit.addActionListener(this);
		itmAnalyze.addActionListener(this);
		
		fileChooser = new JFileChooser();
		txtSSQL = new JTextArea();
		scrollSSQL = new JScrollPane(txtSSQL);
		tblModel = new DefaultTableModel();
		tblTokens = new JTable();
		tblTokens.setModel(tblModel);
		tblModel.addColumn("Token");
		tblModel.addColumn("Lexema");
		tblModel.addColumn("Línea");
		scrollTokens = new JScrollPane(tblTokens);
		tln = new TextLineNumbers(txtSSQL);
		scrollSSQL.setRowHeaderView(tln);
		scrollSSQL.setMaximumSize(new Dimension(1800, 200));
		scrollSSQL.setPreferredSize(new Dimension(1800, 300));
		scrollSSQL.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollTokens.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblInf = new JLabel("Analizador Lexico OK");
		lblInf.setForeground(MainWindows.greenInf);
		txtErr = new JTextArea();
		txtErr.setBorder(new EmptyBorder(5, 5, 5, 5));
		txtErr.setEditable(false);
		txtSSQL.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollErr = new JScrollPane(txtErr);
		scrollErr.setPreferredSize(new Dimension(1800,150));
		scrollErr.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlMenu.add(mnubarMenu, BorderLayout.NORTH);
		pnlMain.add(scrollSSQL);
		pnlMain.add(scrollTokens);
		pnlMain.add(scrollErr);
		pnlMain.add(lblInf);
		add(pnlMenu, BorderLayout.NORTH);
		add(pnlMain, BorderLayout.CENTER);
	}
	
	/**
	 * Este metodo recibe cualquier accion sobre los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == itmOpen)
			openFile();
		if(e.getSource() == itmSave)
			saveFile();
		if(e.getSource() == itmSaveAs)
			saveFileAs();
		if(e.getSource() == itmExit)
			System.exit(0);
		if(e.getSource() == itmAnalyze)
			analyze();
	}
	
	/**
	 * Método que se encarga de la parte visual
	 * a la hora de abrir un archivo.
	 */
	public void openFile(){
		int option = fileChooser.showOpenDialog(this);
		if(option == JFileChooser.APPROVE_OPTION){
			text = fileHandler.openFile(fileChooser.getSelectedFile().getAbsolutePath());
			if(text == null){
				lblInf.setForeground(MainWindows.redInf);
				lblInf.setText("Error, verifique la extension del archivo seleccionado");
			}else{
				lblInf.setForeground(MainWindows.greenInf);
				lblInf.setText("Archivo cargado correctamente");
				txtSSQL.setForeground(Color.BLACK);
				txtSSQL.setText(text);
			}
		}
	}
	
	/**
	 * Método que guarda los cambios realizados en el archivo
	 * abierto actualmente.
	 */
	public void saveFile(){
		boolean func = fileHandler.saveFile(txtSSQL.getText());
		if(func){
			lblInf.setForeground(MainWindows.greenInf);
			lblInf.setText("Cambios guardados");
		}else{
			lblInf.setForeground(MainWindows.redInf);
			lblInf.setText("No hay un archivo abierto actualmente");
		}
	}
	
	/**
	 * Método que se encarga de la parte visual a la hora
	 * de guardar un archivo.
	 */
	public void saveFileAs(){
		
		int option = fileChooser.showSaveDialog(this);
		if(option == JFileChooser.APPROVE_OPTION){
			String path = fileChooser.getSelectedFile().getAbsolutePath() + ".ssql";
			fileHandler.saveFile(txtSSQL.getText(), path);
			lblInf.setForeground(MainWindows.greenInf);
			lblInf.setText("Archivo guardado correctamente");
		}
	}
	
	/**
	 * Metodo intermedio entre la interfaz y la lógica que analiza
	 * el texto.
	 */
	public void analyze(){
		if(fileHandler.getFile() == null){
			JOptionPane.showMessageDialog(null, "Debe guardar el archivo antes de poder analizarlo");
			saveFileAs();
			return;
		}
		if(text != null && !text.equals(txtSSQL.getText())){
			Object[] options = { "Modificar", "Crear nuevo", "Cancelar" };
			int res = JOptionPane.showOptionDialog(null, "Desea modificar el archivo original o crear uno nuevo", "Se han detectado cambios", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
			if(res == 0){
				saveFile();
			}
			if(res == 1){
				saveFileAs();
			}
			if(res == 2){
				lblInf.setForeground(MainWindows.redInf);
				lblInf.setText("No se han realizado cambios");
				return;
			}
		}
		
		ArrayList<Token> tokens = analyzeLex.extraerTokens(txtSSQL.getText());
		txtErr.setText("");
		for(int i = tblModel.getRowCount() - 1; i >= 0; i--)
			tblModel.removeRow(i);
		for(int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).darTipo().equals(Token.NORECONOCIDO)){
				txtErr.setText(txtErr.getText() + "Error en la linea: " + tokens.get(i).darLinea() + " - Caracter invalido: "+ tokens.get(i).darLexema() + "\n");
			}else{
				Object obj[] = {tokens.get(i).darTipo(), tokens.get(i).darLexema(), tokens.get(i).darLinea()};
				tblModel.addRow(obj);
			}
		}
	}

}
