package main.java;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import main.ressources.Ressources;
import au.com.bytecode.opencsv.CSVReader;

public class CSVManager {
	
	/**
	 * get data from a list of CSV file
	 * @param file
	 * @return list of data
	 * @throws IOException 
	 */
	public List<String[]> getDatas(Collection<File> files) throws IOException {
		List<String[]> datas = new ArrayList<String[]>();
		for (File file : files) {
			datas.addAll(getDatas(file));
		}
		return datas;
	}
	
	
	/**
	 * get data from CSV file
	 * @param file
	 * @return list of data
	 * @throws IOException 
	 */
	public List<String[]> getDatas(File file) throws IOException {

		FileReader fileReader;
		List<String[]> datas = new ArrayList<String[]>();

		fileReader = new FileReader(file);
		// Put the separator of the CSV file
		CSVReader csvReader = new CSVReader(fileReader, Ressources.SEPARATOR);

		String[] nextLine = null;

		while ((nextLine = csvReader.readNext()) != null) {
			int size = nextLine.length;

			// Empty line
			if (size == 0) {
				continue;
			}
			String debut = nextLine[0].trim();
			if (debut.length() == 0 && size == 1) {
				continue;
			}

			// Commenatary line
			if (debut.startsWith("#")) {
				continue;
			}
			datas.add(nextLine);
		}

		return datas;

	}
	
	/**
	 * Convert the CSV file into Message Date object
	 * @param files
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Collection<MessageData> getMessagesDatas(Collection<File> files) throws IOException, ParseException {
		Collection<MessageData> messagesDatas = new ArrayList<MessageData>();
		List<String[]> datas = getDatas(files);
		for (String[] csvData : datas) {
			MessageData newMessageData = convertToMessageDate(csvData);
			messagesDatas.add(newMessageData);
		}
		return messagesDatas;
	}
	
	/**
	 * convert csvData to MessageData Object
	 * @param csvData
	 * @return
	 * @throws ParseException 
	 */
	public MessageData convertToMessageDate(String[] csvData) throws ParseException {
		String priorityS = csvData[0];
		String subject = csvData[1];
		String content = csvData[2];
		String dateS = csvData[3];

		Date date = Utils.toDate(dateS);
		

		Integer priority = Integer.getInteger(priorityS);
		MessageData message = new MessageData(priority, subject, content, date);
		return message;

	}

}
