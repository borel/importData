package main.java;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import main.ressources.Ressources;

public class Log {

	Logger logger = null;

	public Log() throws SecurityException, IOException {
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

	public void trace(MessageData messageData) {

		SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
		StringBuilder dateWithSimpleFormat = new StringBuilder(formatDate.format(messageData.getDate()));

		String message = "The message with subject '"
				+ messageData.getSubject() + "' and content '"
				+ messageData.getContent() + "' and date = '"
				+ dateWithSimpleFormat + "' and priority = '"
				+ messageData.getPriority()+ "'";
		logger.log(Level.SEVERE, message);
	}

}
