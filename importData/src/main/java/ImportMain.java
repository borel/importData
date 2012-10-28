package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import main.ressources.Ressources;

import com.mongodb.MongoException;

public class ImportMain {

	public static void main(String[] args) {
		try {
			// Init
			Collection<MessageData> messageDataImported = new ArrayList<MessageData>();
			Database database = new Database();
			Directory directoryManager = new Directory();
			CSVManager csvManager = new CSVManager();
			XMLManager xmlManager = new XMLManager();
			Email email = new Email();
			
			//Init log
			Log.init();
			
			// Database connection
			database.connection();

			// Access to directory
			File directory = new File(Ressources.RESOURCES_PATH);

			// CSV

			// Import CSV file
			Collection<File> csvFiles = directoryManager.getCSVFileInRepertory(directory);

			// Convert CSV to Object Data
			messageDataImported = csvManager.getMessagesDatas(csvFiles);
			

			// XML

			// Import XML file
			Collection<File> xmlFiles = directoryManager.getXmlFileInRepertory(directory);
			
			//Convert XML to Object Data
			messageDataImported.addAll(xmlManager.getMessagesDatas(xmlFiles));

			// Object Message
			for (MessageData messageData : messageDataImported) {

				// stored to database
				database.insertMessage(messageData);
				
				if (messageData.getPriority() > 10) {
					email.send(messageData);
				} else {
					Log.traceSuccesfull(messageData);
				}

			}
			
			
		} catch (MongoException e) {
			Log.traceDatabaseError();
		}
		 catch (IOException e) {
			Log.traceLogError();
		} 
	}

}
