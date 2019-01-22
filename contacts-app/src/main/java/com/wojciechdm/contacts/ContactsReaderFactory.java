package com.wojciechdm.contacts;

import java.io.*;

public class ContactsReaderFactory {
	public ContactsReader getContactsReader(String path, FileFormat fileFormat) throws FileNotFoundException {		
		switch (fileFormat){
		case CSV:
			return new CsvContactsReader(path);
		case XML:
			return new XmlContactsReader(path);
		default:
			throw new IllegalArgumentException("Illegal FileFormat: " + fileFormat);
		}
	}
}
