package main.java;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLManager {
	
	/**
	 * Get message data from XML files
	 * @param files
	 * @return
	 */
	public Collection<MessageData> getMessagesDatas(Collection<File> files) {
		Collection<MessageData> messagesDatas = new ArrayList<MessageData>();
		for (File file : files) {
			messagesDatas.addAll(getMessagesDatas(file));
		}
		return messagesDatas;
	}
	
	/**
	 * Get message data from XML file
	 * @param file
	 * @return
	 */
	public Collection<MessageData> getMessagesDatas(File file) {

		Collection<MessageData> messagesDatas = new ArrayList<MessageData>();
		SAXBuilder saxBuilder = new SAXBuilder();
		String priorityS = null;
		String content = null;
		String subject = null;
		String dateS = null;
		
		// Parsing of the file
		Document document;
		try {
			document = saxBuilder.build(file);

			Element racine = document.getRootElement();
			
			//we take all the message children
			List listeMessages = racine.getChildren("message");
			if (!listeMessages.isEmpty()) {
				Iterator i = listeMessages.iterator();

				while (i.hasNext()) {

					Date date;
					Element courant = (Element) i.next();
				
					try {
						priorityS = courant.getChild("priority").getText();
						content = courant.getChild("content").getText();
						subject = courant.getChild("subject").getText();
						dateS = courant.getChild("date").getText();

						//Prioriry cast to int
						int priority = Integer.valueOf(priorityS);
						//Date cast to date
						date = Utils.toDate(dateS);
						MessageData newMessageData = new MessageData(Integer.valueOf(priority), subject, content, date);
						messagesDatas.add(newMessageData);

					} catch (ParseException e) {
						MessageData newMessageData = new MessageData(null, subject, content, null);
						Log.traceDateError(newMessageData, file,dateS);
					}catch (NumberFormatException e) {
						MessageData newMessageData = new MessageData(1, subject, content, null);
						Log.tracePriorityError(newMessageData, file,priorityS);
					}
					catch (Exception e) {
						MessageData message = new MessageData(null, subject, content, null);
						Log.traceError(message, file);
						return null;
					}

				}
			}

		} catch (JDOMException e1) {
			Log.traceFileCorrupt(file);
		} catch (IOException e1) {
			Log.traceFileCorrupt(file);
		}

		return messagesDatas;
	}

}
