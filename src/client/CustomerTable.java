package client;

import both.Customers;


import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class CustomerTable extends AbstractTableModel {

    private final String[] columnNames = {"customer_id", "first name", "last name", "address", "city", "country", "postal",
            "phone 1", "phone 2", "email"};

    private final ArrayList<Object[]> Customerdetails = new ArrayList<>();


    public CustomerTable(){

    }

    public void getData(ArrayList<Customers> data){

        Customerdetails.clear(); //clearing from previous

        for(Customers details: data){  //for each row

            int customerid = details.getCustomer_id();
            String firstName = details.getFirstName();
            String lastName = details.getLastName();
            String address = details.getAddress();
            String city = details.getCity();
            String country = details.getCountry();
            String postal = details.getPostal();
            int phone1 = details.getPhone1();
            int phone2 = details.getPhone2();
            String email = details.getEmail();

            Object[] tableData = {customerid, firstName,lastName, address, city, country, postal, phone1, phone2, email}; //populating the details each row at a time into the table

            Customerdetails.add(tableData);


        }
    }


    @Override
    public int getRowCount() {
        return Customerdetails.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Customerdetails.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Customerdetails.get(rowIndex)[columnIndex] = aValue.toString();
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }


}
