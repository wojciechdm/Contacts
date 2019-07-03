package com.wojciechdm.contacts

import spock.lang.Specification

class CsvContactsReaderTest extends Specification {

  def csvContactsReader = new CsvContactsReader(new File("src/test/resources/test-file.csv"))

  def "should read customers contacts data from file"() {

    when:

    def actualReadedData = csvContactsReader.readData()

    then:

    actualReadedData == expectedReadedData

    where:

    expectedReadedData << expectedReadedData()
  }

  def expectedReadedData() {
    [
        ["Jan,Kowal,30,Warszawa,999999999,888 888 888,jankowal@gmail.com,kowal@gmail.com",
         "Kamil,Nowicki,,Kraków,777666999,kamil.nowicki@gmail.com,3344,nowickijbr",
         "Jan,Kowal,30,Warszawa,999999999,888 888 888,jankowal@gmail.com,kowal@gmail.com",
         "Kamil,Nowicki,,Kraków,777666999,kamil.nowicki@gmail.com,3344,nowickijbr",
         "Jan,Kowal,30,Warszawa,999999999,888 888 888,jankowal@gmail.com,kowal@gmail.com",
         "Kamil,Nowicki,,Kraków,777666999,kamil.nowicki@gmail.com,3344,nowickijbr",
         "Jan,Kowal,30,Warszawa,999999999,888 888 888,jankowal@gmail.com,kowal@gmail.com",
         "Kamil,Nowicki,,Kraków,777666999,kamil.nowicki@gmail.com,3344,nowickijbr",
         "Jan,Kowal,30,Warszawa,999999999,888 888 888,jankowal@gmail.com,kowal@gmail.com",
         "Kamil,Nowicki,,Kraków,777666999,kamil.nowicki@gmail.com,3344,nowickijbr"] as String[]
    ]
  }
}
