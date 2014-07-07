package com.ontottext.interview.cltool;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.ontotext.intervirew.core.exporter.ExportFormat;

class CommandLineParameters {	
	public static final String exportFormatDescription;
	
	static {
		StringBuilder builder = new StringBuilder("The export RDF format: ");
		ExportFormat[] values = ExportFormat.values();
		for (int i = 0; i < values.length; i++) {
			builder.append(values[i].name());
			if (i != values.length - 1)
				builder.append(", ");
		}			
		exportFormatDescription = builder.toString();
	}
	
	@Parameter
	private List<String> parameters = new ArrayList<String>();
	
	@Parameter(
			names = "-file", 
			description = "Specify the input file location",			
			converter = CommandLineParameters.FileConverter.class, 
			validateWith = CommandLineParameters.FileValidator.class)
	private File file;
	
	@Parameter(
			names = "-url", 
			description = "Internet address of web page",			
			converter = URLConverter.class, 
			validateWith = URLValidator.class)
	private URL url;
	
	@Parameter(
			names = "-firstNameColumn",
			description = "The sequance number of the column containing first names information. Starting from 1",
			required = true,
			validateWith = IntGreaterThanOneValidator.class)
	private int firstNameColumnIndex;
	
	@Parameter(
			names = "-lastNameColumn",
			description = "The sequance number of the column containing last name information. Starting from 1",
			required = true,
			validateWith = IntGreaterThanOneValidator.class)
	private int lastNameColumnIndex;
	
	@Parameter(
			names = {"-d", "--destination"}, 
			description = "Specify the destination file where the RDF will be written",			
			converter = CommandLineParameters.FileConverter.class)
	private File destination;
	
	@Parameter(
			names = "-format",			
			description = "The RDF format of the data. Type usage to see all format names",
			required = true,
			converter = CommandLineParameters.ExportFormatConverter.class,
			validateWith = CommandLineParameters.ExportFormatValidator.class)
	private ExportFormat exportFormat; 
	
	@Parameter(names = {"-help", "--help"}, description = "Receive information about the available options", help = true)
	private boolean help;
	
	public static final class FileConverter implements IStringConverter<File> {
		@Override
		public File convert(String value) {				
			return new File(value);
		}			
	}
	
	public static final class FileValidator implements IParameterValidator {
		@Override
		public void validate(String name, String value) throws ParameterException {
			if (! new File(value).exists()) {
				throw new ParameterException("Parameter " + name + " should be an existing file");
			}
		}			
	}
	
	public static final class ExportFormatConverter implements IStringConverter<ExportFormat> {
		@Override
		public ExportFormat convert(String value) {								
			return ExportFormat.valueOf(value);								
		}			
	}
	
	public static final class ExportFormatValidator implements IParameterValidator {
		@Override
		public void validate(String name, String value) throws ParameterException {
			try {
				ExportFormat.valueOf(value);
			} catch (IllegalArgumentException e) {
				throw new ParameterException("Invalid format name: " + value + " . Valid formats are: " + exportFormatDescription);
			}
		}			
	}
	
	public static final class URLConverter implements IStringConverter<URL> {
		@Override
		public URL convert(String value) {								
			try {
				return new URL(value);
			} catch (MalformedURLException e) {
				throw new ParameterException("Invalid URL parameter: " + value);
			}								
		}			
	}
	
	public static final class URLValidator implements IParameterValidator {
		@Override
		public void validate(String name, String value) throws ParameterException {
			try {
				new URL(value);
			} catch (MalformedURLException e) {
				throw new ParameterException("Invalid URL parameter: " + value);
			}
		}			
	}
	
	public static final class IntGreaterThanOneValidator implements IParameterValidator {
		@Override
		public void validate(String name, String value) throws ParameterException {
			int n = Integer.parseInt(value);
			if (n < 1) {
				throw new ParameterException("Parameter " + name + " should be greater than 0");
			}			 
		}			
	}

	//Getters
	public List<String> getParameters() {
		return parameters;
	}

	public File getFile() {
		File f = null;
		if (this.file != null) 
			f = new File(this.file.getPath());
		return f;
	}

	public URL getUrl() {
		return url;
	}

	public int getFirstNameColumnIndex() {
		return firstNameColumnIndex;
	}

	public int getLastNameColumnIndex() {
		return lastNameColumnIndex;
	}

	public File getDestination() {
		return destination;
	}

	public ExportFormat getExportFormat() {
		return exportFormat;
	}	
}