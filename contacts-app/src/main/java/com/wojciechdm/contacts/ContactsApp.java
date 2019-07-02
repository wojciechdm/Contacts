package com.wojciechdm.contacts;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;

public class ContactsApp {

  public static void main(String[] args) {

    ContactsDao contactsDao =
        new ContactsDao(new HikariDataSource(new HikariConfig("/datasource.properties")));
    ContactsReaderFactory contactsReaderFactory = new ContactsReaderFactory();
    ContactsConverterFactory contactsConverterFactory = new ContactsConverterFactory();
    FileFormatChecker fileFormatChecker = new FileFormatChecker();
    ContactsService contactsService =
        new ContactsService(
            contactsDao, contactsReaderFactory, contactsConverterFactory, fileFormatChecker);

    for (String path : args) {
      contactsService.parseAndSaveContacts(new File(path));
    }
  }
}
