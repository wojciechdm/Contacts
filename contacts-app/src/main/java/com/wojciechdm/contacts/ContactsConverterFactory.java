package com.wojciechdm.contacts;

class ContactsConverterFactory {
  ContactsParser getContactsConverter(FileFormat fileFormat) {
    switch (fileFormat) {
      case CSV:
        return new CsvContactsParser();
      case XML:
        return new XmlContactsParser();
      default:
        throw new IllegalFileFormatException("Illegal file format: " + fileFormat);
    }
  }
}
