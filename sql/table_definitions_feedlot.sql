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
CREATE TABLE HERD (
	`herdId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `quantity`						INT NOT NULL,
    `weight`						INT NOT NULL,
    `cost`							DOUBLE NOT NULL,
    `tagNumber`						VARCHAR(15) NOT NULL,
    `estimatedSaleDate`				DATETIME,
    `implantDate`					DATETIME,
    `optiflexDate`					DATETIME,
    `dateEntered`					DATETIME NOT NULL,
    PRIMARY KEY ( herdId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
) ENGINE = InnoDB;

CREATE TABLE SUPPLIER (
	`supplierId`					INT UNIQUE NOT NULL AUTO_INCREMENT,
    `supplierName`					VARCHAR(100),
    PRIMARY KEY ( supplierId )
) ENGINE = InnoDB;

CREATE TABLE HERD_SUPPLIERS_MAP (
	`herdSupplierId`				INT UNIQUE NOT NULL AUTO_INCREMENT,
	`supplierId`					INT NOT NULL,
	`herdId`						INT NOT NULL,
	PRIMARY KEY ( herdSupplierId ),
	FOREIGN KEY ( supplierId ) REFERENCES SUPPLIER( supplierId ),
	FOREIGN KEY ( herdId ) REFERENCES HERD( herdId )
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
    `herdId`						INT NOT NULL,
    `salePrice`						DOUBLE NOT NULL,
    `saleWeight`					INT NOT NULL,
    `quantity`						INT NOT NULL,
    `saleDate`						DATETIME NOT Null,
    PRIMARY KEY ( saleId ),
	FOREIGN KEY ( herdId ) REFERENCES HERD( herdId )
) ENGINE = InnoDB;


#######################################################################
############			TABLES Feeding					###############
#######################################################################
CREATE TABLE FEEDING (
	`feedingId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedingTime`					INT NOT NULL,
    `feedingDate`					DATETIME NOT Null,
	`localeId` 						INT NOT NULL,
	`bunckScore`					INT NOT NULL,
	`deliveredAmountTMRD`			INT NOT NULL,
	`userId`						INT NOT NULL,
    PRIMARY KEY ( feedingId ),
    FOREIGN KEY ( localeId ) REFERENCES LOCALE( localeId ),
    FOREIGN KEY ( userId ) REFERENCES USERS( userId )
) ENGINE = InnoDB;

CREATE TABLE FEED_TYPES (
	`feedTypeId`					INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `feedType` 						VARCHAR(25) NOT NULL,
    PRIMARY KEY ( feedTypeId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
) ENGINE = InnoDB;

CREATE TABLE FEED_STOCK (
	`feedStockId` 					INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedTypeId`					INT NOT NULL,
    `stockAmount` 					INT NOT NULL,
    PRIMARY KEY ( feedStockId ),
    FOREIGN KEY ( feedTypeId ) REFERENCES FEED_TYPES( feedTypeId )
) ENGINE = InnoDB;

CREATE TABLE FEED (
	`feedId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedAmount`					INT NOT NULL,
    `feedTypeId`					INT UNIQUE NOT NULL,
    PRIMARY KEY ( feedId ),
    FOREIGN KEY ( feedTypeId ) REFERENCES FEED_TYPES( feedTypeId )
) ENGINE = InnoDB;

CREATE TABLE FEED_MIX (
	`mixId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedId`					INT NOT NULL,
    `feedingId`					INT NOT NULL,
    PRIMARY KEY ( mixId ),
    FOREIGN KEY ( feedId ) REFERENCES FEED( feedId ),
	FOREIGN KEY ( feedingId ) REFERENCES FEED_MIX( feedingId )
) ENGINE = InnoDB;

CREATE TABLE LEFTOVER_FEED_MIX (
	`leftoverId`			INT UNIQUE NOT NULL AUTO_INCREMENT,
	`mixId`					INT NOT NULL,
    PRIMARY KEY ( leftoverId ),
    FOREIGN KEY ( mixId ) REFERENCES FEED_MIX( mixId )
) 
ENGINE = InnoDB;

CREATE TABLE HERD_FEED (
	`herdFeedId`			INT UNIQUE NOT NULL AUTO_INCREMENT,
	`herdId`				INT NOT NULL,
    PRIMARY KEY ( herdFeedId ),
    FOREIGN KEY ( herdId ) REFERENCES HERD( herdId )
) ENGINE = InnoDB;
