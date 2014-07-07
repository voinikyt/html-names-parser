package com.ontotext.intervirew.core.parser;

import info.aduna.iteration.Iterations;

import org.junit.Test;
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

public class SesameTest {

	
	@Test
	public void test() throws RepositoryException {
		Repository rep = new SailRepository(new MemoryStore());
		rep.initialize();
					
		ValueFactory f  = rep.getValueFactory();
		
		String namespace = "http://example.org/";
		URI john = f.createURI(namespace, "john");
		
		RepositoryConnection conn = rep.getConnection();
		
		try {		
			conn.add(john, RDF.TYPE, FOAF.PERSON);
			conn.add(john, RDFS.LABEL, f.createLiteral("John", XMLSchema.STRING));
			conn.add(john, f.createURI("http://xmlns.com/foaf/0.1/firstName"), f.createLiteral("John", XMLSchema.STRING));
			conn.add(john, f.createURI("http://xmlns.com/foaf/0.1/lastName"), f.createLiteral("Smith", XMLSchema.STRING));
			conn.add(john, f.createURI("http://xmlns.com/foaf/0.1/lastName"), f.createLiteral("Wetson", XMLSchema.STRING));
						
			RepositoryResult<Statement> statements =  conn.getStatements(null, null, null, true);				
			
			Model model = Iterations.addAll(statements, new LinkedHashModel());
			
			model.setNamespace("rdf", RDF.NAMESPACE);
			model.setNamespace("rdfs", RDFS.NAMESPACE);
			model.setNamespace("xsd", XMLSchema.NAMESPACE);
			model.setNamespace("foaf", FOAF.NAMESPACE);
			model.setNamespace("ex", namespace);
			
			Rio.write(model, System.out, RDFFormat.TURTLE);		
		} catch (RDFHandlerException e) {
			e.printStackTrace();
			System.exit(1);			
		} finally {
			conn.close();
		}
	}
	
}
