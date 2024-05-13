import java.sql.*;
import java.util.*;
import java.io.Console;
public class insert{
    static  int custID=0;

    static  int ticketID=100;
    static int trainID=10000;
 
    void insertCustomer(Connection conn,Statement stmt){
     
      

      
      
      
       Scanner scanner=new Scanner(System.in);
       System.out.print("First Name : ");
       String fname=scanner.nextLine();
 
       System.out.print("Last Name : ");
       String lname=scanner.nextLine();
       System.out.print("sex : ");
       String sex=scanner.nextLine();
       System.out.print("Email : ");
       String Email=scanner.nextLine();
       

       Console console = System.console();
        if (console == null) {
            System.err.println("No console available");
            System.exit(1);
        }

        char[] passwordArray = console.readPassword("Enter your password: ");
        String password = new String(passwordArray);
        char[] confirmPass = console.readPassword("Confirm Password : ");
        String confirmPassword=new String(confirmPass);
        if(!password.equals(confirmPassword)){
         System.out.println("Your Password doesnt match your confirmed password");
         return;
        }

        
      
       String statement=String.format("insert into Customer (fname,lname,sex,EMAIL,Password) values('%s','%s','%s','%s','%s')",fname,lname,sex,Email,password);
       
 
       try{
          stmt.executeUpdate(statement);
          System.out.println("Customer Added");
          conn.commit();
       }
       catch(SQLException e){
          System.out.println("Email Exists Already");
          try{
            conn.rollback();
          }
          catch(Exception se){
            se.printStackTrace();
          }
          
       }
       
       
       
 
   }
   
   void insertTicket(Connection conn,Statement stmt,String ticket){
      Statement stmt2=null; 
      try {
         stmt2=conn.createStatement();
       } catch (Exception e) {
        
         e.printStackTrace();
       }
       
       String statement=String.format("insert into Ticket (ticketNo) values('%s')",ticket);
       
       try{
         stmt.executeUpdate(statement);
         
      }
      catch(SQLException e){
         System.out.println(e.getMessage());
         e.printStackTrace();
      }
      String query="SELECT * FROM Ticket ORDER BY ticketID DESC LIMIT 1;";
       try {
         ResultSet resultSet=stmt2.executeQuery(query);
         int ticketID=0;
         String ticketnumber="";
         while(resultSet.next()){
            ticketID=Integer.parseInt(resultSet.getString("ticketID"));
            ticketnumber=resultSet.getString("ticketNo");
            
            ticketnumber+=ticketID;
            stmt.executeUpdate(String.format("UPDATE Ticket SET ticketNo = '%s' where ticketID=%d",ticketnumber,ticketID));

         }
         
         System.out.println("Ticket Added to your List");
         resultSet.close();
         
       }
       catch(SQLException se){
         se.printStackTrace();
         
       }
       
       

       
 
       try {
         stmt2.close();
       } catch (Exception e) {
         e.printStackTrace();
       }
      
       
 
    }
    void insertCustomerTicket(Connection conn,Statement stmt,int ticketID,int custID){
       //ticketID=25;
       
      String statement=String.format("insert into TicketBought values(%d,%d)",custID,ticketID);
      

      try{
         stmt.executeUpdate(statement);
         
      }
      catch(SQLException e){
         System.out.println("Couldnt add , invalid ticketID, cancelling transaction\n");
         try{
            conn.rollback();
         }
         catch(SQLException sql){

         }
         
        
      }
     
      

   }
   void insertTicketTrain(Connection conn,Statement stmt,int ticketID,int TrainID){
       
       
      String statement=String.format("insert into Ticket_Train values(%d,%d)",ticketID,TrainID);
      

      try{
         stmt.executeUpdate(statement);
         
      }
      catch(SQLException e){
         System.out.println("Invalid\n");
         
      }
      
      

   }
    
 
 
 }
