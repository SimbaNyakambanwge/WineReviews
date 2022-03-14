package server;


import both.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
//
//    private final PrintWriter printWriter;
//    private final BufferedReader bufferedReader;

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
       // this.hashMapNames = hashMapNames;

       // printWriter = new PrintWriter(socket.getOutputStream(), true);
       // bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        connectionCount++;
        connectionNumber = connectionCount;
        threadSays("Connection " + connectionNumber + " established.");
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
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        )
        {
            threadSays("Connection" + connectionCount + " To the server");

            Parcel parcelRead;

            while((parcelRead = (Parcel) objectInputStream.readObject()) != null){

                if(parcelRead.getCommand() == Command.SELECT && parcelRead.getTable() == Table.WINE){
                    ArrayList<Wine> reply;

                    reply = (ArrayList<Wine>) ThreadedServer.getWines();

                    objectOutputStream.writeObject(reply);
                }
                else if((parcelRead.getCommand() == Command.SELECT && parcelRead.getTable() == Table.CUSTOMERS)){
                    ArrayList<Customers> reply;
                    reply = (ArrayList<Customers>) ThreadedServer.getCustomers();

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