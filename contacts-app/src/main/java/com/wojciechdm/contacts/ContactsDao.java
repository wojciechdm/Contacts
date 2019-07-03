package com.wojciechdm.contacts;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@AllArgsConstructor
class ContactsDao {

  private final DataSource dataSource;

  void save(List<Customer> customersList) {
    List<String> sqlStatements = new LinkedList<>();
    for (Customer customer : customersList) {
      sqlStatements.add(createCustomerSaveSqlStatement(customer));
      for (Contact contact : customer.getContacts()) {
        sqlStatements.add(createContactSaveSqlStatement(contact));
      }
    }
    trySave(sqlStatements);
  }

  long fetchLastCustomerId() {

    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {

      ResultSet result = statement.executeQuery("SELECT MAX(id) FROM customers");
      result.next();

      return result.getLong(1);

    } catch (SQLException exception) {
      throw new IllegalStateException("Problem with database while fetching last id", exception);
    }
  }

  private void trySave(List<String> sqlStatements) {
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {

      for (String sqlStatement : sqlStatements) {
        statement.addBatch(sqlStatement);
      }
      statement.executeBatch();
    } catch (SQLException exception) {
      throw new IllegalStateException("Problem with database while saving customers", exception);
    }
  }

  private String createCustomerSaveSqlStatement(Customer customer) {

    return "INSERT INTO customers VALUE ("
        + customer.getId()
        + ",'"
        + customer.getName()
        + "','"
        + customer.getSurname()
        + "',"
        + customer.getAge()
        + ",'"
        + customer.getCity()
        + "')";
  }

  private String createContactSaveSqlStatement(Contact contact) {

    return "INSERT INTO contacts VALUE ("
        + contact.getId()
        + ","
        + contact.getCustomerId()
        + ","
        + contact.getType()
        + ",'"
        + contact.getContact()
        + "')";
  }
}
