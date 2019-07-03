package com.wojciechdm.contacts

import com.mysql.cj.jdbc.MysqlDataSource
import groovy.util.logging.Slf4j
import org.testcontainers.containers.MySQLContainer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.sql.SQLException

@Slf4j
class ContactsDaoTest extends Specification {

  @Shared
  private def contactsDao
  @AutoCleanup
  @Shared
  private def container
  @Shared
  private def dataSource

  def setupSpec() {
    container = new MySQLContainer()
    dataSource = new MysqlDataSource()
    container.withInitScript("schema-test.sql")
    container.start()
    dataSource.setUrl(container.getJdbcUrl())
    dataSource.setUser(container.getUsername())
    dataSource.setPassword(container.getPassword())
    try {
      dataSource.setUseSSL(false)
      dataSource.setServerTimezone("Europe/Warsaw")
    } catch (SQLException exception) {
      log.error("Problem with test container", exception)
    }
    contactsDao = new ContactsDao(dataSource)
  }

  def "should save customers and fetch last used id"() {

    given:

    def customers = [new Customer(1L, "Adam", "Nowicki", 20, "Kraków",
        [new Contact(null, 1L, 1, "adam@nowicki.pl")]),
                     new Customer(2L, "Anna", "Majka", 33, "Wrocław",
                         [new Contact(null, 2L, 0, "34455")])]

    when:

    contactsDao.save(customers)
    def actualLastSavedId = contactsDao.fetchLastCustomerId()

    then:

    actualLastSavedId == 2L
  }
}