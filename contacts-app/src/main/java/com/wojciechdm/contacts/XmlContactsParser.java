package com.wojciechdm.contacts;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

@Slf4j
class XmlContactsParser implements ContactsParser {

  @Override
  public List<Customer> parse(String... data) {

    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    ContactsHandler contactsHandler = new ContactsHandler();

    try {

      SAXParser saxParser = saxParserFactory.newSAXParser();

      for (String line : data) {
        if (Objects.nonNull(line)) {
          saxParser.parse(new InputSource(new StringReader(line)), contactsHandler);
        }
      }
    } catch (ParserConfigurationException | SAXException | IOException exception) {
      log.error("Problem while parsing xml file ", exception);
    }

    return contactsHandler.getCustomers();
  }

  private Contact prepareContact(Integer type, String data) {
    return new Contact(null, null, type, data);
  }

  private class ContactsHandler extends DefaultHandler {

    private Customer customer = new Customer();
    private List<Contact> contacts = new LinkedList<>();
    private List<Customer> customers = new LinkedList<>();
    private Map<String, Boolean> elements = new HashMap<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
      String qNameUpperCase = qName.toUpperCase();
      switch (qNameUpperCase) {
        case "PERSONS":
          break;
        case "PERSON":
          customer = new Customer();
          contacts = new LinkedList<>();
          break;
        default:
          elements.put(qNameUpperCase, true);
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
      String qNameUpperCase = qName.toUpperCase();
      if ("PERSON".equals(qNameUpperCase)) {
        customer.setContacts(contacts);
        customers.add(customer);
      } else {
        elements.put(qNameUpperCase, false);
      }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
      for (Map.Entry<String, Boolean> element : elements.entrySet()) {
        if (element.getValue()) {
          switch (element.getKey()) {
            case "NAME":
              customer.setName(new String(ch, start, length));
              break;
            case "SURNAME":
              customer.setSurname(new String(ch, start, length));
              break;
            case "AGE":
              customer.setAge(Integer.parseInt(new String(ch, start, length)));
              break;
            case "CITY":
              customer.setCity(new String(ch, start, length));
              break;
            case "EMAIL":
              contacts.add(prepareContact(CONTACT_TYPE_EMAIL, new String(ch, start, length)));
              break;
            case "PHONE":
              contacts.add(prepareContact(CONTACT_TYPE_PHONE, new String(ch, start, length)));
              break;
            case "JABBER":
              contacts.add(prepareContact(CONTACT_TYPE_JABBER, new String(ch, start, length)));
              break;
            case "CONTACTS":
              break;
            default:
              if (elements.containsKey("CONTACTS") && elements.get("CONTACTS")) {
                contacts.add(prepareContact(CONTACT_TYPE_UNKNOWN, new String(ch, start, length)));
              }
          }
        }
      }
    }

    List<Customer> getCustomers() {
      return customers;
    }
  }
}
