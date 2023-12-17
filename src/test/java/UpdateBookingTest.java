import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Bookigdates;
import models.UserBookingInfo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateBookingTest extends BaseTest{
    private static final Logger LOG = Logger.getLogger(UpdateBookingTest.class);

    @Test
    public void createBookingTest() {
        LOG.info("Create Booking by method Post");
        UserBookingInfo userBookingInfoNew = new UserBookingInfo();
        userBookingInfoNew.setFirstname("Petro");
        userBookingInfoNew.setLastname("Konashevych");
        userBookingInfoNew.setTotalprice(555);
        userBookingInfoNew.setDepositpaid(true);
        Bookigdates bookigdatesNew = new Bookigdates();
        bookigdatesNew.setCheckin("2023-12-12");
        bookigdatesNew.setCheckout("2023-12-24");
        userBookingInfoNew.setBookingdates(bookigdatesNew);
        userBookingInfoNew.setAdditionalneeds("Breakfast");

        //https://restful-booker.herokuapp.com/booking/

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Cookie", getToken())
                .body(new Gson().toJson(userBookingInfoNew))
                .when()
                .post("/booking")
                .then()
                .extract().response();
        LOG.info("Booking create request " + new Gson().toJson(userBookingInfoNew));
        LOG.info("Booking create response " + response.body().print());

        String id = "/booking/" + response.body().path("bookingid");

        //https://restful-booker.herokuapp.com/booking/:id
        userBookingInfoNew.setLastname("Sagaydachniy");
        Response response_bookingUpdate = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json" )
                .header("Cookie", getToken())
                .body(new Gson().toJson(userBookingInfoNew))
                .when()
                .put(id)
                .then()
                .extract().response();
        LOG.info("Booking create response " + response_bookingUpdate.body().print());

        // ------ Assertions
        Assertions.assertEquals(200, response_bookingUpdate.statusCode());

    }
}
