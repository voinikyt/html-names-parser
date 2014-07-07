package com.ontotext.intervirew.core.exporter;

import com.ontotext.intervirew.core.parser.PersonParser;

abstract class AbstractExporter implements PersonExporter {
	private PersonParser parser;	
	private ExportFormat exportFormat;
	
	/**
	 * @throws NullPointerException if any of the constructor's parameters is null
	 * @param parser
	 * @param exportFormat
	 */
	AbstractExporter(PersonParser parser, ExportFormat exportFormat) {
		if (parser == null) {
			throw new NullPointerException("parser should not be null");
		}
		if (exportFormat == null) {
			throw new NullPointerException("exportFormat should not be null");
		}
		this.parser = parser;		
		this.exportFormat = exportFormat;
	}

	protected PersonParser getParser() {
		return parser;
	}	

	@Override
	public final ExportFormat getExportFormat() {
		return exportFormat;
	}				
}
