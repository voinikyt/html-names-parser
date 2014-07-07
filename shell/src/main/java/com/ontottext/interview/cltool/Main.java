package com.ontottext.interview.cltool;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.ParameterException;
import com.ontotext.intervirew.core.exporter.RDFExportException;
import com.ontotext.intervirew.core.exporter.PersonExporter;
import com.ontotext.intervirew.core.exporter.RDFPersonExporterFactory;
import com.ontotext.intervirew.core.exporter.UnsupportedFormatExceptoion;
import com.ontotext.intervirew.core.parser.PersonParser;
import com.ontotext.intervirew.core.parser.PersonParserFactory;

public class Main {
	
	public static void main(String[] args) {
		int result = mainMethod(args);
		System.exit(result);
	}
	
	public static int mainMethod(String[] args) {
		CommandLineParameters parameters = new CommandLineParameters();
		JCommander shell = new JCommander(parameters);
 		try {			
			shell.parse(args);					
		} catch (ParameterException e) {
			printErrorMessage(shell, e);
			return 1;
		}		
 		
 		for (ParameterDescription pd : shell.getParameters()) {
 			if (pd.isAssigned() && pd.isHelp()) {
 				printUsage(shell);
 				return 0;
 			}
 		}
	
		PersonParser parser = null;		
		if (parameters.getFile() != null) {
			parser = PersonParserFactory.createUTF8Parser(
					parameters.getFile(), 
					parameters.getFirstNameColumnIndex(),
					parameters.getLastNameColumnIndex());		
		} else if (parameters.getUrl() != null) {
			parser = PersonParserFactory.createParser(
					parameters.getUrl(), 
					parameters.getFirstNameColumnIndex(),
					parameters.getLastNameColumnIndex());
		} else {
			JCommander.getConsole().println("You have not provided neither -file parameter nor -url parameter.");
			printUsage(shell);
			return 1;
		}
		
		PersonExporter exporter = RDFPersonExporterFactory.createExporter(
				parser, 
				parameters.getExportFormat());			
		
		OutputStream outputStream = null;
		try {				
			if (parameters.getDestination() != null) {						
				if ( !parameters.getDestination().exists() ) {
					parameters.getDestination().createNewFile();
				}
				outputStream = new FileOutputStream(parameters.getDestination());																
			} else {
				outputStream = System.out;
			}
			exporter.export(outputStream);
			return 0;
		} catch (IOException e) {
			JCommander.getConsole().println("Cannot export to " + parameters.getDestination().getAbsolutePath());
			JCommander.getConsole().println(e.getMessage());
			return 1;
		} catch (UnsupportedFormatExceptoion|RDFExportException|NullPointerException e) {
			JCommander.getConsole().println("An error has hppened during export");
			JCommander.getConsole().println(e.getMessage());
			return 1;
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				JCommander.getConsole().println("The command could not close the destination");
				return 1;
			}
		}
	}
	
	private static void printErrorMessage(JCommander shell, Exception e) {
		JCommander.getConsole().println(e.getMessage());
		printUsage(shell);
	}

	private static void printUsage(JCommander shell) {
		shell.usage();
		JCommander.getConsole().println("Available formats are: " + CommandLineParameters.exportFormatDescription);
		JCommander.getConsole().println("");
		JCommander.getConsole().println("");
		JCommander.getConsole().println("Examples:");
		JCommander.getConsole().println("");
		JCommander.getConsole().println("Console:");
		JCommander.getConsole().println("rdf.bat -url http://store4.data.bg/ruin/tasks/A.html -firstNameColumn 2 -lastNameColumn 3 -format RDFXML:");
		JCommander.getConsole().println("File:");
		JCommander.getConsole().println("rdf.bat -file A.html -firstNameColumn 2 -lastNameColumn 3 -format RDFXML -d A.xml");
		JCommander.getConsole().println("Plain:");
		JCommander.getConsole().println("rdf.bat -url http://store4.data.bg/ruin/tasks/A.html -firstNameColumn 2 -lastNameColumn 3 -format PLAIN -d A.txt");
		JCommander.getConsole().println("Help:");
		JCommander.getConsole().println("rdf.bat --help");
		JCommander.getConsole().println("!!!WARNING: If destinationis not proivded the default output is the console");
	}
}
