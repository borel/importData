package main.java;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import main.ressources.Ressources;

public class  Log {

	static Logger logger = null;

	/**
	 * Init the log information with information stock in class Ressources
	 * @throws IOException
	 */
	public static void init()throws IOException {
		logger = Logger.getLogger(Ressources.LOG_ID);
		// path of the log file
		FileHandler fileHandler = new FileHandler(Ressources.LOG_PATH);
		logger.addHandler(fileHandler);
		
		//Simple format
		fileHandler.setFormatter(new SimpleFormatter());
		
		// Add to console
		ConsoleHandler ch = new ConsoleHandler();
		logger.addHandler(ch);
	}
	
	/**
	 * Trace successfull insertion  in db
	 * @param messageData
	 */
	public static void traceSuccesfull(MessageData messageData) {

		SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
		StringBuilder dateWithSimpleFormat = new StringBuilder(formatDate.format(messageData.getDate()));

		String message = "The message with subject '"
				+ messageData.getSubject() + "' and content '"
				+ messageData.getContent() + "' and date = '"
				+ dateWithSimpleFormat + "' and priority = '"
				+ messageData.getPriority()+ "' has been successfully manage";
		logger.log(Level.INFO, message);
	}
	
	/**
	 * Trace wrong Date format 
	 * @param messageData
	 * @param file
	 * @param valueInError
	 */
	public static void traceDateError(MessageData messageData,File file,String valueInError) {
		String message = "The message with subject '"
				+ messageData.getSubject() + "' and content '"
				+ messageData.getContent() + "' and date = '"
				+ messageData.getPriority()+ "' on the file '"
				+ file.getName()+"' has a wrong date format . The value in error is : "+valueInError;
		logger.log(Level.SEVERE, message);
	}
	
	/**
	 * Trace error
	 * @param messageData
	 * @param file
	 * @param valueInError
	 */
	public static void traceError(MessageData messageData,File file) {
		String message = "The message with subject '"
				+ messageData.getSubject() + "' and content '"
				+ messageData.getContent() + "' and date = '"
				+ messageData.getPriority()+ "' on the file '"
				+ file.getName()+"' has a wrong format .";
		logger.log(Level.SEVERE, message);
	}
	
	/**
	 * Trace wrong Integer format 
	 * @param messageData
	 * @param file
	 * @param valueInError
	 */
	public static void tracePriorityError(MessageData messageData,File file,String valueInError) {
		String message = "The message with subject '"
				+ messageData.getSubject() + "' and content '"
				+ messageData.getContent() + "' and date = '"
				+ messageData.getPriority()+ "' on the file '"
				+ file.getName()+"' has a wrong priority format . The value in error is : "+valueInError;
		logger.log(Level.SEVERE, message);
	}
	
	/**
	 * Trace file corruption
	 * @param file
	 */
	public static void traceFileCorrupt(File file) {
		String fileCorrupt = "The file '"+ file.getName()+"' is corrupt";
		logger.log(Level.SEVERE, fileCorrupt);
	}
	
	/**
	 * Trace mail configuration error
	 */
	public static void traceMailError() {
		String emailError = "The email configuration is not ok";
		logger.log(Level.SEVERE, emailError);
	}
	
	/**
	 * Trace database configuration error
	 */
	public static void traceDatabaseError() {
		String dbError = "The database configuration is not ok";
		logger.log(Level.SEVERE, dbError);
	}
	
	/**
	 * Trace log configuration error
	 */
	public static void traceLogError() {
		String logError = "The log configuration is not ok";
		logger.log(Level.SEVERE, logError);
	}
	
	
	

}
