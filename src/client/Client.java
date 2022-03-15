package client;

import both.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    private JTabbedPane Wines;
    private JPanel panel1;
    private JButton connectToServerButton;
    private JButton editButton;
    private JButton printButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton getTableButton;
    private JTable Winestable1;
    private JButton GetTableCustomers;
    private JButton DeleteCustomers;
    private JButton EditCustomers;
    private JButton PrintCustomers;
    private JTable Customertable;
    private JTable Reviewstable;
    private JButton GetTablleReviews;
    private JButton AddReviews;
    private JButton EditReviews;
    private JButton DeleteReviews;
    private JButton PrintReviews;
    private JTextField TitleWinestextfield;
    private JTextField WineIdTextfield;
    private JTextField DesignationWinesTextfield;
    private JTextField ProvinceWinesTextfield;
    private JTextField TastertwitterHandleWinesTextfiled;
    private JTextField VarietyWinesTextfield;
    private JTextField WineryTextfield;
    private JTextField YearWinesTextfield;
    private JTextField CustomerIdCustomersTextfield;
    private JTextField FirstnameCustomersTextfield;
    private JTextField LastnameCustomersTextfield;
    private JTextField AddressCustomersTextfield;
    private JTextField CityCustomersTextfield;
    private JTextField CountryCustomersTextfield;
    private JTextField PostalTextfield;
    private JTextField Phone1CustomersTextfield;
    private JTextField Phone2CustomersTextfield;
    private JTextField EmailCustomersTextfield;
    private JTextField ReviewIdReviwsTextfield;
    private JTextField CustomerIDReviewsTextfield;
    private JTextField WineIdReviewsTexfield;
    private JTextField CustomerDescripReviewsTextfield;
    private JTextField CustomerRatingReviewsTextfield;
    private JTextField DateAddedReviewTextfield;
    private JLabel WineIDLabel;
    private JLabel CountryWinesLabel;
    private JLabel DesignationLabel;
    private JLabel DescriptionLabel;
    private JLabel PointsLabel;
    private JLabel PriceLabel;
    private JLabel ProvinceLabel;
    private JLabel Region1Label;
    private JLabel Region2Label;
    private JLabel TasterNameLabel;
    private JLabel TasterTwitterHandleLabel;
    private JLabel TitleLabel;
    private JLabel VarietyLabel;
    private JLabel WineryLabel;
    private JLabel YearLabel;
    private JTextField CountryWinesTextfield;
    private JTextField DescriptionWinesTextfield;
    private JTextField PointsWinesTextfield;
    private JTextField PriceWinesTextfield;
    private JTextField Region1WinesTextfield;
    private JTextField Region2WinestTextfield;
    private JTextField TasterNameWinestextfield;
    private JLabel CustomerIDLabel;
    private JLabel FirstnameLabel;
    private JLabel LastNameLabel;
    private JLabel AddressLabel;
    private JLabel Countrylabel;
    private JLabel PostalLabel;
    private JLabel Phone1Label;
    private JLabel Phone2Label;
    private JLabel ReviewIdLabel;
    private JLabel CustomerIdLabel;
    private JLabel WineIdLabel;
    private JLabel CustomerDescriptionReviewsLabel;
    private JLabel CustomerratingReviewsLabel;
    private JLabel DateaddedLabel;
    private JPanel WinesJPanel;
    private JPanel CustomersJPanel;
    private JPanel ReviewsJPanel;
    private JScrollPane winescroll;


    private WineTable winetablemodel;
    private CustomerTable customertablemodel;
    private ReviewTable reviewtablemodel;

    public Client(){


        final JFrame j = new JFrame("Wine Review");
        winetablemodel = new WineTable();
        Winestable1.setModel(winetablemodel);

        customertablemodel = new CustomerTable();
        Customertable.setModel(customertablemodel);

        reviewtablemodel = new ReviewTable();
        Reviewstable.setModel(reviewtablemodel);


        j.add(panel1);
        j.setSize(400, 400);
        j.setVisible(true);

        connectToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reconnectToServer();
            }
        });

        getTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getWineTable();

            }
        });

        GetTableCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCustomerTable();
            }
        });

        GetTablleReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getReviewTable();
            }
        });

    }

    private void getReviewTable() {
        if(objectOutputStream !=null && objectInputStream !=null) {
            Reviews newReview = new Reviews();

            try {
                objectOutputStream.writeObject(new Parcel(Command.SELECT, TableSelection.REVIEWS, newReview));
            }catch (IOException e){

                System.out.println("IOEXCEPTION ERROR" + e);
            }
            ArrayList<Reviews> reply = new ArrayList<>();

            try{
                reply = (ArrayList<Reviews>) objectInputStream.readObject();
            }
            catch(IOException | ClassNotFoundException e){
                System.out.println("IOEXCEPTION ERROR" + e);
            }

            if(reply !=null){
                try {
                    reviewtablemodel.getData(reply);
                }
                catch(NullPointerException e) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                }


            }
            else {
                System.out.println("please connect to server first");
            }
        }
    }


    private void getCustomerTable() {
        if(objectOutputStream !=null && objectInputStream !=null) {
            Customers newCustomer = new Customers();

            try {
                objectOutputStream.writeObject(new Parcel(Command.SELECT, TableSelection.CUSTOMERS, newCustomer));
            }catch (IOException e){

                System.out.println("IOEXCEPTION ERROR" + e);
            }
            ArrayList<Customers> reply = new ArrayList<>();

            try{
                reply = (ArrayList<Customers>) objectInputStream.readObject();
            }
            catch(IOException | ClassNotFoundException e){
                System.out.println("IOEXCEPTION ERROR" + e);
            }

            if(reply !=null){
                try {
                    customertablemodel.getData(reply);
                }
                catch(NullPointerException e) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                }


            }
            else {
                System.out.println("please connect to server first");
            }
        }
    }

    private void getWineTable(){
        if(objectOutputStream !=null && objectInputStream !=null) {
            Wine newWine = new Wine();

            try {
                objectOutputStream.writeObject(new Parcel(Command.SELECT, TableSelection.WINE, newWine));
            }catch (IOException e){

                System.out.println("IOEXCEPTION ERROR" + e);
            }
            ArrayList<Wine> reply = new ArrayList<>();

            try{
                reply = (ArrayList<Wine>) objectInputStream.readObject();
            }
            catch(IOException | ClassNotFoundException e){
                System.out.println("IOEXCEPTION ERROR" + e);
            }

            if(reply !=null){
                try {
                    winetablemodel.getData(reply);

                }
                catch(NullPointerException e) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                }


            }
            else {
                System.out.println("please connect to server first");
            }
        }

    }



    private void closeConnection(){
        if(socket != null){
            System.out.println("Status: closing connection");
            try{
                socket.close();
            }catch (IOException ex){
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                socket = null;
            }
        }
    }

    private void reconnectToServer(){
        closeConnection();
        System.out.println("Status: Attempting to connect");
        try {

            socket = new Socket("127.0.0.1", 5000);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Status: Connected to server");


        }catch(IOException e){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);

        }


    }



    public static void main(String[] args){
        Client obj = new Client();
    }

}
