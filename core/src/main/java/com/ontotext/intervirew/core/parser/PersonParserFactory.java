package com.ontotext.intervirew.core.parser;

import java.io.File;
import java.net.URL;

public final class PersonParserFactory {
	
	/**
	 * @param file - file located on the file system. which is HTML page containing 1 table with the desired columns
	 * @param charset - the charset of the file
	 * @param firstNameColumnIndex - index of the column(starting from 1)
	 * @param lastNameColumnIndex - index of the column(starting from 1)
	 * @return Appropriate implementation of {@link PersonParser}
	 * @throws IllegalArgumentException if file is null or not existing
	 * @throws IllegalArgumentException charset is empty or null
	 * @throws IllegalArgumentException firstNameColumnIndex or lastNameColumnIndex are less than 1
	 */
	public static PersonParser createParser(File file, String charset, int firstNameColumnIndex, int lastNameColumnIndex) {
		FilePersonParser parser = new FilePersonParser(file, charset);
		parser.firstNameColumnIndex(firstNameColumnIndex);
		parser.lastNameColumnIndex(lastNameColumnIndex);
		return parser;
	}
	
	/**
	 * @param file - file located on the file system. which is HTML page containing 1 table with the desired columns 
	 * @param firstNameColumnIndex - index of the column(starting from 1)
	 * @param lastNameColumnIndex - index of the column(starting from 1)
	 * @return Appropriate implementation of {@link PersonParser}
	 * @throws IllegalArgumentException if file is null or not existing 
	 * @throws IllegalArgumentException firstNameColumnIndex or lastNameColumnIndex are less than 1
	 */
	public static PersonParser createUTF8Parser(File file, int firstNameColumnIndex, int lastNameColumnIndex) {
		FilePersonParser parser = new FilePersonParser(file, "UTF-8");
		parser.firstNameColumnIndex(firstNameColumnIndex);
		parser.lastNameColumnIndex(lastNameColumnIndex);
		return parser;
	}

	/**
	 * @param url - url of a page located online
	 * @param firstNameColumnIndex - index of the column(starting from 1)
	 * @param lastNameColumnIndex - index of the column(starting from 1)
	 * @return Appropriate implementation of {@link PersonParser}
	 * @throws NullPointerException if url is null 
	 * @throws IllegalArgumentException firstNameColumnIndex or lastNameColumnIndex are less than 1
	 */
	public static PersonParser createParser(URL url, int firstNameColumnIndex, int lastNameColumnIndex) {
		URLPersonParser parser = new URLPersonParser(url);
		parser.firstNameColumnIndex(firstNameColumnIndex);
		parser.lastNameColumnIndex(lastNameColumnIndex);
		return parser;
	}
}
