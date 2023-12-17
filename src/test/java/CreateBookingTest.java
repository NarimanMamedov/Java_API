import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Bookigdates;
import models.UserBookingInfo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateBookingTest extends BaseTest {

    private static final Logger LOG = Logger.getLogger(String.valueOf(VerificationFirstBookingTest.class));

    @Test
    public void createBookingTest() {
        LOG.info("Create Booking by method Post");
        UserBookingInfo userBookingInfoNew = new UserBookingInfo();
        userBookingInfoNew.setFirstname("Ivan");
        userBookingInfoNew.setLastname("Mazepa");
        userBookingInfoNew.setTotalprice(555);
        userBookingInfoNew.setDepositpaid(true);
        Bookigdates bookigdatesNew = new Bookigdates();
        bookigdatesNew.setCheckin("2023-12-12");
        bookigdatesNew.setCheckout("2023-12-24");
        userBookingInfoNew.setBookingdates(bookigdatesNew);
        userBookingInfoNew.setAdditionalneeds(" Breakfast");

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

        // ------ Assertions
        Assertions.assertEquals(200, response.statusCode());

        //https://restful-booker.herokuapp.com/booking/1
        // ____  DELETE  test data after yourself
        Response responseDelete = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Cookie", getToken())
                .body("")
                .when()
                .delete(id)
                .then()
                .extract().response();
        LOG.info("Booking delete response status " + responseDelete.statusCode());


    }
}
