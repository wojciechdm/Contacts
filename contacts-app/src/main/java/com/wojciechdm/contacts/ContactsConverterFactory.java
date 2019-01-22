package com.wojciechdm.contacts;

public class ContactsConverterFactory {
	public ContactsConverter getContactsConverter(FileFormat fileFormat) {		
		switch (fileFormat){
		case CSV:
			return new CsvContactsConverter();
		case XML:
			return new XmlContactsConverter();
		default:
			throw new IllegalArgumentException("Illegal FileFormat: " + fileFormat);
		}
	}
}
