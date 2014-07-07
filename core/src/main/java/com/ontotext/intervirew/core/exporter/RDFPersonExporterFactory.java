package com.ontotext.intervirew.core.exporter;

import com.ontotext.intervirew.core.parser.PersonParser;


public class RDFPersonExporterFactory {	
	/**
	 * @param parser
	 * @param exportFormat
	 * @return
	 * @throws NullPointerException if either of parser or exportFormat is null 
	 */
	public static PersonExporter createExporter(PersonParser parser, ExportFormat exportFormat) {
		return new OutputStreamExporter(parser, exportFormat);
	}
}
