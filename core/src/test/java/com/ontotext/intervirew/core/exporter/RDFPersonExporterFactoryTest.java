package com.ontotext.intervirew.core.exporter;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.ontotext.intervirew.core.exporter.ExportFormat;
import com.ontotext.intervirew.core.exporter.PersonExporter;
import com.ontotext.intervirew.core.exporter.RDFPersonExporterFactory;
import com.ontotext.intervirew.core.parser.PersonParserFactory;

public class RDFPersonExporterFactoryTest {
	File source;
	
	@Before
	public void init() throws IOException {
		source = new File(getClass().getResource("/A.html").getFile());
	}
	
	@Test
	public void testFileExporter() throws IOException {				
		File target = File.createTempFile("export", ".rdf");
		OutputStream output = new FileOutputStream(target);
		assertTrue( target.length() == 0);
		
		PersonExporter exporter = RDFPersonExporterFactory.createExporter(
				PersonParserFactory.createUTF8Parser(source, 2, 3), 				
				ExportFormat.RDFJSON);		
		exporter.export(output);				
		output.close();
		
		assertTrue(target.length() > 0);
		//TODO The file should be parser by an RDF parser and then the data should be asserted
	}
	
	
	@Test
	public void testPlainText() throws IOException {				
		File target = File.createTempFile("export", ".txt");		
		
		OutputStream output = new FileOutputStream(target);
		assertTrue( target.length() == 0);
		
		PersonExporter exporter = RDFPersonExporterFactory.createExporter(
				PersonParserFactory.createUTF8Parser(source, 2, 3), 				
				ExportFormat.PLAIN);
		
		exporter.export(output);			
		output.close();
		
		assertTrue(target.length() > 0);	
		//TODO The file should be parser by an RDF parser and then the data should be asserted
	}
	
	@Test
	public void testConsoleExporter() throws IOException {				
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(byteArrayOutputStream);
		System.setOut(out);
		
		String outputString = byteArrayOutputStream.toString("UTF-8");
		assertTrue(outputString == null || outputString.isEmpty());
		
		PersonExporter exporter = RDFPersonExporterFactory.createExporter(
				PersonParserFactory.createUTF8Parser(source, 2, 3), 			
				ExportFormat.RDFJSON);		
		exporter.export(System.out);				
		
		
		outputString = byteArrayOutputStream.toString("UTF-8");
		assertTrue(!outputString.isEmpty());		
		out.close();
		//TODO The stream should be parser by an RDF parser and then the data should be asserted
	}
	
	@Test
	public void testOutputStreamExporter() throws IOException {				
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		String outputString = byteArrayOutputStream.toString("UTF-8");
		assertTrue(outputString == null || outputString.isEmpty());
		
		PersonExporter exporter = RDFPersonExporterFactory.createExporter(
				PersonParserFactory.createUTF8Parser(source, 2, 3),
				ExportFormat.RDFJSON);		
		exporter.export(byteArrayOutputStream);				
		
		
		outputString = byteArrayOutputStream.toString("UTF-8");
		assertTrue(!outputString.isEmpty());	
		byteArrayOutputStream.close();
		//TODO The stream should be parser by an RDF parser and then the data should be asserted
	}

}
