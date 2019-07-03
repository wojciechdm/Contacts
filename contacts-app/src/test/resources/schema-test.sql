create TABLE `customers` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `age` int(3) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create TABLE `contacts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_customer` int(10) unsigned NOT NULL,
  `type` int(1) NOT NULL,
  `contact` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_customer` (`id_customer`),
  CONSTRAINT `contacts_ibfk_1`
  FOREIGN KEY (`id_customer`)
  REFERENCES `customers` (`id`)
  ON delete CASCADE ON update CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;