package com.wojciechdm.contacts;

import java.util.List;

public interface ContactsConverter {
	
	int CONTACT_TYPE_UNKNOWN=0;
	int CONTACT_TYPE_EMAIL=1;
	int CONTACT_TYPE_PHONE=2;
	int CONTACT_TYPE_JABBER=3;
	
	void convertContacts(String... data);
	List<Customer> getCustomersList();
	
}
