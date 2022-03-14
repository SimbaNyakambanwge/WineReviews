package client;

import both.Customers;
import both.Reviews;
import both.Wine;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class ReviewTable extends AbstractTableModel {

    private final String[] columnNames = {"review_id", "customer_id", "wine_id", "customer description",
            "customer rating", "date added"};

    private final ArrayList<Object[]> Reviewdetails = new ArrayList<>();


    public ReviewTable(){

    }

    public void getData(ArrayList<Reviews> data){

        Reviewdetails.clear(); //clearing from previous

        for(Reviews details: data){  //for each row

            int review_id = details.getReview_id();
            int customer_id = details.getCustomer_id();
            int wine_id = details.getWine_id();
            String customerDescription = details.getCustomerDescription();
            int customerRating = details.getCustomerRating();
            String dateAdded = details.getDateAdded();


            Object[] tableData = {review_id, customer_id,wine_id, customerDescription, customerRating, dateAdded}; //populating the details each row at a time into the table

            Reviewdetails.add(tableData);


        }
    }


    @Override
    public int getRowCount() {
        return Reviewdetails.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Reviewdetails.get(rowIndex)[columnIndex];
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
        Reviewdetails.get(rowIndex)[columnIndex] = aValue.toString();
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }

    public void removeRow (int rowIndex) {
        Reviewdetails.remove(rowIndex);
    }
}
