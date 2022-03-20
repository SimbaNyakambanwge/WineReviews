package server;

import both.Customers;
import both.Reviews;
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
 * 06/04/2016
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
    public static Object getWines() {
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

    public static Object getCustomers() {
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

    public static Object getReviews() {
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