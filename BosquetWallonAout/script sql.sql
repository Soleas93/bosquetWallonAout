 ---------------------------------------------------------------
 --        Script Oracle.  
 ---------------------------------------------------------------


------------------------------------------------------------
-- Table: BWA_Person
------------------------------------------------------------
CREATE TABLE BWA_Person(
	id            NUMBER NOT NULL ,
	firstName     VARCHAR2 (50) NOT NULL  ,
	lastName      VARCHAR2 (50) NOT NULL  ,
	address       VARCHAR2 (120) NOT NULL  ,
	passwordHash  VARCHAR2 (64) NOT NULL  ,
	emailAddress  VARCHAR2 (80) NOT NULL  ,
	role          VARCHAR2(9) ,
	CONSTRAINT BWA_Person_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_role CHECK (role IN ('Spectator','Artist','Manager','Organizer'))
);

------------------------------------------------------------
-- Table: BWA_Spectator
------------------------------------------------------------
CREATE TABLE BWA_Spectator(
	id            NUMBER(10,0)  NOT NULL  ,
	phoneNumber   VARCHAR2 (12) NOT NULL  ,
	gender        VARCHAR2(5) ,
	birthDate     DATE  NOT NULL  ,
	firstName     VARCHAR2 (50) NOT NULL  ,
	lastName      VARCHAR2 (50) NOT NULL  ,
	address       VARCHAR2 (120) NOT NULL  ,
	passwordHash  VARCHAR2 (64) NOT NULL  ,
	emailAddress  VARCHAR2 (80) NOT NULL  ,
	role          VARCHAR2(9) ,
	CONSTRAINT BWA_Spectator_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_gender CHECK (gender IN ('Man','Woman')),
	CONSTRAINT CHK_TYPE_role CHECK (role IN ('Spectator','Artist','Manager','Organizer'))

	,CONSTRAINT BWA_Spectator_BWA_Person_FK FOREIGN KEY (id) REFERENCES BWA_Person(id)
);

------------------------------------------------------------
-- Table: BWA_Artist
------------------------------------------------------------
CREATE TABLE BWA_Artist(
	id            NUMBER(10,0)  NOT NULL  ,
	showName      VARCHAR2 (12) NOT NULL  ,
	firstName     VARCHAR2 (50) NOT NULL  ,
	lastName      VARCHAR2 (50) NOT NULL  ,
	address       VARCHAR2 (120) NOT NULL  ,
	passwordHash  VARCHAR2 (64) NOT NULL  ,
	emailAddress  VARCHAR2 (80) NOT NULL  ,
	role          VARCHAR2(9) ,
	CONSTRAINT BWA_Artist_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_role CHECK (role IN ('Spectator','Artist','Manager','Organizer'))

	,CONSTRAINT BWA_Artist_BWA_Person_FK FOREIGN KEY (id) REFERENCES BWA_Person(id)
);

------------------------------------------------------------
-- Table: BWA_Manager
------------------------------------------------------------
CREATE TABLE BWA_Manager(
	id            NUMBER(10,0)  NOT NULL  ,
	phoneNumber   VARCHAR2 (12) NOT NULL  ,
	firstName     VARCHAR2 (50) NOT NULL  ,
	lastName      VARCHAR2 (50) NOT NULL  ,
	address       VARCHAR2 (120) NOT NULL  ,
	passwordHash  VARCHAR2 (64) NOT NULL  ,
	emailAddress  VARCHAR2 (80) NOT NULL  ,
	role          VARCHAR2(9) ,
	CONSTRAINT BWA_Manager_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_role CHECK (role IN ('Spectator','Artist','Manager','Organizer'))

	,CONSTRAINT BWA_Manager_BWA_Person_FK FOREIGN KEY (id) REFERENCES BWA_Person(id)
);

------------------------------------------------------------
-- Table: BWA_Organizer
------------------------------------------------------------
CREATE TABLE BWA_Organizer(
	id            NUMBER(10,0)  NOT NULL  ,
	phoneNumber   VARCHAR2 (12) NOT NULL  ,
	firstName     VARCHAR2 (50) NOT NULL  ,
	lastName      VARCHAR2 (50) NOT NULL  ,
	address       VARCHAR2 (120) NOT NULL  ,
	passwordHash  VARCHAR2 (64) NOT NULL  ,
	emailAddress  VARCHAR2 (80) NOT NULL  ,
	role          VARCHAR2(9) ,
	CONSTRAINT BWA_Organizer_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_role CHECK (role IN ('Spectator','Artist','Manager','Organizer'))

	,CONSTRAINT BWA_Organizer_BWA_Person_FK FOREIGN KEY (id) REFERENCES BWA_Person(id)
);

------------------------------------------------------------
-- Table: BWA_Order
------------------------------------------------------------
CREATE TABLE BWA_Order(
	id                NUMBER NOT NULL ,
	paymentMethod     VARCHAR2(6) ,
	deliveryMethod    VARCHAR2(11) ,
	total             FLOAT (24)  NOT NULL  ,
	id_BWA_Spectator  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT BWA_Order_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_paymentMethod CHECK (paymentMethod IN ('Visa','Paypal','Sepa')),
	CONSTRAINT CHK_TYPE_deliveryMethod CHECK (deliveryMethod IN ('OnSite','Post','SecuredPost'))

	,CONSTRAINT BWA_Order_BWA_Spectator_FK FOREIGN KEY (id_BWA_Spectator) REFERENCES BWA_Spectator(id)
);

------------------------------------------------------------
-- Table: BWA_Configuration
------------------------------------------------------------
CREATE TABLE BWA_Configuration(
	id           NUMBER NOT NULL ,
	type         VARCHAR2(8) ,
	description  CLOB  NOT NULL  ,
	CONSTRAINT BWA_Configuration_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_type CHECK (type IN ('Standing','Concert','Circus'))
);

------------------------------------------------------------
-- Table: BWA_Category
------------------------------------------------------------
CREATE TABLE BWA_Category(
	id                    NUMBER NOT NULL ,
	type                  VARCHAR2(8) ,
	price                 FLOAT (24)  NOT NULL  ,
	availableTickets      NUMBER(10,0)  NOT NULL  ,
	maximumTickets        NUMBER(10,0)  NOT NULL  ,
	id_BWA_Configuration  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT BWA_Category_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_type CHECK (type IN ('Standing','Diamond','Gold','Silver','Bronze'))

	,CONSTRAINT BWA_Category_BWA_Configuration_FK FOREIGN KEY (id_BWA_Configuration) REFERENCES BWA_Configuration(id)
);

------------------------------------------------------------
-- Table: BWA_Planning
------------------------------------------------------------
CREATE TABLE BWA_Planning(
	id         NUMBER NOT NULL ,
	beginDate  DATE  NOT NULL  ,
	endDate    DATE  NOT NULL  ,
	CONSTRAINT BWA_Planning_PK PRIMARY KEY (id)
);

------------------------------------------------------------
-- Table: BWA_Show
------------------------------------------------------------
CREATE TABLE BWA_Show(
	id               NUMBER NOT NULL ,
	title            VARCHAR2 (30) NOT NULL  ,
	description      CLOB  NOT NULL  ,
	image            BLOB  NOT NULL  ,
	ticketPerPerson  NUMBER(10,0)  NOT NULL  ,
	id_BWA_Planning  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT BWA_Show_PK PRIMARY KEY (id)

	,CONSTRAINT BWA_Show_BWA_Planning_FK FOREIGN KEY (id_BWA_Planning) REFERENCES BWA_Planning(id)
);

------------------------------------------------------------
-- Table: BWA_Representation
------------------------------------------------------------
CREATE TABLE BWA_Representation(
	id           NUMBER NOT NULL ,
	date         DATE  NOT NULL  ,
	beginHour    DATE  NOT NULL  ,
	endHour      DATE  NOT NULL  ,
	id_BWA_Show  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT BWA_Representation_PK PRIMARY KEY (id)

	,CONSTRAINT BWA_Representation_BWA_Show_FK FOREIGN KEY (id_BWA_Show) REFERENCES BWA_Show(id)
);

------------------------------------------------------------
-- Table: BWA_Ticket
------------------------------------------------------------
CREATE TABLE BWA_Ticket(
	id                     NUMBER NOT NULL ,
	price                  FLOAT (24)  NOT NULL  ,
	id_BWA_Order           NUMBER(10,0)  NOT NULL  ,
	id_BWA_Representation  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT BWA_Ticket_PK PRIMARY KEY (id)

	,CONSTRAINT BWA_Ticket_BWA_Order_FK FOREIGN KEY (id_BWA_Order) REFERENCES BWA_Order(id)
	,CONSTRAINT BWA_Ticket_BWA_Representation0_FK FOREIGN KEY (id_BWA_Representation) REFERENCES BWA_Representation(id)
);

------------------------------------------------------------
-- Table: BWA_Booking
------------------------------------------------------------
CREATE TABLE BWA_Booking(
	id                NUMBER NOT NULL ,
	deposit           FLOAT (24)  NOT NULL  ,
	balance           FLOAT (24)  NOT NULL  ,
	status            VARCHAR2(8) ,
	price             FLOAT (24)  NOT NULL  ,
	id_BWA_Organizer  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT BWA_Booking_PK PRIMARY KEY (id),
	CONSTRAINT CHK_TYPE_status CHECK (status IN ('Canceled','Pending','Paid'))

	,CONSTRAINT BWA_Booking_BWA_Organizer_FK FOREIGN KEY (id_BWA_Organizer) REFERENCES BWA_Organizer(id)
);

------------------------------------------------------------
-- Table: BWA_Show_PlayedBy_Artists
------------------------------------------------------------
CREATE TABLE BWA_Show_PlayedBy_Artists(
	id           NUMBER(10,0)  NOT NULL  ,
	id_BWA_Show  NUMBER(10,0)  NOT NULL  ,
	CONSTRAINT BWA_Show_PlayedBy_Artists_PK PRIMARY KEY (id,id_BWA_Show)

	,CONSTRAINT BWA_Show_PlayedBy_Artists_BWA_Artist_FK FOREIGN KEY (id) REFERENCES BWA_Artist(id)
	,CONSTRAINT BWA_Show_PlayedBy_Artists_BWA_Show0_FK FOREIGN KEY (id_BWA_Show) REFERENCES BWA_Show(id)
);


------------------------------------------------------------
-- Sequences: PK
-----------------------------------------------------------


CREATE SEQUENCE Seq_BWA_Person_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Order_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Configuration_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Category_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Planning_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Show_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Representation_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Ticket_id START WITH 1 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE Seq_BWA_Booking_id START WITH 1 INCREMENT BY 1 NOCYCLE;

------------------------------------------------------------
-- Triggers : Sequences auto-increment
-----------------------------------------------------------

CREATE OR REPLACE TRIGGER BWA_Person_id
	BEFORE INSERT ON BWA_Person 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Person_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Order_id
	BEFORE INSERT ON BWA_Order 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Order_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Configuration_id
	BEFORE INSERT ON BWA_Configuration 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Configuration_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Category_id
	BEFORE INSERT ON BWA_Category 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Category_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Planning_id
	BEFORE INSERT ON BWA_Planning 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Planning_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Show_id
	BEFORE INSERT ON BWA_Show 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Show_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Representation_id
	BEFORE INSERT ON BWA_Representation 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Representation_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Ticket_id
	BEFORE INSERT ON BWA_Ticket 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Ticket_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;
	/
CREATE OR REPLACE TRIGGER BWA_Booking_id
	BEFORE INSERT ON BWA_Booking 
  FOR EACH ROW 
	WHEN (NEW.id IS NULL) 
	BEGIN
		 select Seq_BWA_Booking_id.NEXTVAL INTO :NEW.id from DUAL; 
	END;

