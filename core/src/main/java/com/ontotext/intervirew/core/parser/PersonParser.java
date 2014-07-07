package com.ontotext.intervirew.core.parser;

import java.util.List;

import com.ontotext.intervirew.core.entity.Person;

public interface PersonParser {

	/**
	 * @return
	 * @throws IllegalStateException if firstNameColumnIndex or lastNameColumnIndex have not been initialized
	 * @throws ColumnIndexOutOfBoundsException if the indexes provided are greater than the number of cells in a row
	 * @throws DocumentParserException if document cannot be passed 
	 */
	public abstract List<Person> parse();

	/**
	 * @param i - the index of the, column starting from 1
	 * @throws IllegalArgumentException if the column is less than 1
	 */
	public abstract void lastNameColumnIndex(int i);

	/**
	 * @param i - the index of the, column starting from 1
	 * @throws IllegalArgumentException if the column is less than 1
	 */
	public abstract void firstNameColumnIndex(int i);

}