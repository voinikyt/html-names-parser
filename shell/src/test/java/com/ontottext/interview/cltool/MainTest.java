package com.ontottext.interview.cltool;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class MainTest {

    @Test
    public void testMainWithFile() throws IOException {
        String filePathA = JCommanderTest.class.getResource("/A.html").getFile();
        File destination = File.createTempFile("destination", ".rdf");

        assertTrue(destination.length() == 0);

        String[] parameters = new String[]{
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
    public void testMainWithURL() throws IOException, URISyntaxException {
        String html = IOUtils.toString(getClass().getResource("/A.html").toURI(), StandardCharsets.UTF_8.displayName());

        WireMockServer server = new WireMockServer(Options.DYNAMIC_PORT);
        server.start();
        server.stubFor(
                WireMock.get(urlEqualTo("/file"))
                        .willReturn(aResponse()
                                .withBody(html)));

        File destination = File.createTempFile("destination", ".rdf");
        assertTrue(destination.length() == 0);

        String[] parameters = new String[]{
                "-url", "http://localhost:" + server.port() + "/file",
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
    public void testUsage() {
        String[] parameters = new String[]{
                "-help"
        };
        int result = Main.mainMethod(parameters);

        assertEquals(0, result);
    }

}
