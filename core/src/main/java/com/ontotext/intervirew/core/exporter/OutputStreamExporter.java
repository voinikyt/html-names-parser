package com.ontotext.intervirew.core.exporter;

import info.aduna.iteration.Iterations;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.Rio;
import org.openrdf.sail.memory.MemoryStore;

import com.ontotext.intervirew.core.entity.Person;
import com.ontotext.intervirew.core.parser.PersonParser;

class OutputStreamExporter extends AbstractExporter {	
	
	/**
	 * @throws NullPointerException if any of the constructor's parameters is null
	 * @param parser
	 * @param outputStream
	 * @param exportFormat
	 */
	OutputStreamExporter(PersonParser parser, ExportFormat exportFormat) {
		super(parser, exportFormat);
	}

	/**
	 * The output stream should be closed by the client.
	 * @throws NullPointerException if the outputStream is null
	 * @throws UnsupportedFormatExceptoion if the {@link ExportFormat} is not supported
	 * @throws RDFExportException if something has gone wrong with the export 
	 */
	@Override
	public void export(OutputStream outputStream) {		
		if (outputStream == null) {
			throw new NullPointerException("The outputStream should not be null");
		}
		
		List<Person> people = getParser().parse();

		if (getExportFormat() == ExportFormat.PLAIN) {
			exportPlainText(outputStream, people);			
		} else {
			exportRDF(outputStream, people);
		}
	}

	private void exportRDF(OutputStream outputStream, List<Person> people) {
		Repository rep = new SailRepository(new MemoryStore());
		try {
			rep.initialize();
		} catch (RepositoryException e) {
			throw new RDFExportException(
					"Could not export RDF. The real problem is in the stack trace",
					e);
		}

		RepositoryConnection conn = null;
		try {
			conn = rep.getConnection();
		} catch (RepositoryException e) {
			throw new RDFExportException(
					"Could not export RDF. The real problem is in the stack trace",
					e);
		}

		ValueFactory f = rep.getValueFactory();

		try {
			int uriIndex = 0;
			for (Person person : people) {
				uriIndex++;
				URI personURI = f.createURI(FOAF.NAMESPACE,
						String.valueOf(uriIndex));
				conn.add(personURI, RDF.TYPE, FOAF.PERSON);
				conn.add(personURI, RDFS.LABEL, f.createLiteral(
						person.getFirstName(), XMLSchema.STRING));
				conn.add(personURI, f
						.createURI("http://xmlns.com/foaf/0.1/firstName"), f
						.createLiteral(person.getFirstName(), XMLSchema.STRING));
				conn.add(personURI,
						f.createURI("http://xmlns.com/foaf/0.1/lastName"),
						f.createLiteral(person.getLastName(), XMLSchema.STRING));
			}

			RepositoryResult<Statement> statements = conn.getStatements(null,
					null, null, true);

			Model model = Iterations.addAll(statements, new LinkedHashModel());

			model.setNamespace("rdf", RDF.NAMESPACE);
			model.setNamespace("rdfs", RDFS.NAMESPACE);
			model.setNamespace("xsd", XMLSchema.NAMESPACE);
			model.setNamespace("foaf", FOAF.NAMESPACE);

			Rio.write(model, outputStream, mapToRDFFormat());
		} catch (RepositoryException e) {
			throw new RDFExportException(
					"Could not export RDF. The real problem is in the stack trace",
					e);
		} catch (RDFHandlerException e) {
			throw new RDFExportException(
					"Could not export RDF to the output destination", e);
		} finally {
			try {
				conn.close();
			} catch (RepositoryException e) {
				throw new Error("SESAM repository closing failed", e);
			}
		}
	}

	private void exportPlainText(OutputStream outputStream, List<Person> people) {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));			
		for (Person person : people) {
			String names = String.format("%s %s", person.getFirstName(), person.getLastName());
			writer.println(names);			
		}
		writer.flush();
	}

	private RDFFormat mapToRDFFormat() {
		RDFFormat format = null;
		switch (getExportFormat()) {
		case RDFXML:
			format = RDFFormat.RDFXML;
			break;
		case NTRIPLES:
			format = RDFFormat.NTRIPLES;
			break;
		case TURTLE:
			format = RDFFormat.TURTLE;
			break;
		case N3:
			format = RDFFormat.N3;
			break;
		case TRIX:
			format = RDFFormat.TRIX;
			break;
		case TRIG:
			format = RDFFormat.TRIG;
			break;
		case BINARY:
			format = RDFFormat.BINARY;
			break;
		case NQUADS:
			format = RDFFormat.NQUADS;
			break;
		case RDFJSON:
			format = RDFFormat.RDFJSON;
			break;		
		default:
			throw new UnsupportedFormatExceptoion(getExportFormat() + " is not supported");		
		}
		
		return format;
	}
}
