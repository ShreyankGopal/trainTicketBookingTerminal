public class Table{
    String createTableCustomer(){
        String statement="Create table Customer("+
        "custID int AUTO_INCREMENT primary key,"+
        "fname varchar(15) NOT NULL,"+
        "lname varchar(15) NOT NULL,"+
        "sex char(1) NOT NULL"+
        "EMAIL varchar(30) UNIQUE"+
        "Password varchar(30)"+
        
        ")";
        return statement;

   }
   String createTableTicket(){
      String statement="Create table Ticket("+
         "ticketID  int AUTO_INCREMENT primary key,"+
         "ticketNo char(20) NOT NULL"+
         ")";
      return statement;
   }
   String createCustomerTicket(){
      String statement="create table TicketBought("+
      "CustomerID int,"+
      "TicketID int,"+
      "constraint pk_cust_tick PRIMARY KEY(CustomerID,TicketID),"+
      "constraint fk_cust FOREIGN KEY(CustomerID) REFERENCES Customer(custID) on delete cascade,"+
      "constraint fk_Ticket_bought FOREIGN KEY(TicketID) REFERENCES Ticket(ticketID) on delete cascade"+
      ")";
      return statement;
   }
   String createTrainTicket(){
    String statement="create table Ticket_Train("+
    "TicketID int,"+
    "TrainID int,"+
    "constraint pk_train_tick PRIMARY KEY(TrainID,TicketID),"+
    "constraint fk_trainTicket FOREIGN KEY(TrainID) REFERENCES Trains(trainID) on delete cascade,"+
    "constraint fk_Ticket FOREIGN KEY(TicketID) REFERENCES Ticket(ticketID) on delete cascade"+
    ")";
    return statement;
 }
   String createTrain(){
      String statement="create table Trains("+
      "trainID int primary key,"+
      "trainName varchar(20),"+
      "Train_No char(20),"+
      "Route varchar(30)"+
      
      ")";
      return statement;
   }
   String timingInfo(){
      String statement="create table Timing("+
      "trainID int primary key,"+
      
      "ArrivalTime TIMESTAMP,"+
      "DepartureTime TIMESTAMP,"+
      "constraint fk_train Foreign Key(trainID) references Trains(trainID) ON DELETE CASCADE"+

      
      ")";
      return statement;
   }
}
