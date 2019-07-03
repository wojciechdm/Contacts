package com.wojciechdm.contacts;

import java.io.*;

class ContactsReaderFactory {
  ContactsReader getContactsReader(File file, FileFormat fileFormat) {
    switch (fileFormat) {
      case CSV:
        return new CsvContactsReader(file);
      case XML:
        return new XmlContactsReader(file);
      default:
        throw new IllegalArgumentException("Illegal file format: " + fileFormat);
    }
  }
}
