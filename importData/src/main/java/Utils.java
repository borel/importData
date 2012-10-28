package main.java;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	/**
	 * Convert a string to a date
	 * @param dateS
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String dateS) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		date = dateFormat.parse(dateS);
		return date;
	}

}
