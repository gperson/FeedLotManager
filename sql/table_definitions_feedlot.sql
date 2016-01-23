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
    `localeName`					VARCHAR(50) NOT NULL,
    `enabled`						BOOL NOT NULL,
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId ),
    PRIMARY KEY ( localeId )
) ENGINE = InnoDB;

CREATE TABLE GROUPED_HERDS (
	`groupedHerdsId`				INT UNIQUE NOT NULL AUTO_INCREMENT,
    `localeId`						INT UNIQUE,
	PRIMARY KEY ( groupedHerdsId ),
    FOREIGN KEY ( localeId ) REFERENCES LOCALE( localeId )
) ENGINE = InnoDB;

CREATE TABLE SALE (
	`saleId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `groupedHerdsId`				INT NOT NULL,
    `salePrice`						DOUBLE NOT NULL,
    `saleWeight`					INT NOT NULL,
    `quantity`						INT NOT NULL,
    `saleDate`						DATETIME NOT Null,
    `dressingPercent`				DOUBLE NOT NULL,
    `shrinkPercent`					DOUBLE NOT NULL,
    `packerId` 						INT NOT NULL,
    PRIMARY KEY ( saleId ),
	FOREIGN KEY ( groupedHerdsId ) REFERENCES GROUPED_HERDS( groupedHerdsId ),
    FOREIGN KEY ( packerId ) REFERENCES PACKER( packerId )
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
    `groupedHerdsId`				INT,
    PRIMARY KEY ( herdId ),
    FOREIGN KEY ( supplierId ) REFERENCES SUPPLIER( supplierId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId ),
    FOREIGN KEY ( groupedHerdsId ) REFERENCES GROUPED_HERDS( groupedHerdsId )
) ENGINE = InnoDB;


#######################################################################
############			TABLES Feeding					###############
#######################################################################
CREATE TABLE FEED_TYPES (
	`feedTypeId`					INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `feedType` 						VARCHAR(100) NOT NULL,
    `driedMatterPercentage`			DOUBLE NOT NULL,
    `enabled`						BOOL,
    PRIMARY KEY ( feedTypeId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
) ENGINE = InnoDB;

/*
CREATE TABLE LOCALES_FEEDING (
	`localeFeedingId`			INT UNIQUE NOT NULL AUTO_INCREMENT,
	`localeId`					INT NOT NULL,
    `feedingId`					INT NOT NULL,
    `groupedHerdsId`				INT NOT NULL,
    PRIMARY KEY ( localeFeedingId ),
    FOREIGN KEY ( localeId ) REFERENCES LOCALE( localeId ),
    FOREIGN KEY ( groupedHerdsId ) REFERENCES GROUPED_HERDS( groupedHerdsId )
) ENGINE = InnoDB;*/

CREATE TABLE FEEDING (
	`feedingId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedingTime`					DATETIME,
	`bunckScore`					INT,
	`deliveredAmountTMRD`			DOUBLE,
	`userId`						INT NOT NULL,
	`hasLeftovers`					BOOL,
    `groupedHerdsId`				INT NOT NULL,
    PRIMARY KEY ( feedingId ),
    FOREIGN KEY ( userId ) REFERENCES USERS( userId ),
    FOREIGN KEY ( groupedHerdsId ) REFERENCES GROUPED_HERDS( groupedHerdsId )
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

/*
DELIMITER $$
DROP PROCEDURE IF EXISTS feedlot.SAVE_OR_UPDATE_HERD $$
CREATE PROCEDURE feedlot.SAVE_OR_UPDATE_HERD
(
	IN `p_herdId`	INT,
    IN `p_farmId` INT,
    IN `p_quantity` INT,
    IN `p_weight` DOUBLE,
    IN `p_cost` DOUBLE,
    IN `p_tagNumber` VARCHAR(15),
    IN `p_estimatedSaleDate` DATETIME,
    IN `p_implantDate` DATETIME,
    IN `p_optiflexDate` DATETIME,
    IN `p_dateEntered` DATETIME,
    IN `p_supplierId` INT,
    IN `p_sold` BOOLEAN
)
BEGIN
DECLARE newHerdId INT;
IF EXISTS (SELECT herdId FROM HERD WHERE herdId = p_herdId)
THEN
  UPDATE HERD 
  SET 
	quantity=p_quantity,
    weight=p_weight,
    cost=p_cost,
    tagNumber=p_tagNumber,
    estimatedSaleDate=p_estimatedSaleDate,
    implantDate=p_implantDate,
    optiflexDate=p_optiflexDate,
    dateEntered=p_dateEntered,
    supplierId=p_supplierId,
    sold=p_sold
    WHERE herdId=p_herdId AND farmId = p_farmId;
else
  INSERT INTO HERD 
  VALUES (0,p_farmId,p_quantity,p_weight,p_cost,p_tagNumber,p_estimatedSaleDate,p_implantDate,p_optiflexDate,p_dateEntered,p_supplierId,p_sold);
  SELECT LAST_INSERT_ID() INTO newHerdId;
  INSERT INTO HERD_LOCALE_MAP VALUES (0,null,newHerdId,NOW());
END IF;
END $$
DELIMITER ;

CALL SAVE_OR_UPDATE_HERD(8,1,2,3,4,'5',NOW(),NOW(),NOW(),NOW(),2,false);*/