USE feedlot;
INSERT INTO `FARM` VALUES 
	(1,'Holz Bros.');
INSERT INTO `USERS` VALUES 
	(1,'JHolz','James','Holz','test@test.com','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',1,1,1),
	(2,'HHolz','Howard','Holz','test2@test.com','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',1,1,1);
INSERT INTO `ROLES` VALUES 
	('HHolz','Default'),
    ('JHolz','Admin'),
    ('JHolz','Default');
INSERT INTO `FEED_TYPES` VALUES 
	(1,1,'Corn',50,1),
    (2,1,'Hay',90,1);
INSERT INTO `SUPPLIER` VALUES 
	(1,'Bob\'s Big Ass Cows','Kearney, NE',1),
    (2,'The Cow Locker','Rockford, IL',1);
INSERT INTO `HERD` VALUES 
	(1,1,10,3000,10000,'T2','2016-01-20 00:00:00','2016-01-27 00:00:00','2016-02-13 00:00:00','2016-01-20 20:50:07',2,null),
	(2,1,32,9000,30300,'K12','2016-01-24 00:00:00','2016-02-04 00:00:00','2016-02-05 00:00:00','2016-01-20 20:51:31',1,null),
	(3,1,1,289,1530,'L4','2016-01-13 00:00:00','2016-01-28 00:00:00','2016-03-03 00:00:00','2016-01-20 20:53:55',1,null),
	(4,1,23,6000,34094,'PL','2016-01-20 00:00:00','2016-01-27 00:00:00','2008-11-11 00:00:00','2016-01-20 21:34:21',1,NULL),
	(5,1,7,1400,8000,'U2','2016-04-05 00:00:00','2016-04-06 00:00:00','2016-06-07 00:00:00','2016-01-20 21:35:01',2,NULL),
	(6,1,100,75000,78000,'G2','2015-12-12 00:00:00','2015-12-13 00:00:00','2016-01-04 00:00:00','2016-01-20 21:36:14',2,null);
INSERT INTO `LOCALE` VALUES 
	(1,1,'SE Lot',1),
    (2,1,'NE Back Lot',1),
    (3,1,'Small Pasture',1);
INSERT INTO `PACKER` VALUES 
	(1,'Tyson','Fort Dodge, IA',1),
    (2,'Scuba Steve Packers Inc.','Miami, FL',1);