package com.ontotext.intervirew.core.exporter;

import java.io.OutputStream;



public interface PersonExporter {
	
	/**
	 * @param outputStream - non null output stream
	 * @throws UnsupportedFormatExceptoion if the {@link ExportFormat} is not supported
	 * @throws RDFExportException if something has gone wrong with the export
	 * @throws NullPointerException if the stream is null
	 */
	public void export(OutputStream outputStream);		

	public ExportFormat getExportFormat();
}
