package com.ontotext.intervirew.core.parser;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.ontotext.intervirew.core.entity.Person;
import com.ontotext.intervirew.core.parser.DocumentParserException;
import com.ontotext.intervirew.core.parser.URLPersonParser;

public class URLPersonParserTest {

	@Test
	public void testWithHttpURL() throws MalformedURLException {
		URLPersonParser parser = new URLPersonParser(new URL("http://store4.data.bg/ruin/tasks/B.html"));		
		parser.firstNameColumnIndex(2);
		parser.lastNameColumnIndex(1);
		List<Person> people = parser.parse();
		assertTrue(people.size() > 0);
	}
	
	@Test(expected = DocumentParserException.class)
	public void testWithFileSystemURL() throws MalformedURLException {
		URL fileURL = getClass().getResource("/B.html");
		URLPersonParser parser = new URLPersonParser(fileURL);		
		parser.firstNameColumnIndex(2);
		parser.lastNameColumnIndex(1);		
		parser.parse();
	}

}
