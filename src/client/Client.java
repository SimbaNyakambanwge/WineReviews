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

    private JPanel panel1;
    private JButton connectToServerButton;
    private JButton editButton;
    private JButton printButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton getTableButton;
    private JTable winesTable1;
    private JButton GetTableCustomers;
    private JButton DeleteCustomers;
    private JButton EditCustomers;
    private JButton PrintCustomers;
    private JTable customerTable;
    private JTable reviewsTable;
    private JButton getTableReviews;
    private JButton AddReviews;
    private JButton filterReviews;
    private JButton DeleteReviews;
    private JButton PrintReviews;
    private JTextField titleWinesTextField;
    private JTextField wineIdTextField;
    private JTextField designationWinesTextField;
    private JTextField provinceWinesTextField;
    private JTextField tasterTwitterHandleWinesTextField;
    private JTextField varietyWinesTextField;
    private JTextField wineryTextField;
    private JTextField yearWinesTextField;
    private JTextField customerIdCustomersTextField;
    private JTextField firstNameCustomersTextField;
    private JTextField lastNameCustomersTextField;
    private JTextField addressCustomersTextField;
    private JTextField cityCustomersTextField;
    private JTextField countyCustomersTextField;
    private JTextField postalTextField;
    private JTextField phone1CustomersTextField;
    private JTextField phone2CustomersTextField;
    private JTextField emailCustomersTextField;
    private JTextField reviewIdReviewsTextField;
    private JTextField customerIdReviewsTextField;
    private JTextField wineIdReviewsTextField;
    private JTextField customerRatingReviewsTextField;
    private JTextField dateAddedReviewTextField;
    private JTextField countryWinesTextField;
    private JTextField descriptionWinesTextField;
    private JTextField pointsWinesTextField;
    private JTextField priceWinesTextField;
    private JTextField region1WinesTextField;
    private JTextField region2WinesTextField;
    private JTextField tasterNameWinesTextField;
    private JPanel CustomersJPanel;
    private JPanel ReviewsJPanel;
    private JButton addCustomersButton;
    private JTextArea reviewsDescriptionTextArea;
    private JScrollPane wineScroll;
    //endregion

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    private WineTable wineTableModel;
    private CustomerTable customerTableModel;
    private ReviewTable reviewTableModel;


    public Client(){
        initialSetup();
        buttonActionListeners();
    }

    private void initialSetup(){
        final JFrame j = new JFrame("Wine Review");
        wineTableModel = new WineTable();
        winesTable1.setModel(wineTableModel);

        customerTableModel = new CustomerTable();
        customerTable.setModel(customerTableModel);

        reviewTableModel = new ReviewTable();
        reviewsTable.setModel(reviewTableModel);

        j.add(panel1);
        j.setSize(400, 400);
        j.setVisible(true);
    }

    private void buttonActionListeners(){
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

        getTableReviews.addActionListener(new ActionListener() {
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
                    winesTable1.print();
                } catch (PrinterException printerException) {
                    printerException.printStackTrace();
                }
            }
        });

        PrintCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    customerTable.print();
                }catch(PrinterException printerException){
                    printerException.printStackTrace();
                }
            }
        });

        PrintReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    reviewsTable.print();
                }catch(PrinterException printerException){
                    printerException.printStackTrace();
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWineRow();
                getWineTable();
            }
        });
        addCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomerRow();
                getCustomerTable();
            }
        });

        AddReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReviewRow();
                getReviewTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeWineRow();
                getReviewTable();
            }
        });

        DeleteCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCustomerRow();
                getCustomerTable();
            }
        });

        DeleteReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeReviewRow();
                getReviewTable();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateWineRow();
                getWineTable();
            }
        });

        EditCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomerRow();
                getCustomerTable();
            }
        });
        filterReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterWineId();
                wineTableListener();

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
                    reviewTableModel.getData(reply);
                    reviewsTable.setAutoCreateRowSorter(true);
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
                    customerTableModel.getData(reply);
                    customerTable.setAutoCreateColumnsFromModel(true);
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
                    wineTableModel.getData(reply);
                    winesTable1.setAutoCreateRowSorter(true);
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

    private void filterWineId(){
        if(objectOutputStream !=null && objectInputStream !=null) {

            Reviews newReviews = new Reviews();
            newReviews.setWine_id(Integer.parseInt(wineIdReviewsTextField.getText()));
            try {
                objectOutputStream.writeObject(new Parcel(Command.FILTER, TableSelection.REVIEWS, newReviews));
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
                    reviewTableModel.getData(reply);

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

        winesTable1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int eachRecord = winesTable1.rowAtPoint(e.getPoint());

                wineIdTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,0)));
                countryWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,1)));
                descriptionWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,2)));
                designationWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,3)));
                pointsWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,4)));
                priceWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,5)));
                provinceWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,6)));
                region1WinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,7)));
                region2WinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,8)));
                tasterNameWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,9)));
                tasterTwitterHandleWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord, 10)));
                titleWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,11)));
                varietyWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,12)));
                wineryTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord, 13)));
                yearWinesTextField.setText(String.valueOf(wineTableModel.getValueAt(eachRecord,14)));

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
       customerTable.addMouseListener(new MouseListener() {

           @Override
           public void mouseClicked(MouseEvent e) {
               int eachRecord = customerTable.rowAtPoint(e.getPoint());

               customerIdCustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,0)));
               firstNameCustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,1)));
               lastNameCustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,2)));
               addressCustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,3)));
               cityCustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,4)));
               countyCustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,5)));
               postalTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,6)));
               phone1CustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,7)));
               phone2CustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord, 8)));
               emailCustomersTextField.setText(String.valueOf(customerTableModel.getValueAt(eachRecord,9)));
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
        reviewsTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int eachRecord = reviewsTable.rowAtPoint(e.getPoint());

                reviewIdReviewsTextField.setText(String.valueOf(reviewTableModel.getValueAt(eachRecord, 0)));
                customerIdReviewsTextField.setText(String.valueOf(reviewTableModel.getValueAt(eachRecord,1)));
                wineIdReviewsTextField.setText(String.valueOf(reviewTableModel.getValueAt(eachRecord,2)));
                reviewsDescriptionTextArea.setText(String.valueOf(reviewTableModel.getValueAt(eachRecord,3)));
                customerRatingReviewsTextField.setText(String.valueOf(reviewTableModel.getValueAt(eachRecord,4)));
                dateAddedReviewTextField.setText(String.valueOf(reviewTableModel.getValueAt(eachRecord,5)));
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

    private void addWineRow() {
        Wine newWine = new Wine();
            //  newWine.setWine_id(Integer.parseInt(wineIdTextField.getText()));
            newWine.setCountry(countryWinesTextField.getText());
            newWine.setDescription(descriptionWinesTextField.getText());
            newWine.setDesignation(designationWinesTextField.getText());
            newWine.setPoints(Integer.parseInt(pointsWinesTextField.getText()));
            newWine.setPrice(Integer.parseInt(pointsWinesTextField.getText()));
            newWine.setProvince((provinceWinesTextField.getText()));
            newWine.setRegion_1(region1WinesTextField.getText());
            newWine.setRegion_2(region2WinesTextField.getText());
            newWine.setTasterName(tasterNameWinesTextField.getText());
            newWine.setTasterTwitterHandle(tasterTwitterHandleWinesTextField.getText());
            newWine.setTitle(titleWinesTextField.getText());
            newWine.setVariety(varietyWinesTextField.getText());
            newWine.setWinery(wineryTextField.getText());
            newWine.setYear(Integer.parseInt(yearWinesTextField.getText()));

            if(objectOutputStream != null && objectInputStream != null){
                //send data
                try{
                    objectOutputStream.writeObject(new Parcel(Command.ADD,TableSelection.WINE, newWine));
                }
                catch(IOException e){
                    System.out.println("IO Exception" + e);
                }
               //receive reply
                Parcel reply =null;
                System.out.println("Waiting for reply from server");
                try{
                    reply = (Parcel) objectInputStream.readObject();
                    System.out.println("Reply received");
                }
                catch(IOException e){
                    System.out.println("IO Exception" + e);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (reply != null) {

                    try {

                        System.out.println(reply);

                    } catch (NullPointerException ex) {

                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                System.out.println("You must connect to the server first!!");

            }


    }
    private void addCustomerRow(){
       Customers newCustomers = new Customers();

       newCustomers.setFirstName(firstNameCustomersTextField.getText());
       newCustomers.setLastName(lastNameCustomersTextField.getText());
       newCustomers.setAddress(addressCustomersTextField.getText());
       newCustomers.setCity(cityCustomersTextField.getText());
       newCustomers.setCountry(countyCustomersTextField.getText());
       newCustomers.setPostal(postalTextField.getText());
       newCustomers.setPhone1(Integer.parseInt(phone1CustomersTextField.getText()));
       newCustomers.setPhone2(Integer.parseInt(phone2CustomersTextField.getText()));
       newCustomers.setEmail(emailCustomersTextField.getText());

        if(objectOutputStream != null && objectInputStream != null){
            //send data
            try{
                objectOutputStream.writeObject(new Parcel(Command.ADD,TableSelection.CUSTOMERS, newCustomers));
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            }
            //receive reply
            Parcel reply =null;
            System.out.println("Waiting for reply from server");
            try{
                reply = (Parcel) objectInputStream.readObject();
                System.out.println("Reply received");
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (reply != null) {

                try {

                    System.out.println(reply);

                } catch (NullPointerException ex) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("You must connect to the server first!!");

        }

    }
    private void addReviewRow(){
       Reviews newReviews = new Reviews();

       newReviews.setCustomer_id(Integer.parseInt(customerIdReviewsTextField.getText()));
       newReviews.setWine_id(Integer.parseInt(wineIdReviewsTextField.getText()));
       newReviews.setCustomerDescription(reviewsDescriptionTextArea.getText());
       newReviews.setCustomerRating(Integer.parseInt(customerRatingReviewsTextField.getText()));
       newReviews.setDateAdded(dateAddedReviewTextField.getText());

        if(objectOutputStream != null && objectInputStream != null){
            //send data
            try{
                objectOutputStream.writeObject(new Parcel(Command.ADD,TableSelection.REVIEWS, newReviews));
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            }
            //receive reply
            Parcel reply =null;
            System.out.println("Waiting for reply from server");
            try{
                reply = (Parcel) objectInputStream.readObject();
                System.out.println("Reply received");
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (reply != null) {

                try {

                    System.out.println(reply);

                } catch (NullPointerException ex) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("You must connect to the server first!!");

        }
    }

    private void removeWineRow(){
        Wine newWine = new Wine();

        newWine.setWine_id(Integer.parseInt(wineIdTextField.getText()));

        if(objectOutputStream != null && objectInputStream != null){
            //send data
            try{
                objectOutputStream.writeObject(new Parcel(Command.REMOVE,TableSelection.WINE, newWine));
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            }
            //receive reply
            Parcel reply =null;
            System.out.println("Waiting for reply from server");
            try{
                reply = (Parcel) objectInputStream.readObject();
                System.out.println("Reply received");
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (reply != null) {

                try {

                    System.out.println(reply);

                } catch (NullPointerException ex) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("You must connect to the server first!!");

        }
    }
    private void removeCustomerRow(){
        Customers newCustomers = new Customers();

        newCustomers.setCustomer_id(Integer.parseInt(customerIdCustomersTextField.getText()));


        if(objectOutputStream != null && objectInputStream != null){
            //send data
            try{
                objectOutputStream.writeObject(new Parcel(Command.REMOVE,TableSelection.CUSTOMERS, newCustomers));
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            }
            //receive reply
            Parcel reply =null;
            System.out.println("Waiting for reply from server");
            try{
                reply = (Parcel) objectInputStream.readObject();
                System.out.println("Reply received");
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (reply != null) {

                try {

                    System.out.println(reply);

                } catch (NullPointerException ex) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("You must connect to the server first!!");

        }

    }
    private void removeReviewRow(){
        Reviews newReviews = new Reviews();

        newReviews.setReview_id(Integer.parseInt(reviewIdReviewsTextField.getText()));

        if(objectOutputStream != null && objectInputStream != null){
            //send data
            try{
                objectOutputStream.writeObject(new Parcel(Command.REMOVE,TableSelection.REVIEWS, newReviews));
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            }
            //receive reply
            Parcel reply =null;
            System.out.println("Waiting for reply from server");
            try{
                reply = (Parcel) objectInputStream.readObject();
                System.out.println("Reply received");
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (reply != null) {

                try {

                    System.out.println(reply);

                } catch (NullPointerException ex) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("You must connect to the server first!!");

        }
    }

    private void updateWineRow(){
        Wine newWine = new Wine();

        newWine.setWine_id(Integer.parseInt(wineIdTextField.getText()));

        newWine.setCountry(countryWinesTextField.getText());
        newWine.setDescription(descriptionWinesTextField.getText());
        newWine.setDesignation(designationWinesTextField.getText());
        newWine.setPoints(Integer.parseInt(pointsWinesTextField.getText()));
        newWine.setPrice(Integer.parseInt(priceWinesTextField.getText()));
        newWine.setProvince((provinceWinesTextField.getText()));
        newWine.setRegion_1(region1WinesTextField.getText());
        newWine.setRegion_2(region2WinesTextField.getText());
        newWine.setTasterName(tasterNameWinesTextField.getText());
        newWine.setTasterTwitterHandle(tasterTwitterHandleWinesTextField.getText());
        newWine.setTitle(titleWinesTextField.getText());
        newWine.setVariety(varietyWinesTextField.getText());
        newWine.setYear(Integer.parseInt(yearWinesTextField.getText()));

        if(objectOutputStream != null && objectInputStream != null){
            //send data
            try{
                objectOutputStream.writeObject(new Parcel(Command.EDIT,TableSelection.WINE, newWine));
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            }
            //receive reply
            Parcel reply =null;
            System.out.println("Waiting for reply from server");
            try{
                reply = (Parcel) objectInputStream.readObject();
                System.out.println("Reply received");
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (reply != null) {

                try {

                    System.out.println(reply);

                } catch (NullPointerException ex) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("You must connect to the server first!!");

        }

    }
    private void updateCustomerRow(){
        Customers newCustomers = new Customers();

        newCustomers.setCustomer_id(Integer.parseInt(customerIdCustomersTextField.getText()));
        newCustomers.setFirstName(firstNameCustomersTextField.getText());
        newCustomers.setLastName(lastNameCustomersTextField.getText());
        newCustomers.setAddress(addressCustomersTextField.getText());
        newCustomers.setCity(cityCustomersTextField.getText());
        newCustomers.setCountry(countyCustomersTextField.getText());
        newCustomers.setPhone1(Integer.parseInt(phone1CustomersTextField.getText()));
        newCustomers.setPostal(postalTextField.getText());
        newCustomers.setPhone2(Integer.parseInt(phone2CustomersTextField.getText()));
        newCustomers.setEmail(emailCustomersTextField.getText());

        if(objectOutputStream != null && objectInputStream != null){
            //send data to the server
            try{
                objectOutputStream.writeObject(new Parcel(Command.EDIT,TableSelection.CUSTOMERS, newCustomers));
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            }
            //receive reply
            Parcel reply =null;
            System.out.println("Waiting for reply from server");
            try{
                reply = (Parcel) objectInputStream.readObject();
                System.out.println("Reply received");
            }
            catch(IOException e){
                System.out.println("IO Exception" + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (reply != null) {

                try {

                    System.out.println(reply);

                } catch (NullPointerException ex) {

                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("You must connect to the server first!!");

        }
    }



    public static void main(String[] args){
        Client object = new Client();
    }

}
