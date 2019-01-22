package com.wojciechdm.contacts;

import java.util.*;
import java.util.regex.*;

public class CsvContactsConverter implements ContactsConverter {
		
	private static final Pattern DATA_PATTERN_CSV=
			Pattern.compile("(?<name>.+?),(?<surname>.+?),(?<age>\\d*?),(?<city>.+?)(?:,(.+))");
	private static final Pattern CONTACT_PATTERN_EMAIL=Pattern.compile(".+@.+\\.\\w+");
	private static final Pattern CONTACT_PATTERN_PHONE=Pattern.compile("\\d{9,}");
	private static final Pattern CONTACT_PATTERN_UNKNOWN=Pattern.compile("\\d{1,8}");	
	private List<Customer> customersList;	
	
	public CsvContactsConverter() {
		this.customersList=new LinkedList<>();
	}
	
	@Override
	public void convertContacts(String... data) {
		Matcher matcher;
		StringTokenizer contactData;
		Customer customer;
		Contact contact;
		List<Contact> contactsList;
		customersList.clear();
		
		for(String line:data) {
			if (line!=null) {
				customer=new Customer();
				contactsList=new LinkedList<>();
				matcher=DATA_PATTERN_CSV.matcher(line);
				if (matcher.matches()) {
					customer.setName(matcher.group("name"));
					customer.setSurname(matcher.group("surname"));
					if(!matcher.group("age").equals("")) {
						customer.setAge(Integer.parseInt(matcher.group("age")));
					}
					customer.setCity(matcher.group("city"));		
					contactData=new StringTokenizer(matcher.group(matcher.groupCount()),",");
					while(contactData.hasMoreTokens()) {
						contact=new Contact();
						contact.setContact(contactData.nextToken());
						contact.setType(getContactType(contact.getContact()));
						contactsList.add(contact);			
					}
					customer.setContactsList(contactsList);
				}		
				customersList.add(customer);
			}			
		}		
	}
	
	@Override
	public List<Customer> getCustomersList(){		
		return customersList;
	}
	
	private Integer getContactType(String contact) {		
		if (CONTACT_PATTERN_EMAIL.matcher(contact).matches()){
			return CONTACT_TYPE_EMAIL;
		} else if (CONTACT_PATTERN_PHONE.matcher(contact.replaceAll(" ", "")).matches()) {
			return CONTACT_TYPE_PHONE;
		} else if (CONTACT_PATTERN_UNKNOWN.matcher(contact).matches()) {
			return CONTACT_TYPE_UNKNOWN;
		} else {
			return CONTACT_TYPE_UNKNOWN;
		}
	}

}
