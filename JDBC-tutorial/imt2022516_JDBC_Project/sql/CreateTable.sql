CREATE TABLE Customer (
    custID INT AUTO_INCREMENT PRIMARY KEY,
    fname VARCHAR(15) NOT NULL,
    lname VARCHAR(15) NOT NULL,
    sex CHAR(1) NOT NULL,
    EMAIL VARCHAR(30) UNIQUE,
    Password VARCHAR(30)
);

CREATE TABLE Ticket (
    ticketID INT AUTO_INCREMENT PRIMARY KEY,
    ticketNo CHAR(20) NOT NULL
);

CREATE TABLE TicketBought (
    CustomerID INT,
    TicketID INT,
    CONSTRAINT pk_cust_tick PRIMARY KEY (CustomerID, TicketID),
    CONSTRAINT fk_cust FOREIGN KEY (CustomerID) REFERENCES Customer(custID) ON DELETE CASCADE,
    CONSTRAINT fk_Ticket_bought FOREIGN KEY (TicketID) REFERENCES Ticket(ticketID) ON DELETE CASCADE
);



CREATE TABLE Trains (
    trainID INT PRIMARY KEY,
    trainName VARCHAR(20),
    Train_No CHAR(20),
    Route VARCHAR(30),
    NumberOfSeats int
);
CREATE TABLE Ticket_Train (
    TicketID INT,
    TrainID INT,
    CONSTRAINT pk_train_tick PRIMARY KEY (TrainID, TicketID),
    CONSTRAINT fk_trainTicket FOREIGN KEY (TrainID) REFERENCES Trains(trainID) ON DELETE CASCADE,
    CONSTRAINT fk_Ticket FOREIGN KEY (TicketID) REFERENCES Ticket(ticketID) ON DELETE CASCADE
);

CREATE TABLE Timing (
    trainID INT PRIMARY KEY,
    ArrivalTime TIMESTAMP,
    DepartureTime TIMESTAMP,
    CONSTRAINT fk_train FOREIGN KEY (trainID) REFERENCES Trains(trainID) ON DELETE CASCADE
);
