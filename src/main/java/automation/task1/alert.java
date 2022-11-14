package automation.task1;

import java.util.ArrayList;

public class  alert {
    //class called alert not Alert as there is a selenium object already
    private int alertType;
    private String heading;
    private String description;
    private String url;

    private String imageUrl;
    private String postedBy;
    private int priceInCents;

    /*
    function: the main constructor - creates an alert
    params: all the properties of an alert
     */
    public alert(int alertType, String heading, String description, String url, String imageUrl, String postedBy, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.postedBy = postedBy;
        this.priceInCents = priceInCents;
    }
}
