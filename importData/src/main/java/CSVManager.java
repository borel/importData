package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
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
	public List<String[]> getDatas(Collection<File> files) {
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
	public List<String[]> getDatas(File file) {

		FileReader fileReader;
		List<String[]> datas = new ArrayList<String[]>();

		try {
			fileReader = new FileReader(file);

			// Put the separator of the CSV file
			CSVReader csvReader = new CSVReader(fileReader,
					Ressources.SEPARATOR);

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
		} catch (FileNotFoundException e) {
			Log.traceFileCorrupt(file);
		} catch (IOException e) {
			Log.traceFileCorrupt(file);
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
	public Collection<MessageData> getMessagesDatas(Collection<File> files) {
		Collection<MessageData> messagesDatas = new ArrayList<MessageData>();

		for (File file : files) {
			List<String[]> datas = getDatas(file);
			for (String[] csvData : datas) {
				MessageData newMessageData = convertToMessageData(csvData,file);
				if(newMessageData != null){
					messagesDatas.add(newMessageData);
				}
			}
		}

		return messagesDatas;
	}
	
	/**
	 * convert csvData to MessageData Object
	 * @param csvData
	 * @return
	 */
	public MessageData convertToMessageData(String[] csvData, File file) {

		MessageData message = null;
		String subject = null;
		String content = null;
		String dateS = null;
		String priorityS = null;

		try {

			Date date;

			priorityS = csvData[0];
			subject = csvData[1];
			content = csvData[2];
			dateS = csvData[3];

			int priority = Integer.valueOf(priorityS);
			date = Utils.toDate(dateS);
			message = new MessageData(priority, subject, content, date);
		} catch (ParseException e) {
			message = new MessageData(null, subject, content, null);
			Log.traceDateError(message, file, dateS);
			return null;
		} catch (NumberFormatException e) {
			message = new MessageData(null, subject, content, null);
			Log.tracePriorityError(message, file, priorityS);
			return null;
		} catch (Exception e) {
			message = new MessageData(null, subject, content, null);
			Log.traceError(message, file);
			return null;
		}

		return message;

	}

}
