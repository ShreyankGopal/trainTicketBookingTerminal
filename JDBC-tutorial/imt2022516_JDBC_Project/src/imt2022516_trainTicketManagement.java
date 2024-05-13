import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.Console;

public class imt2022516_trainTicketManagement {
   
    // Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/trainTicketManagement?useSSL=false";

   // Database credentials
   static final String USER = "root";// add your user
   static final String PASSWORD = "";// add password

   public static void main(String[] args) {
      Connection conn = null;
      Statement stmt = null;
      
      Table tables=new Table();
      checkTicket ViewTicket=new checkTicket();
      insert insertIntoTable=new insert();
      Scanner sc=new Scanner(System.in);
      delete delete=new delete();
      // STEP 2. Connecting to the Database
      try {
         // STEP 2a: Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // STEP 2b: Open a connection
         System.out.println("Connecting to database...");
         conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         // STEP 2c: Execute a query
         System.out.println("Creating statement...");
         conn.setAutoCommit(false);
         stmt = conn.createStatement();
         ResultSet rs=null;
         int custid=-1;

         while(true){
            
            int input=-1;
            System.out.println("Click 1 to View your credentials");
            System.out.println("Click 2 to purchase ticket");
            System.out.println("Click 3 to Register Customer");
            System.out.println("Click 4 to See your tickets");
            System.out.println("Click 5 to login");
            System.out.println("Click 6 to logout");
            System.out.println("click 7 to Delete Customer");
            System.out.println("click 8 to Delete Ticket");
            System.out.println("click 9 to Change Name");
            System.out.println("click 10 to exit");
            try{
               input=Integer.parseInt(sc.nextLine());
            }
            catch(Exception e){
               System.err.println("Enter Integer");
            }
            if(input==10){
               System.exit(0);
            }
            
            if(input==1){
               if(custid==-1){
                  System.out.println("Login Required");
                  
                  
               }
               rs=stmt.executeQuery(String.format("SELECT custID, fname, lname, sex, EMAIL FROM Customer where custID=%d",custid));
               while(rs.next()){
               int custID = Integer.parseInt(rs.getString("custID"));

               String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String sex = rs.getString("sex");
                String email = rs.getString("EMAIL");

                // Print the retrieved information
                
                System.out.println("Customer ID: " + custID);
                System.out.println("First Name: " + fname);
                System.out.println("Last Name: " + lname);
                System.out.println("Sex: " + sex);
                System.out.println("Email: " + email);
                System.out.println();
               }
            }
            if(input==9){
               System.out.print("First name : ");
                  String fname=sc.nextLine();
                  System.out.print("Last name : ");
                  String lname=sc.nextLine();

                  stmt.executeUpdate(String.format("UPDATE Customer SET fname='%s', lname='%s' where custID=%d",fname,lname,custid));
                  conn.commit();

            }
            if(input==3){
               if(custid!=-1){
                  System.out.println("You can do this operation only if you log out\n");
                  continue;
               }
               insertIntoTable.insertCustomer(conn,stmt);
            }
            if(input==4){
               if(custid==-1){
                  System.out.println("Login Required");
                  continue;
               }
               else{
                  ViewTicket.checkYourTicket(conn,custid);
               }
               
            }
            if(input==2){
               int inp;
               if(custid==-1){
                  System.out.println("Login Required");
                  continue;
               }
               
               System.out.println("Where to?");
               System.out.println("1 for Bangalore - Mysore");
               System.out.println("2 for Bangalore - Chennai");
               System.out.println("3 for Bangalore - Hyderabad");
               System.out.println("4 for Jaipur - Calcutta");
               inp=Integer.parseInt(sc.nextLine());
               if(inp==1 || inp==2 || inp==3 || inp==4){
                  String journeyRoute="";
                  if(inp==1){
                     journeyRoute="Bangalore - Mysore";

                  }
                  if(inp==2){
                     journeyRoute="Bangalore - Chennai";
                     
                  }
                  if(inp==3){
                     journeyRoute="Bangalore - Hyderabad";
                     
                  }
                  if(inp==4){
                     journeyRoute="Jaipur - Calcutta";
                     
                  }
                  System.out.print("Year : ");
                  int year= Integer.parseInt(sc.nextLine());
                  System.out.print("Month : ");
                  int Month= Integer.parseInt(sc.nextLine());
                  System.out.print("day : ");
                  int day= Integer.parseInt(sc.nextLine());
                  LocalDate bookingDate = LocalDate.of(year,Month,day);
                  DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                  String bookDate=bookingDate.format(formatter);
                  String query=String.format("SELECT * from Trains Tr,Timing Ti where Tr.trainID=Ti.trainID and Tr.Route='%s' And DATE(Ti.ArrivalTime)='%s'",journeyRoute,bookDate);
                  rs=stmt.executeQuery(query);
                  LinkedList<String> datesForBooking=new LinkedList<>();
                  LinkedList<Integer>ListofTrains =new LinkedList<>();
                  while(rs.next()){
                     
                     if(Integer.parseInt(rs.getString("NumberOfSeats"))!=0){
                        ListofTrains.addLast(Integer.parseInt(rs.getString("trainID")));
                        datesForBooking.addLast(rs.getString("ArrivalTime"));
                        
                        continue;
                     }
                  }
                  if(datesForBooking.size()==0 || ListofTrains.size()==0){
                     System.out.println("No Trains available on this day :( \n");
                     
                  }
                  

                  else{
                     System.out.println("Choose Time");
                     for(int i=0;i<datesForBooking.size();i++){
                        System.out.println("Type "+i+" for "+datesForBooking.get(i) +"\n");
                     }
                     int optionDateChosen=Integer.parseInt(sc.nextLine());
                     System.out.println("Confirm Booking?");
                     System.out.println("type y for YES I CONFIRM");
                     System.out.println("type n for NO I WANT TO CANCEL");
                     String yesOrNo = sc.nextLine();
                     
                     int Tid=0;
                     String Tno="0";
                     int NumberOfSeats=0;
                     if(yesOrNo.equals("y")){

                        String queryToFindTrainDetails=String.format("SELECT * from Trains Tr,Timing Ti where   Tr.trainID=Ti.trainID and Tr.Route='%s' And Ti.ArrivalTime='%s' LIMIT 1",journeyRoute,datesForBooking.get(optionDateChosen));
                        rs=stmt.executeQuery(queryToFindTrainDetails);
                        while(rs.next()){
                           NumberOfSeats=Integer.parseInt(rs.getString("NumberOfSeats"));

                           Tid=Integer.parseInt(rs.getString("trainID"));
                           
                           Tno=rs.getString("Train_No");
                           
                        }
                        NumberOfSeats=NumberOfSeats-1;
                        String TicketNumber=Tno+Tid+custid;
                        try{

                        
                        insertIntoTable.insertTicket(conn,stmt,TicketNumber);
                        stmt.executeUpdate(String.format("Update Trains set NumberOfSeats=%d where trainID=%d",NumberOfSeats,Tid));// here we insert the ticket
                        int ticketID=0;
                        String query3="SELECT ticketID FROM Ticket ORDER BY ticketID DESC LIMIT 1";
                        ResultSet resultOfGettingLastRow=stmt.executeQuery(query3);
                        while(resultOfGettingLastRow.next()){
                           ticketID=Integer.parseInt(resultOfGettingLastRow.getString("ticketID"));
                        }
                        insertIntoTable.insertCustomerTicket(conn, stmt, ticketID, custid);//linked cust id and ticket over here
                        insertIntoTable.insertTicketTrain(conn,stmt,ticketID,Tid);
                        conn.commit();
                     }
                     catch(SQLException sql){
                        conn.rollback();
                        
                     }

                     
                     }
                     else{
                        continue;
                     }
                  }
               }
            }
            if(input==5){
               System.out.println("Enter Email ID : ");
               String Email=sc.nextLine();
               Console console = System.console();
               if (console == null) {
                  System.err.println("No console available");
                  System.exit(1);
               }

               char[] passwordArray = console.readPassword("Enter your password: ");
               String password = new String(passwordArray);
               String checkCredQuery=String.format("Select * from Customer where EMAIL='%s' AND Password='%s'",Email,password);
               rs=stmt.executeQuery(checkCredQuery);
               int x=0;
               while(rs.next()){
                  String email=rs.getString("EMAIL");
                  custid=Integer.parseInt(rs.getString("custID"));
                  if(email.equals(Email)){
                     x++;
                  }
               }
               if(x>0){
                  System.out.println("Logged in Successfully\n");
               }
               else{
                  System.out.println("Wrong Email or Password Login again\n");
               }
               
            }
            if(input==6){
               custid=-1;
               System.out.println("Logged out successfully\n");
            }
            if(input==7){
               if(custid==-1){
                  System.out.println("login required\n");
                  continue;
               }
               try{
                  delete.deleteCustomer(conn, stmt, custid);
                  conn.commit();
               }
               catch(SQLException E){
                  System.out.println("Rolling back");
                  conn.rollback();
               }
               

               custid=-1;
            }
            if(input==8){
               System.out.print("Enter Ticket Number : ");
               String ticketnum=sc.nextLine();
               System.out.println("");
               try{
                  delete.deleteTicket(conn, stmt, ticketnum);
                  conn.commit();
               }
               catch(SQLException E){
                  conn.rollback();

               }
               
            }
         }
      } catch (SQLException se) { // Handle errors for JDBC

         
         try{
            conn.rollback();
            System.out.println("Transaction rolled back");
         }
         catch(Exception ex){
            System.out.println("Cannot rollback");
         }
      } catch (Exception e) { // Handle errors for Class.forName
         e.printStackTrace();
      } finally { // finally block used to close resources regardless of whether an exception was
                  // thrown or not
         try {
            if (stmt != null)
               stmt.close();
         } catch (SQLException se2) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         } // end finally try
      } // end try
      System.out.println("End of Code");
   } // end main
}
