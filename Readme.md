# Contacts

Simple application to read contact data from CSV and XML files and write it to the database. Contact data include name, surname, optional age, city and any number of phone numbers, email addresses, jabber IDs and other contacts. 

### Getting started

Application was tested at machine with:

- Windows 10 Home 64-bit buid 17134
- Java build 1.8.0_181
- Maven 3.5.3
- MySQL 8.0

### Installing

1. Go to `../contacts-app/src/main/resources/datasource.properties` file and set database settings. 

2. Go to `../contacts-app/`  and use `mvn package` command to build a package.

3. Go to `../contacts-app/target/` and run  `contacts.jar` file with file paths to files with contacts data given as parameters:  `java -jar contacts.jar file_path` 

### Setup database

To create needed tables use scripts:

```
CREATE TABLE `contacts`.`customers` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `age` int(3) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `contacts`.`contacts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_customer` int(10) unsigned NOT NULL,
  `type` int(1) NOT NULL,
  `contact` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_customer` (`id_customer`),
  CONSTRAINT `contacts_ibfk_1` 
  FOREIGN KEY (`id_customer`) 
  REFERENCES `customers` (`id`) 
  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

### Example data file formatting

- CSV file:

  ```
  Jan,Kowal,30,Warszawa,999999999,888 888 888,jankowal@gmail.com,kowal@gmail.com
  Kamil,Nowicki,,Kraków,777666999,kamil.nowicki@gmail.com,3344,nowickijbr
  ```

- XML file:

  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <persons>
      <person>
          <name>Jan</name>
          <surname>Kowal</surname>
          <age>30</age>
          <city>Warszawa</city>
          <contacts>
              <phone>999999999</phone>
              <phone>888 888 888</phone>
              <email>jankowal@gmail.com</email>
              <email>kowal@gmail.com</email>
          </contacts>
      </person>
      <person>
          <name>Kamil</name>
          <surname>Nowicki</surname>
          <city>Kraków</city>
          <contacts>
              <phone>777666999</phone>
              <email>kamil.nowicki@gmail.com</email>
              <icq>3344</icq>
              <jabber>nowickijbr</jabber>
          </contacts>
      </person>
  </persons>
  ```


