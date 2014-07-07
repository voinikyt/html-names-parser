package com.ontottext.interview.cltool;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class MainTest {	
	
	@Test
	public void testMainWithFile() throws IOException {
		String filePathA = JCommanderTest.class.getResource("/A.html").getFile();						
		File destination = File.createTempFile("destination", ".rdf");				
		
		assertTrue(destination.length() == 0);		
		
		String[] parameters = new String[] {
				"-file", filePathA,				
				"-firstNameColumn", "2",
				"-lastNameColumn", "3",
				"--destination", destination.getAbsolutePath(),
				"-format", "TURTLE"				
		};				
		int result = Main.mainMethod(parameters);
		
		assertEquals(0, result);
		assertTrue(destination.length() > 0);		
	}
	
	@Test
	public void testMainWithURL() throws IOException {							
		File destination = File.createTempFile("destination", ".rdf");				
		
		assertTrue(destination.length() == 0);		
		
		String[] parameters = new String[] {
				"-url", "http://store4.data.bg/ruin/tasks/A.html",				
				"-firstNameColumn", "2",
				"-lastNameColumn", "3",
				"--destination", destination.getAbsolutePath(),
				"-format", "TURTLE"				
		};				
		int result = Main.mainMethod(parameters);
		
		assertEquals(0, result);
		assertTrue(destination.length() > 0);		
	}
	
	@Test
	public void testUsage() throws IOException {						
		String[] parameters = new String[] {
				"-help"				
		};				
		int result = Main.mainMethod(parameters);
		
		assertEquals(0, result);			
	}

}
