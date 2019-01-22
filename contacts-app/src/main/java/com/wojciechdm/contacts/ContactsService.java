package com.wojciechdm.contacts;

import java.io.*;
import java.util.List;

public class ContactsService {
	private ContactsDao contactsDao;	
	private ContactsReaderFactory contactsReaderFactory;
	private ContactsConverterFactory contactsConverterFactory;
	private FileFormatChecker fileFormatChecker;
	
	public ContactsService() {
		contactsDao=new ContactsDao();
		contactsReaderFactory=new ContactsReaderFactory();
		contactsConverterFactory=new ContactsConverterFactory();
		fileFormatChecker=new FileFormatChecker();
	}
	
	public void addContactsFromFileToDatabase(String path) {
		List<Customer> customersList;
		String[] data;
		FileFormat fileFormat=fileFormatChecker.getFileFormat(path);
		ContactsConverter contactsConverter=contactsConverterFactory.getContactsConverter(fileFormat);
		
		try (ContactsReader contactsReader=contactsReaderFactory.getContactsReader(path, fileFormat)){			
			while(contactsReader.hasMoreData()) {
				data=contactsReader.readData();
				contactsConverter.convertContacts(data);
				customersList=contactsConverter.getCustomersList();
				setCustomerId(customersList);
				contactsDao.saveContacts(customersList);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setCustomerId(List<Customer> customersList){
		Long customerId=contactsDao.getLastCustomerId();
		List<Contact> contactsList;
		
		for(Customer customer: customersList) {
			customerId++;
			customer.setId(customerId);
			contactsList=customer.getContactsList();
			for (Contact contact: contactsList) {
				contact.setCustomerId(customerId);
			}
		}
	}
}
