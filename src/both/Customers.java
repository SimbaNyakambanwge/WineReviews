package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is responsible for the setting of received data from the sql result set.
 * As well as setting getters and setters for the column different columns found in the Customers table
 * @author Simbarashe Nyakambangwe
 * SID: 8316064
 */

public class Customers implements Serializable {

    private int customer_id;
    private  String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;
    private String postal;
    private int phone1;
    private int phone2;
    private String email;


    public Customers(){
    }



    public Customers(int customer_id, String firstName, String lastName, String address, String city, String country, String postal,
                     int phone1, int phone2, String email ) {
        this.customer_id = customer_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postal = postal;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
    }

    public static Customers CustomerFromResult(ResultSet resultSet)throws SQLException{
        return new both.Customers(
              resultSet.getInt(1),
              resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getInt(8),
                resultSet.getInt(9),
                resultSet.getString(10)

        );

    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public int getPhone1() {
        return phone1;
    }

    public void setPhone1(int phone1) {
        this.phone1 = phone1;
    }

    public int getPhone2() {
        return phone2;
    }

    public void setPhone2(int phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
