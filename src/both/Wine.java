package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class is responsible for the setting of received data from the sql result set.
 * As well as setting getters and setters for the column different columns found in the Wine table
 */
public class Wine implements Serializable {

    private int wine_id;
    private  String country;
    private String description;
    private String designation;
    private int points;
    private int price;
    private String province;
    private String region_1;
    private String region_2;
    private String tasterName;
    private String tasterTwitterHandle;
    private String title;
    private String variety;
    private String winery;
    private int year;

   public Wine(){
  }



    public Wine(int wine_id, String country, String description, String designation, int points, int price, String province, String region_1, String region_2, String tasterName, String tasterTwitterHandle, String title, String variety, String winery, int year) {
        this.wine_id = wine_id;
        this.country = country;
        this.description = description;
        this.designation = designation;
        this.points = points;
        this.price = price;
        this.province = province;
        this.region_1 = region_1;
        this.region_2 = region_2;
        this.tasterName = tasterName;
        this.tasterTwitterHandle = tasterTwitterHandle;
        this.title = title;
        this.variety = variety;
        this.winery = winery;
        this.year = year;
    }

    public Wine(String title,int points){
        this.title = title;
        this.points = points;

    }

    public static Wine WineFromResult(ResultSet resultSet)throws SQLException{
       return new both.Wine(
               resultSet.getInt(1),
               resultSet.getString(2),
               resultSet.getString(3),
               resultSet.getString(4),
               resultSet.getInt(5),
               resultSet.getInt(6),
               resultSet.getString(7),
               resultSet.getString(8),
               resultSet.getString(9),
               resultSet.getString(10),
               resultSet.getString(11),
               resultSet.getString(12),
               resultSet.getString(13),
               resultSet.getString(14),
               resultSet.getInt(15)
       );

    }

    public int getWine_id() {
        return wine_id;
    }

    public void setWine_id(int wine_id) {
        this.wine_id = wine_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion_1() {
        return region_1;
    }

    public void setRegion_1(String region_1) {
        this.region_1 = region_1;
    }

    public String getRegion_2() {
        return region_2;
    }

    public void setRegion_2(String region_2) {
        this.region_2 = region_2;
    }

    public String getTasterName() {
        return tasterName;
    }

    public void setTasterName(String tasterName) {
        this.tasterName = tasterName;
    }

    public String getTasterTwitterHandle() {
        return tasterTwitterHandle;
    }

    public void setTasterTwitterHandle(String tasterTwitterHandle) {
        this.tasterTwitterHandle = tasterTwitterHandle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getWinery() {
        return winery;
    }

    public void setWinery(String winery) {
        this.winery = winery;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
