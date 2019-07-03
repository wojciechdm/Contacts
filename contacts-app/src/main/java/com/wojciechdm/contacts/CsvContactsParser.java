package com.wojciechdm.contacts;

import java.util.*;
import java.util.regex.*;

class CsvContactsParser implements ContactsParser {

  private static final Pattern DATA_PATTERN_CSV =
      Pattern.compile("(?<name>.+?),(?<surname>.+?),(?<age>\\d*?),(?<city>.+?)(?:,(.+))");
  private static final Pattern CONTACT_PATTERN_EMAIL = Pattern.compile(".+@.+\\.\\w+");
  private static final Pattern CONTACT_PATTERN_PHONE = Pattern.compile("\\d{3}-?\\d{3}-?\\d{3}");
  private static final Pattern CONTACT_PATTERN_UNKNOWN = Pattern.compile("\\d{1,8}");

  @Override
  public List<Customer> parse(String... data) {
    List<Customer> customers = new LinkedList<>();

    for (String line : data) {
      if (Objects.nonNull(line)) {

        Matcher matcher = DATA_PATTERN_CSV.matcher(line);

        if (matcher.matches()) {

          customers.add(
              new Customer(
                  null,
                  matcher.group("name"),
                  matcher.group("surname"),
                  matcher.group("age").isBlank() ? null : Integer.parseInt(matcher.group("age")),
                  matcher.group("city"),
                  parseContacts(matcher.group(matcher.groupCount()))));
        }
      }
    }
    return customers;
  }

  private List<Contact> parseContacts(String data) {

    List<Contact> contacts = new LinkedList<>();
    StringTokenizer contactData = new StringTokenizer(data, ",");

    while (contactData.hasMoreTokens()) {
      String rawContact = contactData.nextToken();
      contacts.add(new Contact(null, null, recognizeContactType(rawContact), rawContact));
    }

    return contacts;
  }

  private Integer recognizeContactType(String contact) {
    if (CONTACT_PATTERN_EMAIL.matcher(contact).matches()) {
      return CONTACT_TYPE_EMAIL;
    } else if (CONTACT_PATTERN_PHONE.matcher(contact).matches()) {
      return CONTACT_TYPE_PHONE;
    } else if (CONTACT_PATTERN_UNKNOWN.matcher(contact).matches()) {
      return CONTACT_TYPE_UNKNOWN;
    } else {
      return CONTACT_TYPE_JABBER;
    }
  }
}
