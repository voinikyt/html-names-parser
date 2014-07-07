package com.ontotext.intervirew.core.parser;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class URLPersonParser extends AbstractPersonParser {
	private static final int TIME_OUT_MILLISECONDS = 10000;
	
	private URL url;

	/**
	 * @throws NullPointerException if the url parameter is null
	 * @param url
	 */
	URLPersonParser(URL url) {
		super();
		if (url == null) {
			throw new NullPointerException("url cannot be null");
		}
		this.url = url;
	}

	/**
	 * @throws DocumentParserException if document cannot be passed
	 * @return {@link Document} representing the HTML file
	 */
	@Override
	protected Document getJSoupDocument() {
		try {
			return Jsoup.parse(url, TIME_OUT_MILLISECONDS);
		} catch (IOException e) {
			throw new DocumentParserException("Document could not be parsed: " + e.getMessage() , e);
		}
	}	
}
