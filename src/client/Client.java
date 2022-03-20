package client;

import both.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
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
    private JButton AddCustomersTextfield;
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
                wineTableListener();
            }
        });

        GetTableCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCustomerTable();
                customerTableListener();
            }
        });

        GetTablleReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getReviewTable();
                reviewTableListener();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Winestable1.print();
                } catch (PrinterException printerException) {
                    printerException.printStackTrace();
                }
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

    private void wineTableListener(){

        Winestable1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int eachRecord = Winestable1.rowAtPoint(e.getPoint());

                WineIdTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,0)));
                CountryWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,1)));
                DescriptionWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,2)));
                DesignationWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,3)));
                PointsWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,4)));
                PriceWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,5)));
                ProvinceWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,6)));
                Region1WinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,7)));
                Region2WinestTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,8)));
                TasterNameWinestextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,9)));
                TastertwitterHandleWinesTextfiled.setText(String.valueOf(winetablemodel.getValueAt(eachRecord, 10)));
                TitleWinestextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,11)));
                VarietyWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,12)));
                WineryTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord, 13)));
                YearWinesTextfield.setText(String.valueOf(winetablemodel.getValueAt(eachRecord,14)));

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void customerTableListener(){
       Customertable.addMouseListener(new MouseListener() {

           @Override
           public void mouseClicked(MouseEvent e) {
               int eachRecord = Customertable.rowAtPoint(e.getPoint());

               CustomerIdCustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,0)));
               FirstnameCustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,1)));
               LastnameCustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,2)));
               AddressCustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,3)));
               CityCustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,4)));
               CountryCustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,5)));
               PostalTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,6)));
               Phone1CustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,7)));
               Phone2CustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord, 8)));
               EmailCustomersTextfield.setText(String.valueOf(customertablemodel.getValueAt(eachRecord,9)));
           }

           @Override
           public void mousePressed(MouseEvent e) {

           }

           @Override
           public void mouseReleased(MouseEvent e) {

           }

           @Override
           public void mouseEntered(MouseEvent e) {

           }

           @Override
           public void mouseExited(MouseEvent e) {

           }
       });
    }

    private void reviewTableListener(){
        Reviewstable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int eachRecord = Reviewstable.rowAtPoint(e.getPoint());

                ReviewIdReviwsTextfield.setText(String.valueOf(reviewtablemodel.getValueAt(eachRecord, 0)));
                CustomerIDReviewsTextfield.setText(String.valueOf(reviewtablemodel.getValueAt(eachRecord,1)));
                WineIdReviewsTexfield.setText(String.valueOf(reviewtablemodel.getValueAt(eachRecord,2)));
                CustomerDescripReviewsTextfield.setText(String.valueOf(reviewtablemodel.getValueAt(eachRecord,3)));
                CustomerRatingReviewsTextfield.setText(String.valueOf(reviewtablemodel.getValueAt(eachRecord,4)));
                DateAddedReviewTextfield.setText(String.valueOf(reviewtablemodel.getValueAt(eachRecord,5)));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });




    }


    public static void main(String[] args){
        Client obj = new Client();
    }

}
