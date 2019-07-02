package com.wojciechdm.contacts

import spock.lang.Specification

class XmlContactsReaderTest extends Specification {

  def xmlContactsReader = new XmlContactsReader(new File("src/test/resources/test-file.xml"))

  def "should read customers contacts data from file"() {

    when:

    def actualReadedData = xmlContactsReader.readData()

    then:

    actualReadedData == expectedReadedData

    where:

    expectedReadedData << expectedReadedData()
  }

  def expectedReadedData() {
    [
        ["<PERSONS><person><name>Jan</name><surname>Kowal</surname><age>30</age><city>Warszawa</city>" +
             "<contacts><phone>999999999</phone><phone>888 888 888</phone><email>jankowal@gmail.com</email>" +
             "<email>kowal@gmail.com</email></contacts></person>" +
             "<person><name>Kamil</name><surname>Nowicki</surname><city>Kraków</city>" +
             "<contacts><phone>777666999</phone><email>kamil.nowicki@gmail.com</email>" +
             "<icq>3344</icq><jabber>nowickijbr</jabber></contacts></person>" +
             "<person><name>Jan</name><surname>Kowal</surname><age>30</age><city>Warszawa</city>" +
             "<contacts><phone>999999999</phone><phone>888 888 888</phone><email>jankowal@gmail.com</email>" +
             "<email>kowal@gmail.com</email></contacts></person>" +
             "<person><name>Kamil</name><surname>Nowicki</surname><city>Kraków</city>" +
             "<contacts><phone>777666999</phone><email>kamil.nowicki@gmail.com</email>" +
             "<icq>3344</icq><jabber>nowickijbr</jabber></contacts></person>" +
             "<person><name>Jan</name><surname>Kowal</surname><age>30</age><city>Warszawa</city>" +
             "<contacts><phone>999999999</phone><phone>888 888 888</phone><email>jankowal@gmail.com</email>" +
             "<email>kowal@gmail.com</email></contacts></person>" +
             "<person><name>Kamil</name><surname>Nowicki</surname><city>Kraków</city>" +
             "<contacts><phone>777666999</phone><email>kamil.nowicki@gmail.com</email>" +
             "<icq>3344</icq><jabber>nowickijbr</jabber></contacts></person>" +
             "<person><name>Jan</name><surname>Kowal</surname><age>30</age><city>Warszawa</city>" +
             "<contacts><phone>999999999</phone><phone>888 888 888</phone><email>jankowal@gmail.com</email>" +
             "<email>kowal@gmail.com</email></contacts></person>" +
             "<person><name>Kamil</name><surname>Nowicki</surname><city>Kraków</city>" +
             "<contacts><phone>777666999</phone><email>kamil.nowicki@gmail.com</email>" +
             "<icq>3344</icq><jabber>nowickijbr</jabber></contacts></person>" +
             "<person><name>Jan</name><surname>Kowal</surname><age>30</age><city>Warszawa</city>" +
             "<contacts><phone>999999999</phone><phone>888 888 888</phone><email>jankowal@gmail.com</email>" +
             "<email>kowal@gmail.com</email></contacts></person>" +
             "<person><name>Kamil</name><surname>Nowicki</surname><city>Kraków</city>" +
             "<contacts><phone>777666999</phone><email>kamil.nowicki@gmail.com</email>" +
             "<icq>3344</icq><jabber>nowickijbr</jabber></contacts></person></PERSONS>"
        ] as String[]
    ]
  }
}
