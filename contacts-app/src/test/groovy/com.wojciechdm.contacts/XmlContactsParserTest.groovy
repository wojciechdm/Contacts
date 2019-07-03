package com.wojciechdm.contacts

import spock.lang.Specification
import spock.lang.Unroll

class XmlContactsParserTest extends Specification {

  private def xmlContactsParser = new XmlContactsParser()

  @Unroll
  def "should parse customers contacts data"() {

    when:

    def actualParsedCustomers = xmlContactsParser.parse(customersData)

    then:

    equalsCustomersData(expectedParsedCustomers, actualParsedCustomers)

    where:

    customersData << correctCustomersData()
    expectedParsedCustomers << expectedParsedCustomers()
  }

  @Unroll
  def "should return empty list when customers contacts data incorrect"() {

    when:

    def actualParseResult = xmlContactsParser.parse(customersData)

    then:

    actualParseResult == []

    where:

    customersData << incorrectCustomersData()
  }

  void equalsCustomersData(def expected, def actual) {
    for (int i = 0; i < expected.size(); i++) {
      assert expected.get(i).getName() == actual.get(i).getName()
      assert expected.get(i).getSurname() == actual.get(i).getSurname()
      assert expected.get(i).getAge() == actual.get(i).getAge()
      assert expected.get(i).getCity() == actual.get(i).getCity()
      for (int j = 0; j < expected.get(i).getContacts().size(); j++) {
        assert expected.get(i).getContacts().get(j).getType() == actual.get(i).getContacts().get(j).getType()
        assert expected.get(i).getContacts().get(j).getContact() == actual.get(i).getContacts().get(j).getContact()
      }
    }
  }

  def correctCustomersData() {
    [["<persons><person><name>Jan</name><surname>Nowak</surname><age>15</age><city>Lublin</city>" +
          "<contacts><phone>895890345</phone><email>jan@nowak.pl</email></contacts></person>" +
          "<person><name>Adam</name><surname>Nowicki</surname><age>20</age><city>Kraków</city>" +
          "<contacts><email>adam@nowicki.pl</email></contacts></person>" +
          "<person><name>Anna</name><surname>Majka</surname><age>33</age><city>Wrocław</city>" +
          "<contacts><icq>34455</icq></contacts></person></persons>"] as String[],
     ["<persons><person><name>Michał</name><surname>Fornalik</surname><city>Warszawa</city>" +
          "<contacts><jabber>michfor</jabber></contacts></person></persons>"] as String[],
     ["<persons><person><name>Jan</name><surname>Nowak</surname><age>15</age><city>Lublin</city>" +
          "<contacts><phone>895890345</phone><email>jan@nowak.pl</email></contacts></person>" +
          "<person><name>Adam</name><surname>Nowicki</surname><age>20</age><city>Kraków</city>" +
          "<contacts><email>adam@nowicki.pl</email></contacts></person>" +
          "<person><name>Anna</name><surname>Majka</surname><age>33</age><city>Wrocław</city>" +
          "<contacts><icq>34455</icq></contacts></person>" +
          "<person><name>Jan</name><surname>Nowak</surname><age>15</age><city>Lublin</city>" +
          "<contacts><phone>895890345</phone><email>jan@nowak.pl</email></contacts></person>" +
          "<person><name>Adam</name><surname>Nowicki</surname><age>20</age><city>Kraków</city>" +
          "<contacts><email>adam@nowicki.pl</email></contacts></person>" +
          "<person><name>Anna</name><surname>Majka</surname><age>33</age><city>Wrocław</city>n" +
          "<contacts><icq>34455</icq></contacts></person>" +
          "<person><name>Jan</name><surname>Nowak</surname><age>15</age><city>Lublin</city>" +
          "<contacts><phone>895890345</phone><email>jan@nowak.pl</email></contacts></person>" +
          "<person><name>Adam</name><surname>Nowicki</surname><age>20</age><city>Kraków</city>" +
          "<contacts><email>adam@nowicki.pl</email></contacts></person>" +
          "<person><name>Anna</name><surname>Majka</surname><age>33</age><city>Wrocław</city>n" +
          "<contacts><icq>34455</icq></contacts></person>" +
          "<person><name>Jan</name><surname>Nowak</surname><age>15</age><city>Lublin</city>" +
          "<contacts><phone>895890345</phone><email>jan@nowak.pl</email></contacts></person>" +
          "<person><name>Adam</name><surname>Nowicki</surname><age>20</age><city>Kraków</city>" +
          "<contacts><email>adam@nowicki.pl</email></contacts></person>" +
          "<person><name>Anna</name><surname>Majka</surname><age>33</age><city>Wrocław</city>n" +
          "<contacts><icq>34455</icq></contacts></person>" +
          "<person><name>Jan</name><surname>Nowak</surname><age>15</age><city>Lublin</city>" +
          "<contacts><phone>895890345</phone><email>jan@nowak.pl</email></contacts></person>" +
          "<person><name>Adam</name><surname>Nowicki</surname><age>20</age><city>Kraków</city>" +
          "<contacts><email>adam@nowicki.pl</email></contacts></person>" +
          "<person><name>Anna</name><surname>Majka</surname><age>33</age><city>Wrocław</city>n" +
          "<contacts><icq>34455</icq></contacts></person>" +
          "<person><name>Jan</name><surname>Nowak</surname><age>15</age><city>Lublin</city>" +
          "<contacts><phone>895890345</phone><email>jan@nowak.pl</email></contacts></person>" +
          "<person><name>Adam</name><surname>Nowicki</surname><age>20</age><city>Kraków</city>" +
          "<contacts><email>adam@nowicki.pl</email></contacts></person>" +
          "<person><name>Anna</name><surname>Majka</surname><age>33</age><city>Wrocław</city>n" +
          "<contacts><icq>34455</icq></contacts></person></persons>"] as String[]
    ]
  }

  def expectedParsedCustomers() {
    [[new Customer(null, "Jan", "Nowak", 15, "Lublin",
        [new Contact(null, null, 2, "895890345"),
         new Contact(null, null, 1, "jan@nowak.pl")]),
      new Customer(null, "Adam", "Nowicki", 20, "Kraków",
          [new Contact(null, null, 1, "adam@nowicki.pl")]),
      new Customer(null, "Anna", "Majka", 33, "Wrocław",
          [new Contact(null, null, 0, "34455")])],
     [new Customer(null, "Michał", "Fornalik", null, "Warszawa",
         [new Contact(null, null, 3, "michfor")])],
     [new Customer(null, "Jan", "Nowak", 15, "Lublin",
         [new Contact(null, null, 2, "895890345"),
          new Contact(null, null, 1, "jan@nowak.pl")]),
      new Customer(null, "Adam", "Nowicki", 20, "Kraków",
          [new Contact(null, null, 1, "adam@nowicki.pl")]),
      new Customer(null, "Anna", "Majka", 33, "Wrocław",
          [new Contact(null, null, 0, "34455")]),
      new Customer(null, "Jan", "Nowak", 15, "Lublin",
          [new Contact(null, null, 2, "895890345"),
           new Contact(null, null, 1, "jan@nowak.pl")]),
      new Customer(null, "Adam", "Nowicki", 20, "Kraków",
          [new Contact(null, null, 1, "adam@nowicki.pl")]),
      new Customer(null, "Anna", "Majka", 33, "Wrocław",
          [new Contact(null, null, 0, "34455")]),
      new Customer(null, "Jan", "Nowak", 15, "Lublin",
          [new Contact(null, null, 2, "895890345"),
           new Contact(null, null, 1, "jan@nowak.pl")]),
      new Customer(null, "Adam", "Nowicki", 20, "Kraków",
          [new Contact(null, null, 1, "adam@nowicki.pl")]),
      new Customer(null, "Anna", "Majka", 33, "Wrocław",
          [new Contact(null, null, 0, "34455")]),
      new Customer(null, "Jan", "Nowak", 15, "Lublin",
          [new Contact(null, null, 2, "895890345"),
           new Contact(null, null, 1, "jan@nowak.pl")]),
      new Customer(null, "Adam", "Nowicki", 20, "Kraków",
          [new Contact(null, null, 1, "adam@nowicki.pl")]),
      new Customer(null, "Anna", "Majka", 33, "Wrocław",
          [new Contact(null, null, 0, "34455")]),
      new Customer(null, "Jan", "Nowak", 15, "Lublin",
          [new Contact(null, null, 2, "895890345"),
           new Contact(null, null, 1, "jan@nowak.pl")]),
      new Customer(null, "Adam", "Nowicki", 20, "Kraków",
          [new Contact(null, null, 1, "adam@nowicki.pl")]),
      new Customer(null, "Anna", "Majka", 33, "Wrocław",
          [new Contact(null, null, 0, "34455")]),
      new Customer(null, "Jan", "Nowak", 15, "Lublin",
          [new Contact(null, null, 2, "895890345"),
           new Contact(null, null, 1, "jan@nowak.pl")]),
      new Customer(null, "Adam", "Nowicki", 20, "Kraków",
          [new Contact(null, null, 1, "adam@nowicki.pl")]),
      new Customer(null, "Anna", "Majka", 33, "Wrocław",
          [new Contact(null, null, 0, "34455")])]
    ]
  }

  def incorrectCustomersData() {
    [["<persons><name>Jan</name><surname>Nowak</surname><city>Lublin</city></persons"] as String[],
     [] as String[]
    ]
  }
}
