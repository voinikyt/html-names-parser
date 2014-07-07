package com.ontotext.intervirew.core.exporter;

import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.ontotext.intervirew.core.exporter.ExportFormat;
import com.ontotext.intervirew.core.exporter.PersonExporter;
import com.ontotext.intervirew.core.exporter.RDFPersonExporterFactory;
import com.ontotext.intervirew.core.parser.PersonParser;
import com.ontotext.intervirew.core.parser.PersonParserFactory;

public class OutputsteamExporterTest {
	
	@Test
	public void test() throws IOException {
		URL fileURL = this.getClass().getResource("/A.html");
		File file = new File(fileURL.getPath());
		
		PersonParser parser = PersonParserFactory.createParser(file, "UTF-8", 2, 3);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PersonExporter exporter = RDFPersonExporterFactory.createExporter(parser, ExportFormat.TURTLE);
		exporter.export(byteArrayOutputStream);
		
		String output = byteArrayOutputStream.toString("UTF-8");
		byteArrayOutputStream.close();				
		assertFalse(output.isEmpty());				
	}
}
