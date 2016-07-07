CREATE TABLE `authorISBN` (
  `authorID` int(11) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  KEY `authorID` (`authorID`),
  KEY `isbn` (`isbn`),
  CONSTRAINT `authorisbn_ibfk_1` FOREIGN KEY (`authorID`) REFERENCES `authors` (`authorID`),
  CONSTRAINT `authorisbn_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `titles` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `authors` (
  `authorID` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  PRIMARY KEY (`authorID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `titles` (
  `isbn` varchar(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `editionNumber` int(11) NOT NULL,
  `copyright` varchar(4) NOT NULL,
  PRIMARY KEY (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;