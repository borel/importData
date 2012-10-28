package main.java;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import main.ressources.Ressources;

public class Directory {

	/**
	 * Get the CSV file in repository
	 * 
	 * @return collection of file
	 */
	public Collection<File> getCSVFileInRepertory(File directory) {
		Collection<File> collectionCSVFile = new ArrayList<File>();
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (isCsv(file)) {
					collectionCSVFile.add(file);
				}
			}
		}
		return collectionCSVFile;
	}

	/**
	 * Get the XML file in repository
	 * 
	 * @return collection of file
	 */
	public Collection<File> getXmlFileInRepertory(File directory) {
		Collection<File> collectionXmlFile = new ArrayList<File>();
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (isXml(file)) {
					collectionXmlFile.add(file);
				}
			}
		}
		return collectionXmlFile;
	}
	
	/**
	 * Test if the file is a xml file
	 * @param file
	 * @return true if the file is a xml file else false
	 */
	public static boolean isXml(File file) {
		return isType(Ressources.XML, file);
	}
	
	/**
	 * Test if the file is a csv file
	 * @param file
	 * @return true if the file is a xml file else false
	 */
	public static boolean isCsv(File file) {
		return isType(Ressources.CSV, file);
	}
	
	/**
	 * Test the type of the file
	 * @param type
	 * @param file
	 * @return true if the file is from the type in parameter file else false
	 */
	public static boolean isType(String type, File file) {
		file.getName();
		int posPoint = file.getName().lastIndexOf('.');
		if (0 < posPoint
				&& posPoint <= file.getName().length() - 2
				&& file.getName().substring(posPoint + 1).equals(
						type.toLowerCase())) {
			return true;
		}
		return false;
	}

}
