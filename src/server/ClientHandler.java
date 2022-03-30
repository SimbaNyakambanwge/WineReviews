package server;


import both.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is our thread class with the responsibility of handling client requests
 * once the client has connected. A socket is stored to allow connection.
 * <p>
 * There are two ways to make a thread, one is to extend from the Thread class.
 * The other way is to implement the Runnable interface. Implementing Runnable
 * is better because we do not have to waste our inheritance option.
 *
 * @author Simbarashe Nyakambangwe
 * 14/03/2022
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
//    private final HashMap<String, String> hashMapNames;


    private static int connectionCount = 0;
    private final int connectionNumber;

    /**
     * Constructor just initialises the connection to client.
     *
     * @param socket       the socket to establish the connection to client.
    // * @param  reference to the lookup table for getting email.
     * @throws IOException if an I/O error occurs when creating the input and
     *                     output streams, or if the socket is closed, or socket is not connected.
     */
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        connectionCount++;
        connectionNumber = connectionCount;
        threadSays("Connection " + " "+ connectionNumber+"  " + " to server established.");
    }

    /**
     * The run method is overridden from the Runnable interface. It is called
     * when the Thread is in a 'running' state - usually after thread.start()
     * is called. This method reads client requests and processes names until
     * an exception is thrown.
     */
    @Override
    public void run() {
        try (
            // Read and process names until an exception is thrown.
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
        )
        {
            threadSays("Connection" + connectionCount + " To the server");

            Parcel parcelRead;

            while((parcelRead = (Parcel) objectInputStream.readObject()) != null){

                if(parcelRead.getCommand() == Command.SELECT && parcelRead.getTable() == TableSelection.WINE){
                    ArrayList<Wine> reply;

                    reply = (ArrayList<Wine>) Server.getWines();

                    objectOutputStream.writeObject(reply);
                }
                else if((parcelRead.getCommand() == Command.SELECT && parcelRead.getTable() == TableSelection.CUSTOMERS)){
                    ArrayList<Customers> reply;
                    reply = (ArrayList<Customers>) Server.getCustomers();

                    objectOutputStream.writeObject(reply);
                }
                else if((parcelRead.getCommand() == Command.SELECT && parcelRead.getTable()== TableSelection.REVIEWS)){
                    ArrayList<Reviews> reply;
                    reply = (ArrayList<Reviews>) Server.getReviews();

                    objectOutputStream.writeObject(reply);
                }
               else if(parcelRead.getCommand()== Command.ADD && parcelRead.getTable()==TableSelection.WINE)
               {
                    objectOutputStream.writeObject(new Parcel());
                    Server.addWinesRow((Wine) parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.ADD && parcelRead.getTable()==TableSelection.CUSTOMERS)
               {
                   objectOutputStream.writeObject(new Parcel());
                   Server.addCustomersRow((Customers) parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.ADD && parcelRead.getTable()==TableSelection.REVIEWS)
               {
                  objectOutputStream.writeObject(new Parcel());
                  Server.addReviewsRow((Reviews) parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.REMOVE && parcelRead.getTable()==TableSelection.WINE){
                   objectOutputStream.writeObject(new Parcel());
                   Server.removeWinesRow((Wine)parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.REMOVE && parcelRead.getTable()==TableSelection.CUSTOMERS){
                   objectOutputStream.writeObject(new Parcel());
                   Server.removeCustomersRow((Customers)parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.REMOVE && parcelRead.getTable()==TableSelection.REVIEWS){
                   objectOutputStream.writeObject(new Parcel());
                   Server.removeReviewsRow((Reviews) parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.EDIT && parcelRead.getTable()==TableSelection.WINE){
                      objectOutputStream.writeObject(new Parcel());
                      Server.updateWinesRow((Wine)parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.EDIT && parcelRead.getTable()==TableSelection.CUSTOMERS){
                   objectOutputStream.writeObject(new Parcel());
                   Server.updateCustomersRow((Customers) parcelRead.getObject());
               }
               else if(parcelRead.getCommand()==Command.FILTER && parcelRead.getTable()==TableSelection.REVIEWS){
                    ArrayList<Reviews> reply;
                    reply = (ArrayList<Reviews>) Server.getFilteredReviews();
                    objectOutputStream.writeObject(reply);

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                threadSays("We have lost connection to client " + connectionNumber + ".");
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Private helper method outputs to standard output stream for debugging.
     *
     * @param say the String to write to standard output stream.
     */
    private void threadSays(String say) {
        System.out.println("ClientHandlerThread" + connectionNumber + ": " + say);
    }
}
