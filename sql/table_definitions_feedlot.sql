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
);

CREATE TABLE USERS (
	`userId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `username`						VARCHAR(50) NOT NULL UNIQUE,
    `firstName`						VARCHAR(25) NOT NULL,
	`lastName`						VARCHAR(25) NOT NULL,
	`email`							VARCHAR(50) NOT NULL,
	`password`						VARCHAR(100) NOT NULL,
	`farmId`						INT NOT NULL,
	`enabled`						TINYINT NOT NULL DEFAULT 1,
    `forcePasswordReset`			TINYINT NOT NULL DEFAULT 1, 
	PRIMARY KEY ( userId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
);

CREATE TABLE ROLES (
    `username`						VARCHAR(50) NOT NULL,
    `role`							VARCHAR(50) NOT NULL,
	PRIMARY KEY (role,username),
    FOREIGN KEY ( username ) REFERENCES USERS( username )
);

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
);

CREATE TABLE PACKER (
	`packerId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `packerName`					VARCHAR(100) NOT NULL,
    `packerLocation`				VARCHAR(100),
    `farmId`						INT NOT NULL,
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId ),
    PRIMARY KEY ( packerId )
);

CREATE TABLE LOCALE (
	`localeId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `localeName`					VARCHAR(200) NOT NULL,
    `enabled`						BOOL NOT NULL,
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId ),
    PRIMARY KEY ( localeId )
);

CREATE TABLE GROUPED_HERDS (
	`groupedHerdsId`				INT UNIQUE NOT NULL AUTO_INCREMENT,
    `localeId`						INT NULL,
    `isSold`						BOOLEAN,
	PRIMARY KEY ( groupedHerdsId ),
    FOREIGN KEY ( localeId ) REFERENCES LOCALE( localeId )
);

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
    `farmId` 						INT NOT NULL,
    PRIMARY KEY ( saleId ),
	FOREIGN KEY ( groupedHerdsId ) REFERENCES GROUPED_HERDS( groupedHerdsId ),
    FOREIGN KEY ( packerId ) REFERENCES PACKER( packerId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
);

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
    `sex`							VARCHAR(20),
	`herdLabel`						VARCHAR(100),
    `deadQuantity`					INT,
    PRIMARY KEY ( herdId ),
    FOREIGN KEY ( supplierId ) REFERENCES SUPPLIER( supplierId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId ),
    FOREIGN KEY ( groupedHerdsId ) REFERENCES GROUPED_HERDS( groupedHerdsId )
);


#######################################################################
############			TABLES Feeding					###############
#######################################################################
CREATE TABLE FEED_TYPES (
	`feedTypeId`					INT UNIQUE NOT NULL AUTO_INCREMENT,
    `farmId`						INT NOT NULL,
    `feedType` 						VARCHAR(200) NOT NULL,
    `driedMatterPercentage`			DOUBLE NOT NULL,
    `enabled`						BOOL,
    PRIMARY KEY ( feedTypeId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
);

CREATE TABLE FEEDING (
	`feedingId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedingTime`					DATETIME,
	`bunkScore`						INT,
	`deliveredAmount`				DOUBLE,
	`userId`						INT NOT NULL,
	`hasLeftovers`					BOOL,
    `groupedHerdsId`				INT NOT NULL,
    `farmId`						INT NOT NULL,
    `lastUpdated`					DATETIME,
    PRIMARY KEY ( feedingId ),
    FOREIGN KEY ( userId ) REFERENCES USERS( userId ),
    FOREIGN KEY ( groupedHerdsId ) REFERENCES GROUPED_HERDS( groupedHerdsId ),
    FOREIGN KEY ( farmId ) REFERENCES FARM( farmId )
);

CREATE TABLE FEED (
	`feedId`						INT UNIQUE NOT NULL AUTO_INCREMENT,
	`feedAmount`					DOUBLE NOT NULL,
    `feedTypeId`					INT NOT NULL,
    `feedingId`						INT NOT NULL,
    `ratio`						    DOUBLE NOT NULL,
    PRIMARY KEY ( feedId ),
    FOREIGN KEY ( feedTypeId ) REFERENCES FEED_TYPES( feedTypeId ),
    FOREIGN KEY ( feedingId ) REFERENCES FEEDING( feedingId )
);

DELIMITER //
USE feedlot //
DROP PROCEDURE IF EXISTS `PoundGainedPerDriedMaterPound` //
CREATE PROCEDURE PoundGainedPerDriedMaterPound(IN farmId int)
BEGIN
	DECLARE bDone INT;
    DECLARE `startWeight` DOUBLE;
	DECLARE `endWeight` DOUBLE;
	DECLARE `groupId` INT;
    DECLARE `driedWeight` DOUBLE; 
    DECLARE `herdsLabels` VARCHAR(500);
    DECLARE curs CURSOR FOR (SELECT GH.groupedHerdsId ,SUM(H.weight),
							(SELECT GROUP_CONCAT(HD.herdLabel) FROM HERD HD WHERE HD.groupedHerdsId = GH.groupedHerdsId) AS labels
							FROM GROUPED_HERDS  GH
							JOIN HERD H ON GH.groupedHerdsId = H.groupedHerdsId
							WHERE GH.isSold = 1 AND H.farmId = farmId
							GROUP BY GH.groupedHerdsId);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET bDone = 1;
	
    DROP TEMPORARY TABLE IF EXISTS TEMP_POUNDS_GAINED_PER_DRIED;
    CREATE TEMPORARY TABLE IF NOT EXISTS TEMP_POUNDS_GAINED_PER_DRIED (
        `id` INT,
        `poundGainedPerPoundOfDriedFood` DOUBLE,
        `herdsLabels` VARCHAR(500),
        `driedWeight` DOUBLE,
        `startWeight` DOUBLE,
        `endWeight` DOUBLE
    );

	OPEN curs;
	SET bDone = 0;
	groups: LOOP
		FETCH curs INTO groupId, startWeight, herdsLabels;
        IF bDone = 1 THEN LEAVE groups; END IF;
        
        SELECT SUM(S.saleWeight) AS endWeight
		FROM GROUPED_HERDS  GH
		JOIN SALE S ON GH.groupedHerdsId = S.groupedHerdsId
		WHERE GH.isSold = 1 AND S.farmId = farmId AND GH.groupedHerdsId = groupId INTO endWeight;

        SELECT SUM(deliveredAmount * ratio * (driedMatterPercentage/100.0)) as driedFeedWeight INTO driedWeight
		FROM GROUPED_HERDS  GH
		JOIN FEEDING FG ON GH.groupedHerdsId = FG.groupedHerdsId
		JOIN FEED F ON F.feedingId = FG.feedingId
		JOIN FEED_TYPES FT ON F.feedTypeId = FT.feedTypeId
		WHERE GH.isSold = 1 AND GH.groupedHerdsId = groupId
		GROUP BY GH.groupedHerdsId;
        
		INSERT INTO TEMP_POUNDS_GAINED_PER_DRIED VALUES(groupId, (endWeight - startWeight)/driedWeight, herdsLabels,driedWeight,startWeight,endWeight);
    END LOOP groups;
    
	CLOSE curs;
	SELECT * FROM TEMP_POUNDS_GAINED_PER_DRIED;
END //
DELIMITER ;

DELIMITER //
USE feedlot //
DROP PROCEDURE IF EXISTS `OverviewOfSalesData` //
CREATE PROCEDURE OverviewOfSalesData(IN farmId int)
BEGIN
	DECLARE bDone INT;
    DECLARE `groupId` INT;
    DECLARE `startCount` DOUBLE;
	DECLARE `endCount` DOUBLE;
    DECLARE `startWeight` DOUBLE;
	DECLARE `endWeight` DOUBLE;
	DECLARE `purchasePrice` DOUBLE;
	DECLARE `salesAmount` DOUBLE;
    DECLARE `herdsLabels` VARCHAR(500);
    DECLARE curs CURSOR FOR (SELECT GH.groupedHerdsId, SUM(H.weight) AS startWeight, SUM(H.cost) AS purchasePrices, SUM(H.quantity) AS startAmount,
							(SELECT GROUP_CONCAT(HD.herdLabel) FROM HERD HD WHERE HD.groupedHerdsId = GH.groupedHerdsId) AS labels
							FROM GROUPED_HERDS  GH
							JOIN HERD H ON GH.groupedHerdsId = H.groupedHerdsId
							WHERE GH.isSold = 1 AND H.farmId = 1
							GROUP BY GH.groupedHerdsId);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET bDone = 1;
	
    DROP TEMPORARY TABLE IF EXISTS TEMP_SALES_OVERVIEW;
    CREATE TEMPORARY TABLE IF NOT EXISTS TEMP_SALES_OVERVIEW (
        `startCount` DOUBLE,
        `endCount` DOUBLE,
        `startWeight` DOUBLE,
        `endWeight` DOUBLE,
        `purchasePrice` DOUBLE,
        `salesAmount` DOUBLE,
        `herdsLabels` VARCHAR(500)
    );

	OPEN curs;
	SET bDone = 0;
	groups: LOOP
		FETCH curs INTO groupId, startWeight, purchasePrice, startCount, herdsLabels;
        IF bDone = 1 THEN LEAVE groups; END IF;
        
        SELECT SUM(S.saleWeight) AS endWeight, SUM(S.salePrice) AS salesAmount, SUM(S.quantity) AS endAmount
		FROM GROUPED_HERDS  GH
		JOIN SALE S ON GH.groupedHerdsId = S.groupedHerdsId
		WHERE GH.isSold = 1 AND S.farmId = 1 AND GH.groupedHerdsId = groupId INTO endWeight,salesAmount,endCount;
        
		INSERT INTO TEMP_SALES_OVERVIEW VALUES(startCount,endCount,startWeight,endWeight,purchasePrice,salesAmount,herdsLabels);
    END LOOP groups;
    
	CLOSE curs;
	SELECT * FROM TEMP_SALES_OVERVIEW;
END //
DELIMITER ;