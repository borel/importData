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

	public Collection<MessageData> getMessagesDatas(Collection<File> files)
			throws IOException, JDOMException, ParseException {
		Collection<MessageData> messagesDatas = new ArrayList<MessageData>();
		for (File file : files) {
			messagesDatas.addAll(getMessagesDatas(file));
		}
		return messagesDatas;
	}

	public Collection<MessageData> getMessagesDatas(File file)
			throws IOException, JDOMException, ParseException {

		Collection<MessageData> messagesDatas = new ArrayList<MessageData>();
		SAXBuilder saxBuilder = new SAXBuilder();
		// Parsing of the file
		Document document = saxBuilder.build(file);

		Element racine = document.getRootElement();

		List listeMessages = racine.getChildren("message");

		Iterator i = listeMessages.iterator();
		
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			// On affiche le nom de l'element courant
			String priority = courant.getChild("priority").getText();
			String content = courant.getChild("content").getText();
			String subject = courant.getChild("subject").getText();
			String dateS = courant.getChild("date").getText();
			
			Date date = Utils.toDate(dateS);
			
			MessageData newMessageData = new MessageData(Integer.valueOf(priority), subject, content, date);
			messagesDatas.add(newMessageData);
		}


		return messagesDatas;

	}

}
