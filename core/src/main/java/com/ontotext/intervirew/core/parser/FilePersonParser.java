package com.ontotext.intervirew.core.parser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class FilePersonParser extends AbstractPersonParser {
	private File file;
	private String charset;

	/**
	 * @throws IllegalArgumentException file is null ot not existing; if charset is empty string or null
	 * @param file
	 * @param charset
	 */
	FilePersonParser(File file, String charset) {
		super();
		if (file == null || !file.exists()) {
			throw new IllegalArgumentException();
		}
		if (charset == null || charset.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.file = new File(file.getPath());
		this.charset = charset;
	}
	
	/**
	 * @throws DocumentParserException if document cannot be passed
	 * @return {@link Document} representing the HTML file
	 */
	@Override
	protected Document getJSoupDocument() {
		try {
			return Jsoup.parse(file, charset);
		} catch (IOException e) {
			throw new DocumentParserException("Cannot parse file: " + e.getMessage(), e); 
		}
	}

}
