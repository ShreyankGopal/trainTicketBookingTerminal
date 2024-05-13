import java.sql.*;
import java.util.*;
public class delete {
    void deleteCustomer(Connection conn,Statement stmt,int custID){
        try{
            stmt.executeUpdate(String.format("delete from Customer where custID='%s'",custID));
            System.out.println("Customer Removed\n");
            
        }
        catch(SQLException se){
            se.printStackTrace();
            System.out.println("unable to delete");
            
            
        }
        
    }
    void deleteTicket(Connection conn,Statement stmt,String ticketNO){
        try{
            stmt.executeUpdate(String.format("delete from Ticket where ticketNo='%s'",ticketNO));
            System.out.println("Ticket Removed\n");
        }
        catch(SQLException se){
            se.printStackTrace();
            System.out.println("unable to delete");
        }
        
    }
}
