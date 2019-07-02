package com.wojciechdm.contacts;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

@Slf4j
@AllArgsConstructor
class ContactsService {

  private final ContactsDao contactsDao;
  private final ContactsReaderFactory contactsReaderFactory;
  private final ContactsConverterFactory contactsConverterFactory;
  private final FileFormatChecker fileFormatChecker;

  void parseAndSaveContacts(File file) {
    FileFormat fileFormat = fileFormatChecker.recognizeFileFormat(file);
    ContactsParser contactsParser = contactsConverterFactory.getContactsConverter(fileFormat);

    try (ContactsReader contactsReader =
        contactsReaderFactory.getContactsReader(file, fileFormat)) {

      String[] data;
      do {
        data = contactsReader.readData();
        if (Objects.nonNull(data)) {
          contactsDao.save(setCustomersIds(contactsParser.parse(data)));
        }
      } while (Objects.nonNull(data[0]));

    } catch (IOException exception) {
      log.error("Problem with file", exception);
    }
  }

  private List<Customer> setCustomersIds(List<Customer> customers) {
    long customerId = contactsDao.fetchLastCustomerId();
    List<Customer> resultCustomers = new LinkedList<>();

    for (Customer customer : customers) {

      customerId++;
      List<Contact> contacts = new LinkedList<>();

      for (Contact contact : customer.getContacts()) {
        contacts.add(
            new Contact(contact.getId(), customerId, contact.getType(), contact.getContact()));
      }

      resultCustomers.add(
          new Customer(
              customerId,
              customer.getName(),
              customer.getSurname(),
              customer.getAge(),
              customer.getCity(),
              contacts));
    }
    return resultCustomers;
  }
}
