DROP DATABASE If EXISTS myDB;
CREATE DATABASE myDB;
USE myDB;

CREATE TABLE provides(
	Whenn Date,
	CreditcardNumber int(16) references CreditCard on delete cascade,
	AccountNum int(12) references Bankinfo on update cascade,
	UserName varchar(100) references Users on update cascade,
	PRIMARY KEY (CreditcardNumber,AccountNum,UserName) 
);

CREATE TABLE Users(
	UserName varchar(100) NOT NULL,
	Password varchar(100) NOT NULL,
	FName varchar(100),
	LName varchar(100),
	State varchar(100),
	Street varchar(100),
	City varchar(100),
	ZipCode int(5),
	Iscustomerrep boolean,
	Isadmin boolean,
	PRIMARY KEY (UserName)
);

CREATE TABLE Footwear(
	ItemNum varchar(100) NOT NULL,
	Typee varchar(100),
	Namee varchar(100),
	Conditionn varchar(100),
	Color varchar(20),
	Size varchar(100),
	BrandName varchar(100),
	PRIMARY KEY (ItemNum)
);

CREATE TABLE Sells(
	EndDate Date,
	MinPrice varchar(100),
	UserName varchar(100) references Users on update cascade,
	IsClosed boolean,
	ItemNum varchar(100) references Footwear on delete cascade,
	PRIMARY KEY (UserName,ItemNum)
);

CREATE TABLE Buys(
	BidPrice varchar(100),
	UserName varchar(100) references Users on update cascade,
	ItemNum varchar(100) references Footwear on delete cascade,
	PRIMARY KEY (UserName,ItemNum)
);

CREATE TABLE ParticipateIn(
	maxPrice varchar(100),
	UserName varchar(100) references Users on update cascade,
	IsManual boolean,
	PRIMARY KEY (Username)
);

CREATE TABLE Bid(
	BidID varchar(100) NOT NULL,
	Timee time,
	Datee date,
	PRIMARY KEY (BidID)
);

CREATE TABLE OnBid(
	BidID varchar(100) references Bid on delete cascade,
	ItemNum varchar(100) references Footwear on delete cascade,
	PRIMARY KEY (BidID,ItemNum)	
);


CREATE TABLE Alerts(
	AlertID varchar(100) NOT NULL,
	PRIMARY KEY (AlertID)
);

CREATE TABLE Sets(
	whenn date,
	timee time,
	UserName varchar(100) references Users on update cascade,
	AlertID varchar(100) references Alerts on delete cascade,
	PRIMARY KEY (UserName,AlertID)
);

CREATE TABLE ForItem(
	AlertID varchar(100) references Alerts on delete cascade,
	ItemNum varchar(100) references Footwear on delete cascade,
	PRIMARY KEY (AlertID,ItemNum)	
);

CREATE TABLE Question(
	Answer varchar(250),
	QID varchar(100) NOT NULL,
	PRIMARY KEY (QID)
);

CREATE TABLE asks(
	UserName varchar(100) references Users on update cascade,
	QID varchar(100) references Question,
	PRIMARY KEY (Username,QID)
);

CREATE TABLE AnsweredBy(
	QID varchar(100) references Question,
	UserName varchar(100) references Users on update cascade,
	PRIMARY KEY (QID,UserName)
);

CREATE TABLE buyer(
	UserName varchar(100) references Users on update cascade,
	PRIMARY KEY (UserName)
);

CREATE TABLE Seller(
	UserName varchar(100) references Users on update cascade,
	PRIMARY KEY (UserName)
);

CREATE TABLE CreditCard(
	CreditcardNumber int(16) NOT NULL,
	ExpiryDate Date,
	CVV int(3),
	PRIMARY KEY (CreditcardNumber)
);

CREATE TABLE Bankinfo(
	RoutingNum int(12),
	AccountNum int(12) NOT NULL,
	PRIMARY KEY (AccountNum)
);