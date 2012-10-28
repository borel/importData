package main.java;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

import org.jdom2.JDOMException;

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
			Log log = new Log();
			

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
				if (messageData.getPriority() > 10) {
					email.send(messageData);
				} else {
					log.trace(messageData);
				}

				// stored to database
				// database.insertMessage(messageData);
			}

			System.out.println("Done");

		} catch (MongoException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
