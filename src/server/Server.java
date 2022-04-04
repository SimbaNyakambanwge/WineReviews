package server;

import both.Customers;
import both.Reviews;
import both.ReviewsInner;
import both.Wine;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A threaded server can handle multiple client's requests at the same time via
 * multi-threading. This class has the responsibility for connecting new clients
 * and starting a new thread for each new client.
 *
 * @author Simbarashe Nyakambangwe
 * 14/03/2022
 */
public class Server {


    /**
     * Constructor will just initialise the HashMap lookup table on the Server.
     */
    public Server() {

    }

    /**
     * Let's just hard-code a simple HashMap<Keys, Values> to act as a lookup
     * table for the data to send.
     */
    public static synchronized  Object getWines() {
        ArrayList<Wine> record = new ArrayList<>();
        String sql = "SELECT * FROM Wines";

        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){

            ResultSet resultSet = prep.executeQuery();

            while(resultSet.next()){
                record.add(Wine.WineFromResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;

    }

    public static synchronized Object getFilteredReviews(Reviews reviews){
        ArrayList<Reviews> record = new ArrayList<>();
        String sql = "SELECT * FROM CustomerReviews WHERE wine_id=?";

        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){
            prep.setInt(1,reviews.getWine_id());
            ResultSet resultSet = prep.executeQuery();

            while(resultSet.next()){

                record.add(Reviews.ReviewFromResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public static synchronized Object innerWine(Reviews reviews){
        ArrayList<ReviewsInner> record = new ArrayList<>();
       // ArrayList<Reviews> record = new ArrayList<>();
        String sql = "SELECT Wines.title, Wines.points FROM Wines INNER JOIN customerReviews ON customerReviews.wine_id = Wines.wine_id "+
                "WHERE customerReviews.review_id = ? ";

        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){
            prep.setInt(1, reviews.getReview_id());
            ResultSet resultSet = prep.executeQuery();

            while(resultSet.next()){
                //record.add(new Reviews());
                record.add(ReviewsInner.ReviewFromResult(resultSet));

                //record.add(Reviews.ReviewFromResult(resultSet));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public static synchronized Object getCustomers() {
        ArrayList<Customers> record = new ArrayList<>();
        String sql = "SELECT * FROM Customers";

        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){

            ResultSet resultSet = prep.executeQuery();

            while(resultSet.next()){
                record.add(Customers.CustomerFromResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;

    }

    public static synchronized Object getReviews() {
        ArrayList<Reviews> record = new ArrayList<>();
        String sql = "SELECT * FROM CustomerReviews";

        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){

            ResultSet resultSet = prep.executeQuery();

            while(resultSet.next()){
                record.add(Reviews.ReviewFromResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public static void addWinesRow(Wine wine) {

        String sql = "INSERT INTO Wines ( Country, Description, Designation, Points, Price, Province, Region_1, Region_2, Taster_Name, Taster_Twitter_Handle, Title, Variety, Winery, Year)"
               + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try(Connection conn = ConnectionFactory.getConnection();PreparedStatement prep = conn.prepareStatement(sql)){
            prep.setString(1,wine.getCountry());
            prep.setString(2, wine.getDescription());
            prep.setString(3, wine.getDesignation());
            prep.setInt(4, wine.getPoints());
            prep.setInt(5, wine.getPrice());
            prep.setString(6, wine.getProvince() );
            prep.setString(7, wine.getRegion_1());
            prep.setString(8, wine.getRegion_2());
            prep.setString(9,wine.getTasterName());
            prep.setString(10, wine.getTasterTwitterHandle());
            prep.setString(11, wine.getTitle());
            prep.setString(12, wine.getVariety());
            prep.setString(13, wine.getWinery());
            prep.setInt(14, wine.getYear());

            prep.execute();
        }
        catch(SQLException e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }

   }

   public static void addCustomersRow(Customers customers){
        String sql = "INSERT INTO Customers (first_name, last_name, address, city, county, postal, phone1, phone2, email)"+
                "VALUES(?,?,?,?,?,?,?,?,?)";

        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){

            prep.setString(1,customers.getFirstName());
            prep.setString(2,customers.getLastName());
            prep.setString(3, customers.getAddress());
            prep.setString(4, customers.getCity());
            prep.setString(5, customers.getCountry());
            prep.setString(6, customers.getPostal());
            prep.setInt(7, customers.getPhone1());
            prep.setInt(8, customers.getPhone2());
            prep.setString(9, customers.getEmail());

            prep.execute();
        }
        catch(SQLException e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);

        }
   }

   public static void addReviewsRow(Reviews reviews){
        String sql = "INSERT INTO CustomerReviews (customer_id, wine_id, customer_description, customer_rating, date_added)" +
                "VALUES(?,?,?,?,?)";

        try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){

            prep.setInt(1,reviews.getCustomer_id());
            prep.setInt(2,reviews.getWine_id());
            prep.setString(3, reviews.getCustomerDescription());
            prep.setInt(4, reviews.getCustomerRating());
            prep.setString(5,reviews.getDateAdded());

            prep.execute();

        }
        catch(SQLException e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
   }

   public static void removeWinesRow(Wine wine){
        String sql = "DELETE FROM Wines WHERE Wine_id=?";

       try(Connection conn = ConnectionFactory.getConnection();PreparedStatement prep = conn.prepareStatement(sql)){
           prep.setInt(1, wine.getWine_id());

           prep.execute();
       }
       catch(SQLException e){
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
       }

   }

   public static void removeCustomersRow(Customers customers){
        String sql = "DELETE FROM Customers WHERE customer_id=?";

       try(Connection conn = ConnectionFactory.getConnection();PreparedStatement prep = conn.prepareStatement(sql)){
           prep.setInt(1, customers.getCustomer_id());
           prep.execute();
       }
       catch(SQLException e){
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
       }
   }

   public static void removeReviewsRow(Reviews reviews){
        String sql = "DELETE FROM CustomerReviews WHERE review_id=?";

       try(Connection conn = ConnectionFactory.getConnection();PreparedStatement prep = conn.prepareStatement(sql)){
           prep.setInt(1, reviews.getReview_id());
           prep.execute();
       }
       catch(SQLException e){
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
       }
   }

   public static void updateWinesRow(Wine wine){
        String sql = "UPDATE wines SET Country= ?, Description= ?, Designation= ?, Points= ?, Price= ?, Province= ?, Region_1= ?, Region_2= ?, Taster_Name= ?, Taster_Twitter_Handle= ?,Title= ?, Variety= ?, Winery= ?, Year= ? WHERE Wine_id= ?";
       try(Connection conn = ConnectionFactory.getConnection();PreparedStatement prep = conn.prepareStatement(sql)){

           prep.setString(1,wine.getCountry());
           prep.setString(2, wine.getDescription());
           prep.setString(3, wine.getDesignation());
           prep.setInt(4, wine.getPoints());
           prep.setInt(5, wine.getPrice());
           prep.setString(6, wine.getProvince() );
           prep.setString(7, wine.getRegion_1());
           prep.setString(8, wine.getRegion_2());
           prep.setString(9,wine.getTasterName());
           prep.setString(10, wine.getTasterTwitterHandle());
           prep.setString(11, wine.getTitle());
           prep.setString(12, wine.getVariety());
           prep.setString(13, wine.getWinery());
           prep.setInt(14, wine.getYear());
           prep.setInt(15, wine.getWine_id());

           prep.execute();
       }
       catch(SQLException e){
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
       }

   }
   public static void updateCustomersRow(Customers customers){
       String sql = "UPDATE customers SET First_Name=?, Last_Name=?, Address=?, City=?, County=?, Postal=?, Phone1=?, Phone2=?, Email=? WHERE Customer_Id= ?";

       try(Connection conn = ConnectionFactory.getConnection(); PreparedStatement prep = conn.prepareStatement(sql)){

           prep.setString(1, customers.getFirstName());
           prep.setString(2, customers.getLastName());
           prep.setString(3, customers.getAddress());
           prep.setString(4, customers.getCity());
           prep.setString(5, customers.getCountry());
           prep.setString(6, customers.getPostal());
           prep.setInt(7, customers.getPhone1());
           prep.setInt(8, customers.getPhone2());
           prep.setString(9, customers.getEmail());
           prep.setInt(10, customers.getCustomer_id());

           prep.execute();
       }
       catch(SQLException e){
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
       }
   }

    /**
     * Wait until a client connects to the server on a port, then establish the
     * connection via a socket object and create a thread to handle requests.
     */
    private void connectToClients() {
        System.out.println("Server: Server starting.");

        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            while (true) {
                System.out.println("Server: Waiting for connecting client...");

                try {
                    Socket socket = serverSocket.accept();

                    ClientHandler clientHandlerThread = new ClientHandler(socket);
                    Thread connectionThread = new Thread(clientHandlerThread);
                    connectionThread.start();
                } catch (IOException ex) {
                    System.out.println("Server: Could not start connection to a client.");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: Closed down");
        }
    }

    public static void main(String[] args) {
        Server myServer = new Server();
        myServer.connectToClients();
    }

}