DROP DATABASE IF EXISTS feedlot;
CREATE DATABASE feedlot;
USE feedlot;

#######################################################################
############			TABLES Users					###############
#######################################################################
CREATE TABLE FARM (
	`farmId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmName`						VARCHAR(50),
    PRIMARY KEY ( farmId )
) ENGINE = InnoDB;

CREATE TABLE USERS (
	`userId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `username`						VARCHAR(50) NOT NULL UNIQUE,
    `firstName`						VARCHAR(25) NOT NULL,
	`lastName`						VARCHAR(25) NOT NULL,
	`email`							VARCHAR(50) NOT NULL,
	`password`						VARCHAR(100) NOT NULL,
	`farmId`						INT NOT NULL,
	`enabled`						TINYINT NOT NULL DEFAULT 1, 
	PRIMARY KEY ( userId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
) ENGINE = InnoDB;

CREATE TABLE ROLES (
    `username`						VARCHAR(50) NOT NULL,
    `role`							VARCHAR(50) NOT NULL,
	PRIMARY KEY (role,username),
    FOREIGN KEY ( username ) REFERENCES USERS( username )
) ENGINE = InnoDB;

#######################################################################
############			TABLES HERD						###############
#######################################################################
CREATE TABLE SUPPLIER (
	`supplierId`					INT UNIQUE NOT NULL AUTO_INCREMENT,
    `supplierName`					VARCHAR(100) NOT NULL,
    `supplierLocation`				VARCHAR(100),
    `farmId`						INT NOT NULL,
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId ),
    PRIMARY KEY ( supplierId )
) ENGINE = InnoDB;

CREATE TABLE HERD (
	`herdId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `quantity`						INT NOT NULL,
    `weight`						DOUBLE NOT NULL,
    `cost`							DOUBLE NOT NULL,
    `tagNumber`						VARCHAR(15) NOT NULL,
    `estimatedSaleDate`				DATETIME,
    `implantDate`					DATETIME,
    `optiflexDate`					DATETIME,
    `dateEntered`					DATETIME NOT NULL,
    `supplierId`					INT NOT NULL,
    `sold`							BOOLEAN NOT NULL,
    PRIMARY KEY ( herdId ),
    FOREIGN KEY ( supplierId ) REFERENCES SUPPLIER( supplierId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
) ENGINE = InnoDB;

CREATE TABLE PACKER (
	`packerId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `packerName`					VARCHAR(100) NOT NULL,
    `packerLocation`				VARCHAR(100),
    `farmId`						INT NOT NULL,
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId ),
    PRIMARY KEY ( packerId )
) ENGINE = InnoDB;

CREATE TABLE LOCALE (
	`localeId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `localeName`					VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE HERD_LOCALE_MAP (
	`herdLocaleId`					INT UNIQUE NOT NULL AUTO_INCREMENT,
	`localeId`						INT NOT NULL,
	`herdId`						INT NOT NULL,
    `dateEnteringLocale`			DATETIME NOT NULL,
	PRIMARY KEY ( herdLocaleId ),
	FOREIGN KEY ( localeId ) REFERENCES LOCALE( localeId ),
	FOREIGN KEY ( herdId ) REFERENCES HERD( herdId )
) ENGINE = InnoDB;

CREATE TABLE SALE (
	`saleId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `localeId`						INT NOT NULL,
    `salePrice`						DOUBLE NOT NULL,
    `saleWeight`					INT NOT NULL,
    `quantity`						INT NOT NULL,
    `saleDate`						DATETIME NOT Null,
    `dressingPercent`				DOUBLE NOT NULL,
    `shrinkPercent`					DOUBLE NOT NULL,
    `packerId` 						INT NOT NULL,
    PRIMARY KEY ( saleId ),
	FOREIGN KEY ( localeId ) REFERENCES LOCALE( localeId ),
    FOREIGN KEY ( packerId ) REFERENCES PACKER( packerId )
) ENGINE = InnoDB;


#######################################################################
############			TABLES Feeding					###############
#######################################################################
CREATE TABLE FEEDING (
	`feedingId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedingTime`					DATETIME,
	`localeId` 						INT,
	`bunckScore`					INT,
	`deliveredAmountTMRD`			DOUBLE,
	`userId`						INT,
	`hasLeftovers`					BOOL,
    PRIMARY KEY ( feedingId ),
    FOREIGN KEY ( localeId ) REFERENCES LOCALE( localeId ),
    FOREIGN KEY ( userId ) REFERENCES USERS( userId )
) ENGINE = InnoDB;

CREATE TABLE FEED_TYPES (
	`feedTypeId`					INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `feedType` 						VARCHAR(100) NOT NULL,
    `driedMatterPercentage`			DOUBLE NOT NULL,
    `enabled`						BOOL,
    PRIMARY KEY ( feedTypeId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
) ENGINE = InnoDB;

CREATE TABLE FEED (
	`feedId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedAmount`					INT NOT NULL,
    `feedTypeId`					INT UNIQUE NOT NULL,
    `feedingId`						INT NOT NULL,
    PRIMARY KEY ( feedId ),
    FOREIGN KEY ( feedTypeId ) REFERENCES FEED_TYPES( feedTypeId ),
    FOREIGN KEY ( feedingId ) REFERENCES FEEDING( feedingId )
) ENGINE = InnoDB;
