package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ReviewsInner implements Serializable {


    private String title;
    private String description;




    public ReviewsInner(){
    }



    public ReviewsInner( String title, String description) {


        this.title = title;
        this.description = description;


    }

    public static ReviewsInner ReviewFromResult(ResultSet resultSet)throws SQLException{
        return new both.ReviewsInner(
               resultSet.getString(1),
                resultSet.getString(2)

        );

    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
