import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.UserBookingInfo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class VerificationFirstBookingTest extends BaseTest {
    private static final Logger LOG = Logger.getLogger(String.valueOf(VerificationFirstBookingTest.class));

    @Test
    public void apiTest() {
        System.out.println("Auth token: " + getToken());
        //https://restful-booker.herokuapp.com/booking/
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Cookie", getToken())
                .when()
                .get("/booking")
                .then()
                .extract().response();
        LOG.info("Booking response " + response.body().print());
        String id = "/booking/" + ((ArrayList<Integer>)response.body().path("bookingid")).get(0);

        //https://restful-booker.herokuapp.com/booking/:id
        Response response_booking = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Cookie", getToken())
                .when()
                .get( id)
                .then()
                .extract().response();
        //System.out.println(response_booking.body().prettyPrint());
        UserBookingInfo userBookingInfo = response_booking.body().as(UserBookingInfo.class);
        System.out.println(userBookingInfo.toString());

        // ------ Assertions
        Assertions.assertEquals(200, response_booking.statusCode());
        Assertions.assertNotNull(userBookingInfo.getFirstname());
        Assertions.assertTrue(userBookingInfo.getTotalprice() > 0);

        //String actualName = response_booking.body().path("firstname");
        //Assertions.assertEquals("Bob", response_booking.body().path("firstname"));
        //Assertions.assertEquals(111, (int) response_booking.body().path("totalprice"));

    }
}
