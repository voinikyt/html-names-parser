package com.ontotext.intervirew.core.parser;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ontotext.intervirew.core.entity.Person;

abstract class AbstractPersonParser implements PersonParser {
	private int firstNameColumnIndex;
	private int lastNameColumnIndex;

	/**
	 * @throws DocumentParserException if document cannot be passed
	 * @return {@link Document} representing the HTML file
	 */
	protected abstract Document getJSoupDocument();		
	
	/**
	 * @return
	 * @throws IllegalStateException if firstNameColumnIndex or lastNameColumnIndex have not been initialized
	 * @throws ColumnIndexOutOfBoundsException if the indexes provided are greater than the number of cells in a row
	 * @throws DocumentParserException if document cannot be passed 
	 */
	@Override
	public List<Person> parse() {
		if (firstNameColumnIndex < 1 || lastNameColumnIndex < 1) {
			throw new IllegalStateException("You have to initialize firstNameColumnIndex and lastNameColumnIndex");
		}
		List<Person> people = new LinkedList<>();
		Document fileDocument = getJSoupDocument();
		Elements firstNames = fileDocument.select("table tr");
		for (Element tr : firstNames) {
			if (firstNameColumnIndex > tr.children().size() || lastNameColumnIndex > tr.children().size()) {
				throw new ColumnIndexOutOfBoundsException("The columns are greater than the number of cells in a row");
			}
			if (tr.children().size() > 0 && "th".equalsIgnoreCase(tr.children().get(0).tagName())) continue;
			String firstName = tr.child(firstNameColumnIndex - 1).text();
			String lastName = tr.child(lastNameColumnIndex - 1).text();
			people.add(new Person(firstName, lastName));
		}
		return people;
	}
		
	/**
	 * Index of the column in the table(starting from 1)
	 * @throws IllegalArgumentException if the index is less than 1
	 */
	@Override
	public void firstNameColumnIndex(int i) {
		if (i < 1) {
			throw new IllegalArgumentException("firstNameColumnIndex shoyld be greater than 1");
		}
		this.firstNameColumnIndex = i; 		
	}

	/**
	 * Index of the column in the table(starting from 1)
	 * @throws IllegalArgumentException if the index is less than 1
	 */
	@Override
	public void lastNameColumnIndex(int i) {
		if (i < 1) {
			throw new IllegalArgumentException("lastNameColumnIndex shoyld be greater than 1");
		}
		this.lastNameColumnIndex = i;		
	}

}