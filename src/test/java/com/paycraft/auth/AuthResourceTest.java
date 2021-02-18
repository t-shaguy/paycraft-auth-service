package com.paycraft.auth;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AuthResourceTest {

    /*
    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/paycraftsystems/auth-service/api/processor/ping")
          .then()
             .statusCode(200)
             .body(is("{\"responseDesc\":\"I am alive and well (Partners)\"}"));
    }*/

}