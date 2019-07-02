package com.wojciechdm.contacts;

import java.util.List;

interface ContactsParser {

  int CONTACT_TYPE_UNKNOWN = 0;
  int CONTACT_TYPE_EMAIL = 1;
  int CONTACT_TYPE_PHONE = 2;
  int CONTACT_TYPE_JABBER = 3;

  List<Customer> parse(String... data);
}
