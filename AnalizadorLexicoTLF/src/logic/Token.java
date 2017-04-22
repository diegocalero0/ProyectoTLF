/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quind�o (Armenia - Colombia)
 * Programa de Ingenier�a de Sistemas y Computaci�n
 *
 * Asignatura: Teor�a de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Autor: Leonardo A. Hern�ndez R. - Agosto 2008, sep 2013.
 * Autor: 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package logic;

/**
 * Clase que modela un token
 * @author Leonardo A. Hern�ndez R.
 */

public class Token {
	/**
	 * Constantes para modelar los posibles tipos de token que se van a analizar
	 */
	final public static String ENTERO = "Entero";
	final public static String PALABRARESERVADA = "Palabra reservada";
	final public static String TERMINALDELINEA = "Terminar de linea";
	final public static String CADENA = "Cadena";
	final public static String IDENTIFICADOR = "Identificador";
	final public static String SEPARADOR = "Separador";
	final public static String COMENTARIO = "Comentario";
	final public static String SIMBOLO = "Simbolo";
	final public static String NORECONOCIDO = "No reconocido";

	/**
	 * Bandera que identifica un token como valido o invalido
	 */
	private boolean valido;
	/**
	 * Lexema
	 */
	private String lexema;

	/**
	 * tipo
	 */
	private String tipo;

	/**
	 * posici�n del siguiente lexema
	 */
	private int indiceSiguiente;
	/**
	 * Linea en la que se encuentra el token
	 */
	private int linea;

	/**
	 * Constructor de un token
	 * 
	 * @param elLexema
	 *            - cadena - laCadena != null
	 * @param elTipo
	 *            - tipo del token - elTipo != null
	 * @param elIndiceSiguiente
	 *            - posici�n del siguiente token - laPosicionSiguiente > 0
	 */
	public Token(String elLexema, String elTipo, int elIndiceSiguiente) {
		lexema = elLexema;
		tipo = elTipo;
		indiceSiguiente = elIndiceSiguiente;
		valido = true;
	}

	/**
	 * Entrega la informaci�n del token
	 * 
	 * @return Descripci�n del token
	 */
	public String darDescripcion() {
		return "Token: " + lexema + "     Tipo: " + tipo + "     �ndice del siguiente: " + indiceSiguiente;
	}

	/**
	 * M�todo que retorna el lexema del token
	 * 
	 * @return el lexema del token
	 */
	public String darLexema() {
		return lexema;
	}

	/**
	 * M�todo que retorna la posici�n del siguiente lexema
	 * 
	 * @return posici�n del siguiente token
	 */
	public int darIndiceSiguiente() {
		return indiceSiguiente;
	}

	/**
	 * M�todo que retorna el tipo del token
	 * 
	 * @return el tipo del token
	 */
	public String darTipo() {
		return tipo;
	}
	
	/**
	 * M�todo que retorna la linea en la que se encuentra el token.
	 * @return la linea en la que se encuentra el token
	 */
	public int darLinea(){
		return linea;
	}
	
	/**
	 * M�todo que asigna la linea en la que se encuentra el token
	 */
	public void asignarLinea(String code){
		int linea = 1;
		for(int i = 0; i < code.length(); i++){
			if(i == indiceSiguiente - 1){
				this.linea = linea;
			}
			if(code.charAt(i) == 10)
				linea++;
		}
	}

	/**
	 * @return the valido
	 */
	public boolean isValido() {
		return valido;
	}

	/**
	 * @param valido the valido to set
	 */
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	
	

}
