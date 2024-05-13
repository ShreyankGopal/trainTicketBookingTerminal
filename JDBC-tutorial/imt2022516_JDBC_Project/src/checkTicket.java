import java.sql.*;
import java.util.*;


public class checkTicket {
    void checkYourTicket(Connection conn,int custID){
        Statement stmt2=null;
        try{
            stmt2=conn.createStatement();
        }
        catch(SQLException se){
            se.printStackTrace();
        }   
        
        Scanner scan1=new Scanner(System.in);
        
        
        try{
        String query1=String.format("Select * from TicketBought where CustomerID=%d",custID);

        
        ResultSet resultSet=stmt2.executeQuery(query1);
        LinkedList<Integer> ticketIDs=new LinkedList<>();
        while(resultSet.next()){
            ticketIDs.addLast(Integer.parseInt(resultSet.getString("TicketID")));
        }
        if(ticketIDs.size()==0){
            System.out.println("You haven't purchased any ticket");
            return;
        }
        LinkedList<Integer> trainIDs=new LinkedList<>();
        for(int i=0;i<ticketIDs.size();i++){
            String query2=String.format("Select TrainID from Ticket_Train where TicketID=%d",ticketIDs.get(i));
            
            resultSet=stmt2.executeQuery(query2);

            while(resultSet.next()){
                trainIDs.addLast(Integer.parseInt(resultSet.getString("TrainID")));
            }
        }


        for(int i=0;i<ticketIDs.size();i++){
            int k=i+1;
            System.out.println("Ticket "+k+"\n");
            String query3=String.format("Select C.fname,C.lname,C.sex,Tk.ticketNo,Tr.Train_No,Tr.trainName,Tr.Route,Ti.ArrivalTime,Ti.DepartureTime from Customer C,Ticket Tk,Trains Tr,Timing Ti where C.custID=%d AND Tk.ticketID=%d and Tr.trainID=%d and Ti.trainID=%d",custID,ticketIDs.get(i),trainIDs.get(i),trainIDs.get(i));
            resultSet=stmt2.executeQuery(query3);
            while(resultSet.next()){
                System.out.println("First Name : " + resultSet.getString("fname"));
                System.out.println("Last Name : " + resultSet.getString("lname"));
                System.out.println("Sex : " + resultSet.getString("sex"));
                System.out.println("Ticket Number  : " + resultSet.getString("ticketNo"));
                System.out.println("Train Number : " + resultSet.getString("Train_No"));
                System.out.println("Train Name : " + resultSet.getString("trainName"));
                System.out.println("Train Route : " + resultSet.getString("Route"));
                System.out.println("Arrival Time : " + resultSet.getString("ArrivalTime"));
                System.out.println("Departur Time : " + resultSet.getString("DepartureTime")+"\n");

            }
        }
        resultSet.close();
    }
    catch( Exception e){
        e.printStackTrace();
    }
    try {
        stmt2.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    
        
    }
}
