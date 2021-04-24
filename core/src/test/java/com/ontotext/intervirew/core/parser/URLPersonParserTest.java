package com.ontotext.intervirew.core.parser;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.ontotext.intervirew.core.entity.Person;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertTrue;

public class URLPersonParserTest {

	@Test
	public void testWithHttpURL() throws IOException, URISyntaxException {
		String html = IOUtils.toString(getClass().getResource("/B.html").toURI(), StandardCharsets.UTF_8);

		WireMockServer server = new WireMockServer(Options.DYNAMIC_PORT);
		server.start();
		server.stubFor(
				WireMock.get(urlEqualTo("/file"))
						.willReturn(aResponse()
								.withBody(html)));

		URLPersonParser parser = new URLPersonParser(new URL("http://localhost:" + server.port() + "/file"));
		parser.firstNameColumnIndex(2);
		parser.lastNameColumnIndex(1);
		List<Person> people = parser.parse();
		assertTrue(people.size() > 0);
	}
	
	@Test(expected = DocumentParserException.class)
	public void testWithFileSystemURL() {
		URL fileURL = getClass().getResource("/B.html");
		URLPersonParser parser = new URLPersonParser(fileURL);		
		parser.firstNameColumnIndex(2);
		parser.lastNameColumnIndex(1);
		List<Person> people = parser.parse();
		assertTrue(people.size() > 0);
	}

}
