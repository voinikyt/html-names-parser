package com.ontotext.intervirew.core.parser;



import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.ontotext.intervirew.core.entity.Person;

public class FileParserTest {

	/*
	 * Testing preconditions
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNonExistingFile() {		
		File file = new File("");
		new FilePersonParser(file, "UTF-8");	
	}	

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyCharsetFile() {		
		URL fileURL = getClass().getResource("/A.html");
		File file = new File(fileURL.getPath());
		if (file.exists()) {
			new FilePersonParser(file, "");	
		}
	}	
	
	@Test
	public void passNegativeOrZeroColumnIndexes() {
		URL fileURL = FileParserTest.class.getResource("/A.html");
		File file = new File(fileURL.getPath());
		FilePersonParser parser = new FilePersonParser(file, "UTF-8");
		assertNotNull(parser);
		IllegalArgumentException expected = null;
		try {
			parser.firstNameColumnIndex(-1);
		} catch (IllegalArgumentException e) {
			expected = e;
		}
		assertNotNull(expected);
		
		expected = null;
		try {
			parser.lastNameColumnIndex(0);
		} catch (IllegalArgumentException e) {
			expected = e;
		}
		assertNotNull(expected);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testParsingWithoutColumnIndexesInitialized() throws IOException {
		URL fileURL = getClass().getResource("/A.html");
		File file = new File(fileURL.getPath());	
		PersonParser parser = new FilePersonParser(file, "UTF-8");
		parser.parse();		
	}
	
	
	/*
	 * Testing functionality
	 */
	@Test
	public void parse() throws IOException {
		URL fileURL = getClass().getResource("/A.html");
		File file = new File(fileURL.getPath());	
		FilePersonParser parser = new FilePersonParser(file, "UTF-8");
		parser.firstNameColumnIndex(2);
		parser.lastNameColumnIndex(3);
		List<Person> people = parser.parse();
		assertEquals(2, people.size());
		assertEquals("Denise", people.get(0).getFirstName());
		assertEquals("Aaron", people.get(0).getLastName());
		assertEquals("Jason", people.get(1).getFirstName());
		assertEquals("Statem", people.get(1).getLastName());
	}
	
	/*
	 * Testing functionality
	 */
	@Test(expected = ColumnIndexOutOfBoundsException.class)
	public void testWithOutOfBoundsIndexes() throws IOException {
		URL fileURL = getClass().getResource("/A.html");
		File file = new File(fileURL.getPath());	
		FilePersonParser parser = new FilePersonParser(file, "UTF-8");
		parser.firstNameColumnIndex(100);
		parser.lastNameColumnIndex(101);
		parser.parse();
	}
}
