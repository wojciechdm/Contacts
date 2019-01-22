package com.wojciechdm.contacts;

public class ContactsApp {	
	
	public static void main(String[] args) {
		ContactsService contactsService=new ContactsService();
		
		for(String path:args) {
			contactsService.addContactsFromFileToDatabase(path);
		}		
	}
}
