/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Teoría de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Autor: Leonardo A. Hernández R. - Agosto 2008 - Marzo 2009
 * Autor:
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package logic;

import java.util.ArrayList;

/**
 * Clase que modela un analizador léxico
 */

public class AnalizadorLexico {

	/**
	 * Lista que contiene todas las palabras reservadas
	 */
	ArrayList<String> palabrasReservadas;
	
	/**
	 * Método constructor de la clase AnalizadorLexico
	 */
	public AnalizadorLexico(){
		palabrasReservadas = new ArrayList<String>();
		cargarPalabrasReservadas();
	}
	

	/**
	 * Extrae los tokens de un código fuente dado
	 * 
	 * @param cod
	 *            - código al cual se le van a extraer los tokens - !=null
	 * @return vector con los tokens
	 */
	public ArrayList<Token> extraerTokens(String cod) {
		Token token;
		ArrayList<Token> vectorTokens = new ArrayList<Token>();
		int i = 0;
		while (i < cod.length()) {
			token = extraerSiguienteToken(cod, i);
			token.asignarLinea(cod);
			if(token.isValido())
				vectorTokens.add(token);
			i = token.darIndiceSiguiente();
		}
		return vectorTokens;
	}

	/**
	 * Extrae el token de la cadena cod a partir de la posición i, basándose en
	 * el Autómata
	 * 
	 * @param cod
	 *            - código al cual se le va a extraer un token - codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	public Token extraerSiguienteToken(String cod, int i) {
		Token token;
		token = extraerEntero(cod, i);
		if (token != null)
			return token;
		token = extraerPalabraReservada(cod, i);
		if (token != null)
			return token;
		token = extraerIdentificador(cod, i);
		if (token != null)
			return token;
		token = extraerTerminalDeLinea(cod, i);
		if(token != null)
			return token;
		token = extraerSeparador(cod, i);
		if(token != null)
			return token;
		token = extraerCadena(cod, i);
		if(token != null)
			return token;
		token = extraerSimbolo(cod, i);
		if(token != null)
			return token;
		token = extraerComentario(cod, i);
		if(token != null)
			return token;
		token = extraerNoReconocido(cod, i);
		return token;
	}

	/**
	 * Intenta extraer un comentario de la cadena cod a partir de la posicion i,
	 * basándose en el Autómata
	 * @param cod
	 *            - código al cual se le va a extraer un token - codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	private Token extraerComentario(String cod, int i) {
		if(cod.charAt(i) == '-' && i + 1 < cod.length() && cod.charAt(i + 1) == '-'){
			int j = i + 2;
			if(j < cod.length())
			do
				j++;
			while(j < cod.length() && cod.charAt(j) != 10 && cod.charAt(j) != 13);
			return new Token(cod.substring(i, j), Token.COMENTARIO, j);
		}
		return null;
	}



	/**
	 * Intenta extraer un conjunto de la cadena cod a partir de la posicion i,
	 * basándose en el Autómata
	 * @param cod
	 *            - código al cual se le va a extraer un token - codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	private Token extraerSimbolo(String cod, int i) {
		if(cod.charAt(i) == '{' || cod.charAt(i) == '}'){
			int j = i + 1;
			return new Token(cod.substring(i, j), Token.SIMBOLO, j);
		}
		return null;
	}



	/**
	 * Intenta extraer una cadena de la cadena cod a partir de la posicion i,
	 * basándose en el Autómata
	 * @param cod
	 *            - código al cual se le va a extraer un token - codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	private Token extraerCadena(String cod, int i) {
		if(cod.charAt(i) == '"'){
			int j = i + 1;
			
			if(j < cod.length()){
				do{
					if(cod.charAt(j) == 10 || cod.charAt(j) == 13)
						return null;
					if(cod.charAt(j) == '"')
						return new Token(cod.substring(i, j + 1), Token.CADENA, j + 1);
					j++;
				}while(j < cod.length());
				
			}
			
		}
		return null;
	}



	/**
	 * Intenta extraer un separador de la cadena cod a partir de la posicion i,
	 * basándose en el Autómata
	 * @param cod
	 *            - código al cual se le va a extraer un token - codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	private Token extraerSeparador(String cod, int i) {
		if(cod.charAt(i) == ',')
			return new Token(cod.substring(i, i + 1), Token.SEPARADOR, i + 1);
		return null;
	}

	/**
	 * Intenta extraer un terminal de linea de la cadena cod a partir de la posicion i,
	 * basándose en el Autómata
	 * @param cod
	 *            - código al cual se le va a extraer un token - codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	private Token extraerTerminalDeLinea(String cod, int i) {
		if(cod.charAt(i) == '.')
			return new Token(cod.substring(i, i + 1), Token.TERMINALDELINEA, i + 1);
		return null;
	}



	/**
	 * Intenta extrar una palabra reservada de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * @param cod
	 *            - código al cual se le va a extraer un token - codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	private Token extraerPalabraReservada(String cod, int i) {
		if(i + 1 < cod.length() && cod.substring(i, i + 2).equals("->"))
			return new Token(cod.substring(i, i + 2), Token.PALABRARESERVADA, i + 2);
			
		int j, res;
		String temp;
		if(esLetra(cod.charAt(i))){
			j = i + 1;
			if(j < cod.length() && esLetra(cod.charAt(j))){
				do{
					if(cod.charAt(j) == 32 || cod.charAt(j) == 10 || cod.charAt(j) == 13){
						temp = cod.substring(i, j);
						res = buscarEnReservadas(temp);
						if(res == 1)
							return new Token(temp, Token.PALABRARESERVADA, j);
						if(res == -1)
							return null;
					}
					j++;
				}while(j < cod.length() && (esLetra(cod.charAt(j)) || cod.charAt(j) == 32 || cod.charAt(j) == 10 || cod.charAt(j) == 13));
			}
			temp = cod.substring(i, j);
			res = buscarEnReservadas(temp);
			if(res == 1)
				return new Token(temp, Token.PALABRARESERVADA, j);
			if(res == -1)
				return null;
		}
		return null;
	}


	/**
	 * Verifica si una cadena hacer parte completa o parcialmente de una alguna palabra
	 * reservada
	 * @param temp
	 * @return
	 */
	private int buscarEnReservadas(String temp) {
		for(int i = 0; i < palabrasReservadas.size(); i++){
			if(temp.equals(palabrasReservadas.get(i)))
				return 1;
			if(palabrasReservadas.get(i).length() >= temp.length()){
				String toCompare = palabrasReservadas.get(i).substring(0, temp.length());
				if(temp.equals(toCompare))
					return 2;
			}
			 
			
		}
		return -1;
	}



	/**
	 * Intenta extraer un entero de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod
	 *            - código al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a intentar extraer un
	 *            entero - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posición dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posición
	 *         del siguiente lexema.
	 */

	public Token extraerEntero(String cod, int i) {
		int j;
		String lex = "";
		if (esDigito(cod.charAt(i))) {
			j = i + 1;
			if (j < cod.length() && esDigito(cod.charAt(j))) {
				do
					j++;
				while (j < cod.length() && esDigito(cod.charAt(j)));
				
			}
			lex = cod.substring(i, j);
			Token token = new Token(lex, Token.ENTERO, j);
			return token;
		}

		return null;
	}




	/**
	 * Intenta extraer un identificador de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod
	 *            - código al cual se le va a intentar extraer un identficador -
	 *            codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posición dada no
	 *         es un identificador. El Token se compone de el lexema, el tipo y
	 *         la posición del siguiente lexema.
	 */

	public Token extraerIdentificador(String cod, int i) {
		if (esLetra(cod.charAt(i))) {
			int j = i + 1;
			while (j < cod.length() && (esLetra(cod.charAt(j)) || esDigito(cod.charAt(j))))
				j++;
			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.IDENTIFICADOR, j);
			return token;
		}
		return null;
	}

	/**
	 * Extraer un lexema no reconocido de la cadena cod a partir de la posición
	 * i. Antes de utilizar este método, debe haberse intentado todos los otros
	 * métodos para los otros tipos de token
	 * 
	 * @param cod
	 *            - código al cual se le va a extraer el token no reconocido -
	 *            codigo!=null
	 * @param i
	 *            - posición a partir de la cual se va a extraer el token no
	 *            reconocido - 0<=indice<codigo.length()
	 * @return el token no reconocido. El Token se compone de lexema, el tipo y
	 *         la posición del siguiente lexema.
	 * 
	 */
	public Token extraerNoReconocido(String cod, int i) {
		String lexema = cod.substring(i, i + 1);
		int j = i + 1;
		Token token = new Token(lexema, Token.NORECONOCIDO, j);
		if(cod.charAt(i) == 10 || cod.charAt(i) == 32 || cod.charAt(i) == 13)
			token.setValido(false);
		return token;
	}

	/**
	 * Determina si un carácter es un dígito
	 * 
	 * @param caracter
	 *            - Carácter que se va a analizar - caracter!=null,
	 * @return true o false según el carácter sea un dígito o no
	 */
	public boolean esDigito(char caracter) {
		return caracter >= '0' && caracter <= '9';
	}

	/**
	 * Determina si un carácter es una letra
	 * 
	 * @param caracter
	 *            - Carácter que se va a analizar - caracter!=null,
	 * @return true o false según el carácter sea una letra o no
	 */
	public boolean esLetra(char caracter) {
		return (caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z');
	}
	
	/**
	 * Método que carga las palabras reservadas del lenguage.
	 */
	private void cargarPalabrasReservadas() {
		palabrasReservadas.add("Traer de");
		palabrasReservadas.add("Ingresar en");
		palabrasReservadas.add("Elimine en");
		palabrasReservadas.add("Actualice en");
		palabrasReservadas.add("donde");
		palabrasReservadas.add("como");
		palabrasReservadas.add("todo");
		palabrasReservadas.add("sea igual a");
		palabrasReservadas.add("sea diferente a");
		palabrasReservadas.add("agrupado por");
		palabrasReservadas.add("este entre");
		palabrasReservadas.add("la");
		palabrasReservadas.add("los");
		palabrasReservadas.add("las");
		palabrasReservadas.add("el");
		palabrasReservadas.add("o");
		palabrasReservadas.add("y");
		palabrasReservadas.add("no");
		palabrasReservadas.add("es");
		palabrasReservadas.add("->");
		palabrasReservadas.add("ordenado por");
		palabrasReservadas.add("descendente");
		
	}

}
