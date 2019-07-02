package com.wojciechdm.contacts

import spock.lang.Specification
import spock.lang.Unroll

class CsvContactsParserTest extends Specification {

  private def csvContactsParser = new CsvContactsParser()

  @Unroll
  def "should parse customers contacts data"() {

    when:

    def actualParsedCustomers = csvContactsParser.parse(customersData)

    then:

    equalsCustomersData(expectedParsedCustomers, actualParsedCustomers)

    where:

    customersData << correctCustomersData()
    expectedParsedCustomers << expectedParsedCustomers()
  }

  @Unroll
  def "should return empty list when customers contacts data incorrect"() {

    when:

    def actualParseResult = csvContactsParser.parse(customersData)

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
    [
        ["Jan,Nowak,15,Lublin,895890345,jan@nowak.pl",
         "Adam,Nowicki,20,Kraków,adam@nowicki.pl",
         "Anna,Majka,33,Wrocław,34455"] as String[],
        ["Michał,Fornalik,,Warszawa,michfor"] as String[],
        ["Jan,Nowak,15,Lublin,895890345,jan@nowak.pl",
         "Adam,Nowicki,20,Kraków,adam@nowicki.pl",
         "Anna,Majka,33,Wrocław,34455",
         "Jan,Nowak,15,Lublin,895890345,jan@nowak.pl",
         "Adam,Nowicki,20,Kraków,adam@nowicki.pl",
         "Anna,Majka,33,Wrocław,34455",
         "Jan,Nowak,15,Lublin,895890345,jan@nowak.pl",
         "Adam,Nowicki,20,Kraków,adam@nowicki.pl",
         "Anna,Majka,33,Wrocław,34455",
         "Jan,Nowak,15,Lublin,895890345,jan@nowak.pl",
         "Adam,Nowicki,20,Kraków,adam@nowicki.pl",
         "Anna,Majka,33,Wrocław,34455",
         "Jan,Nowak,15,Lublin,895890345,jan@nowak.pl",
         "Adam,Nowicki,20,Kraków,adam@nowicki.pl",
         "Anna,Majka,33,Wrocław,34455",
         "Jan,Nowak,15,Lublin,895890345,jan@nowak.pl",
         "Adam,Nowicki,20,Kraków,adam@nowicki.pl",
         "Anna,Majka,33,Wrocław,34455"] as String[]
    ]
  }

  def expectedParsedCustomers() {
    [
        [new Customer(null, "Jan", "Nowak", 15, "Lublin",
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
    [
        ["Jan,Nowak,23,Poznań"] as String[],
        ["kamil@nowak,343456543"] as String[],
        ["Marek,Walczak,Rzeszów,453222111"] as String[],
        [] as String[]
    ]
  }
}
