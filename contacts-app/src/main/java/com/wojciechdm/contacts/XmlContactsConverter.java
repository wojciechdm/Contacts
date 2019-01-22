package com.wojciechdm.contacts;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XmlContactsConverter implements ContactsConverter {
	
	private Customer customer;
	private List<Contact> contactsList;
	private List<Customer> customersList;	
	
	public XmlContactsConverter() {
		customersList=new LinkedList<>();
	}
	
	@Override
	public void convertContacts(String... data) {
		DefaultHandler contactsHandler = new ContactsHandler();		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser;		
		customersList.clear();	
		
		try {
			saxParser = saxParserFactory.newSAXParser();
			for (String line: data) {
				if (line!=null) {
					saxParser.parse(new InputSource(new StringReader(line)), contactsHandler);
				}
			}			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Override	
	public List<Customer> getCustomersList(){		
		return customersList;
	}	
	
	private void addContact(Integer type, String data) {
		Contact contact=new Contact();
		contact.setType(type);
		contact.setContact(data);
		contactsList.add(contact);
	}
	
	private class ContactsHandler extends DefaultHandler{
		private Map<String, Boolean> elements = new HashMap<String, Boolean>();	
		
		public void startElement(String uri, String localName,String qName, 
	            Attributes attributes) throws SAXException {
			String qNameUpperCase=qName.toUpperCase();
			switch(qNameUpperCase) {
			case "PERSONS":					
				break;
			case "PERSON":
				customer=new Customer();
				contactsList=new LinkedList<>();
				break;
			default:
				elements.put(qNameUpperCase, true);				
			}			
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
			String qNameUpperCase=qName.toUpperCase();
			switch(qNameUpperCase) {
			case "PERSON":					
				customer.setContactsList(contactsList);
				customersList.add(customer);					
				break;
			default:
				elements.put(qNameUpperCase, false);				
			}	
		}
		
		public void characters(char ch[], int start, int length) throws SAXException {
			for(Map.Entry<String, Boolean> element : elements.entrySet()) {
				if (element.getValue()) {
					switch (element.getKey()) {
					case "NAME":
						customer.setName(new String(ch, start, length));
						break;
					case "SURNAME":
						customer.setSurname(new String(ch, start, length));
						break;
					case "AGE":
						customer.setAge(Integer.parseInt(new String(ch, start, length)));
						break;
					case "CITY":
						customer.setCity(new String(ch, start, length));
						break;
					case "EMAIL":
						addContact(CONTACT_TYPE_EMAIL, new String(ch, start, length));					
						break;
					case "PHONE":
						addContact(CONTACT_TYPE_PHONE, new String(ch, start, length));	
						break;
					case "JABBER":
						addContact(CONTACT_TYPE_JABBER, new String(ch, start, length));	
						break;
					case "CONTACTS":
						break;
					default:
						if (elements.containsKey("CONTACTS")) {
							if (elements.get("CONTACTS")) {
								addContact(CONTACT_TYPE_UNKNOWN, new String(ch, start, length));
							}
						}						
					}
				}
			}
		}
	}
	
}
