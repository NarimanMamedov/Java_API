import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest{
    private String token = "";

    @BeforeEach
    public void setupEach(){
        String authBody = "{ \"username\": \"admin\",  \"password\": \"password123\"}";

        //set base URI for requests
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";

        //create and execute POST request
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(authBody)
                .when()
                .post("/auth")
                .then()
                .extract().response();
        token = response.path("token");
       System.out.println("Token - " + token);

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
