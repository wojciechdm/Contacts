package com.wojciechdm.contacts;

import java.util.*;

public class ContactsDao {
	
	private DatabaseConnector databaseConnector;	
	
	public ContactsDao() {
		this.databaseConnector=new DatabaseConnector();
	}
	
	private String getCustomerSaveSqlStatement(Customer customer) {
		StringBuilder sqlStatement=new StringBuilder();
		
		sqlStatement.append("INSERT INTO customers VALUE (").append(customer.getId())
		.append(",'").append(customer.getName()).append("','").append(customer.getSurname())
		.append("',").append(customer.getAge()).append(",'").append(customer.getCity())
		.append("')");		
		return sqlStatement.toString();
	}
	
	private String getContactSaveSqlStatement(Contact contact) {
		StringBuilder sqlStatement=new StringBuilder();
		
		sqlStatement.append("INSERT INTO contacts VALUE (").append(contact.getId()).append(",")
		.append(contact.getCustomerId()).append(",").append(contact.getType()).append(",'")
		.append(contact.getContact()).append("')");
		return sqlStatement.toString();
	}
	
	public void saveContacts(List<Customer> customersList) {
		List<String> sqlStatements=new LinkedList<>();
		for(Customer customer: customersList) {
			sqlStatements.add(getCustomerSaveSqlStatement(customer));
			for (Contact contact: customer.getContactsList()) {
				sqlStatements.add(getContactSaveSqlStatement(contact));
			}
		}		
		databaseConnector.saveToDatabase(sqlStatements);		
	}	
	
	public Long getLastCustomerId() {
		Long customerId;
		String lastCustomerIdSqlStatement = "SELECT MAX(id) FROM customers";
		customerId=databaseConnector.getSingleLongValueFromDatabase(lastCustomerIdSqlStatement);
		if (customerId==null) {
			customerId=0L;
		}
		return customerId;
	}
}
