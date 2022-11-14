package automation.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static automation.task1.screenscraper.*;

public class scrapertest {

    String postedBy = "bb59b5de-29c0-44a1-9788-d3f03719a886";

    @Test
    public void createAlert() {
        alert Alert = new alert(2,
                "Heading testing",
                "description testing",
                "https://emotionimports.com/cars/210707182659/",
                "https://emotionimports.com/wp-content/uploads/2021/07/207000386_4107933925920762_728710971201974287_n.jpg",
                postedBy,
                1600);
        addAlert(Alert);
        Assertions.assertEquals(1, Alerts.size());
    }

    @Test
    public void sendAlertPOSTrequestSuccess() throws Exception {
        alert Alert = new alert(2,
                "Heading testing",
                "description testing",
                "https://emotionimports.com/cars/210707182659/",
                "https://emotionimports.com/wp-content/uploads/2021/07/207000386_4107933925920762_728710971201974287_n.jpg",
                postedBy,
                1600);
        sendRequest(Alert);
        //System.out.println(status_code);
        Assertions.assertEquals(201, status_code);
    }


}
