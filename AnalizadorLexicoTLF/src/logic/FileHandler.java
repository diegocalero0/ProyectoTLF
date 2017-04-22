package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase que se encarga del manejo de archivos.
 * Se encarga de funciona como abrir y guardar archivos.
 * @author Diego Calero
 *
 */
public class FileHandler {
	/**
	 * Archivo actual abierto.
	 */
	private File file;
	/**
	 * Objeto encargado de leer el archivo.
	 */
	private FileInputStream fileReader;
	/**
	 * Objeto encargado de escribir sobre el archivo.
	 */
	private FileOutputStream fileWriter;
	
	public FileHandler(){
		fileReader = null;
		fileWriter = null;
	}
	
	/**
	 * Se encarga de leer un archivo.
	 * @param path la ruta del archivo.
	 * @return el contenido del archivo.
	 */
	public String openFile(String path){
		if(!path.substring(path.length() - 5).equalsIgnoreCase(".ssql"))
			return null;
		String res = "";
		try {
			file = new File(path);
			fileReader = new FileInputStream(file);
			int curr = fileReader.read();
			do{
				res += (char)curr;
				curr = fileReader.read();
			}while(curr != -1);
			fileReader.close();
			fileReader = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Se encarga de guardar el archivo abierto actualmente
	 * si es que existe un archivo abierto
	 * @param txt, el nuevo texto a guardar en el archivo.
	 * @return true si existe una archivo abierto, false en caso contrario.
	 */
	public boolean saveFile(String txt){
		if(file == null)
			return false;
		try{
			fileWriter = new FileOutputStream(file);
			for(int i = 0; i < txt.length(); i++)
				fileWriter.write(txt.charAt(i));
			fileWriter.close();
			fileWriter = null;
		}catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Guarda el archivo en path con el texto txt
	 * @param txt el texto que estará en el archivo
	 * @param path la ruta del archivo
	 * @return true si guardó el archivo correctamente
	 */
	public boolean saveFile(String txt, String path){
		
		try{
			file = new File(path);
			fileWriter = new FileOutputStream(file);
			for(int i = 0; i < txt.length(); i++)
				fileWriter.write(txt.charAt(i));
			fileWriter.close();
			fileWriter = null;
			return true;
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the fileReader
	 */
	public FileInputStream getFileReader() {
		return fileReader;
	}

	/**
	 * @param fileReader the fileReader to set
	 */
	public void setFileReader(FileInputStream fileReader) {
		this.fileReader = fileReader;
	}

	/**
	 * @return the fileWriter
	 */
	public FileOutputStream getFileWriter() {
		return fileWriter;
	}

	/**
	 * @param fileWriter the fileWriter to set
	 */
	public void setFileWriter(FileOutputStream fileWriter) {
		this.fileWriter = fileWriter;
	}
	
	
	
}
