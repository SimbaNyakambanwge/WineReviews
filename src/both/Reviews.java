package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Reviews implements Serializable {

    private int review_id;
    private  int customer_id;
    private int wine_id;
    private String customerDescription;
    private int customerRating;
    private String dateAdded;



    public Reviews(){
    }



    public Reviews(int review_id, int customer_id,int wine_id, String customerDescription,
                   int customerRating, String dateAdded) {

        this.review_id = review_id;
        this.customer_id = customer_id;
        this.wine_id = wine_id;
        this.customerDescription = customerDescription;
        this.customerRating = customerRating;
        this.dateAdded = dateAdded;

    }

    public static Reviews ReviewFromResult(ResultSet resultSet)throws SQLException{
        return new both.Reviews(
                resultSet.getInt(1),
               resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getString(4),
                resultSet.getInt(5),
                resultSet.getString(6)
        );

    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getWine_id() {
        return wine_id;
    }

    public void setWine_id(int wine_id) {
        this.wine_id = wine_id;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public int getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(int customerRating) {
        this.customerRating = customerRating;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
