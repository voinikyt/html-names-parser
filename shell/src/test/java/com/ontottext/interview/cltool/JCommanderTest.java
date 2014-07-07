package com.ontottext.interview.cltool;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.beust.jcommander.JCommander;
import com.ontotext.intervirew.core.exporter.ExportFormat;

public class JCommanderTest {

	@Test
	public void testCorrectExecution() throws IOException {		
		String filePathA = JCommanderTest.class.getResource("/A.html").getFile();						
		File destination = File.createTempFile("destination", ",rdf");		
		String urlPath = "http://store4.data.bg/ruin/tasks/A.html";
		
		CommandLineParameters parameters = new CommandLineParameters();		
		new JCommander(parameters, new String[]{
				"-file", filePathA,
				"-url", urlPath,
				"-firstNameColumn", "2",
				"-lastNameColumn", "3",
				"--destination", destination.getAbsolutePath(),
				"-format", "TURTLE"				
		});
		assertEquals(new File(filePathA).getPath(), parameters.getFile().getPath());
		assertEquals(new URL(urlPath), parameters.getUrl());	
		assertEquals(2, parameters.getFirstNameColumnIndex());
		assertEquals(3, parameters.getLastNameColumnIndex());
		assertEquals(destination.getPath(), parameters.getDestination().getPath());
		assertEquals(ExportFormat.TURTLE, parameters.getExportFormat());
	}
}
