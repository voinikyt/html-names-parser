package com.ontotext.intervirew.core.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.ontotext.intervirew.core.parser.FilePersonParser;
import com.ontotext.intervirew.core.parser.PersonParser;
import com.ontotext.intervirew.core.parser.PersonParserFactory;
import com.ontotext.intervirew.core.parser.URLPersonParser;

public class ParserFactoryTest {

	@Test
	public void testFilePersonParser() {
		URL fileURL = getClass().getResource("/A.html");
		File file = new File(fileURL.getPath());
		PersonParser parser = PersonParserFactory.createParser(file, "UTF-8", 2, 3);
		assertTrue(parser instanceof FilePersonParser);
	}
	
	@Test
	public void testURLPersonParser() throws MalformedURLException {
		URL url = new URL("http://store4.data.bg/ruin/tasks/B.html");
		PersonParser parser = PersonParserFactory.createParser(url, 1, 2);
		assertTrue(parser instanceof URLPersonParser);
	}

}
