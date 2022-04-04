package client;

import both.Wine;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Helper class formed to ensure the arraylist of wine objects can be populated into an abstract table on the client side
 * In turn into the J table on the GUI
 */
public class WineTable extends AbstractTableModel {

    private final String[] columnNames = {"wine_id", "country", "description", " designation", "points", "price", "province",
            "region_1", "region_2", "tasterName", " taster_twitter_handle", "title", "variety", "winery", "year"};

    private final ArrayList<Object[]> wineDetails = new ArrayList<>();


    public WineTable(){
    }

    public void getData(ArrayList<Wine> data){

        wineDetails.clear(); //clearing from previous

        for(Wine details: data){  //for each row

            int wine_id = details.getWine_id();
            String country = details.getCountry();
            String description = details.getDescription();
            String designation = details.getDescription();
            int points = details.getPoints();
            int price = details.getPrice();
            String province= details.getProvince();
            String region1 = details.getRegion_1();
            String region2 = details.getRegion_2();
            String tasterName = details.getTasterName();
            String tasterTwitter= details.getTasterTwitterHandle();
            String title = details.getTitle();
            String variety = details.getVariety();
            String winery = details.getWinery();
            int year = details.getYear();

            Object[] tableData = {wine_id, country,description, designation, points, price, province, region1, region2, tasterName,
            tasterTwitter, title, variety, winery, year}; //populating the details each row at a time into the table

            wineDetails.add(tableData);


        }
    }


    @Override
    public int getRowCount() {
        return wineDetails.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return wineDetails.get(rowIndex)[columnIndex];
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
        wineDetails.get(rowIndex)[columnIndex] = aValue.toString();
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }

}
