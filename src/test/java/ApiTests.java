import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @Test
    public void testGet() {
        given()
                .baseUri("https://postman-echo.com")
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get")
                .then()
                .statusCode(200) // соответствует: pm.response.to.have.status(200)
                .contentType(ContentType.JSON)
                .body("args.foo1", equalTo("bar1")) // соответствует: jsonBody('args.foo1', 'bar1')
                .body("args.foo2", equalTo("bar2"));
    }
    @Test
    public void testPostRawText() {
        given()
                .baseUri("https://postman-echo.com")
                .header("Content-Type", "text/plain")
                .body("{\n\"test\": \"value\"\n}")
        .when()
                .post("/post")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", equalTo("{\n\"test\": \"value\"\n}"));
    }
    @Test
    public void testPostFormData() {
        given()
                .baseUri("https://postman-echo.com")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .cookie("sails.sid");
    }
    @Test
    public void testPut() {
        given()
                .baseUri("https://postman-echo.com")
                .header("Content-Type", "text/plain")
                .body("This is expected to be sent back as part of response body.")
                .when()
                .put("/put")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .cookie("sails.sid")
                .body("data", equalTo("This is expected to be sent back as part of response body."));
    }
    @Test
    public void testPatch() {
        given()
                .baseUri("https://postman-echo.com")
                .header("Content-Type", "text/plain")
                .body("This is expected to be sent back as part of response body.")
                .when()
                .patch("/patch")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .cookie("sails.sid")
                .body("data", equalTo("This is expected to be sent back as part of response body."));
    }
    @Test
    public void testDelete() {
        given()
                .baseUri("https://postman-echo.com")
                .header("Content-Type", "text/plain")
                .body("This is expected to be sent back as part of response body.")
                .when()
                .delete("/delete")
                .then()
                .statusCode(200)
                .log().all()
                .contentType(ContentType.JSON)
                .cookie("sails.sid")
                .body("data", equalTo("This is expected to be sent back as part of response body."));
    }
}
