package com.wojciechdm.contacts;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@AllArgsConstructor
class ContactsDao {

  private static final String INSERT_CUSTOMER_STATEMENT =
      "INSERT INTO customers(id, name, surname, age, city) VALUES(?,?,?,?,?)";
  private static final String INSERT_CONTACT_STATEMENT =
      "INSERT INTO contacts(id, id_customer, type, contact) VALUES(?,?,?,?)";

  private final DataSource dataSource;

  void save(List<Customer> customers) {

    try (Connection connection = dataSource.getConnection();
        PreparedStatement insertCustomerStatement =
            connection.prepareStatement(INSERT_CUSTOMER_STATEMENT);
        PreparedStatement insertContactStatement =
            connection.prepareStatement(INSERT_CONTACT_STATEMENT)) {

      connection.setAutoCommit(false);

      prepareInsertStatements(customers, insertCustomerStatement, insertContactStatement);

      insertCustomerStatement.executeBatch();
      insertContactStatement.executeBatch();

      connection.commit();

    } catch (SQLException exception) {
      throw new IllegalStateException("Problem with database while saving customers", exception);
    }
  }

  long fetchLastCustomerId() {

    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement =
            connection.prepareStatement("SELECT MAX(id) FROM customers")) {

      ResultSet result = statement.executeQuery();
      result.next();

      return result.getLong(1);

    } catch (SQLException exception) {
      throw new IllegalStateException("Problem with database while fetching last id", exception);
    }
  }

  private void prepareInsertStatements(
      List<Customer> customers,
      PreparedStatement insertCustomerStatement,
      PreparedStatement insertContactStatement)
      throws SQLException {

    for (Customer customer : customers) {

      insertCustomerStatement.setLong(1, customer.getId());
      insertCustomerStatement.setString(2, customer.getName());
      insertCustomerStatement.setString(3, customer.getSurname());
      insertCustomerStatement.setObject(4, customer.getAge());
      insertCustomerStatement.setString(5, customer.getCity());
      insertCustomerStatement.addBatch();

      for (Contact contact : customer.getContacts()) {

        insertContactStatement.setObject(1, contact.getId());
        insertContactStatement.setLong(2, contact.getCustomerId());
        insertContactStatement.setInt(3, contact.getType());
        insertContactStatement.setString(4, contact.getContact());
        insertContactStatement.addBatch();
      }
    }
  }
}
