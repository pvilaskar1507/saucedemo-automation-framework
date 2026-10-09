package com.qa.framework.tests.api;

import com.qa.framework.base.ApiBaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

public class UsersApiTests extends ApiBaseTest {

    @Test(description = "GET /users returns 10 users")
    public void verifyGetAllUsers() {
        given().spec(requestSpec)
                .when().get("/users")
                .then()
                .statusCode(200)
                .body("size()", equalTo(10));
    }

    @Test(description = "GET /users/1 returns the expected user details")
    public void verifyGetSingleUser() {
        given().spec(requestSpec)
                .when().get("/users/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", containsString("@"))
                .body("address.city", equalTo("Gwenborough"));
    }

    @Test(description = "GET /users/1/posts returns posts that belong to user 1")
    public void verifyUserPosts() {
        given().spec(requestSpec)
                .when().get("/users/1/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("[0].userId", equalTo(1));
    }

    @Test(description = "GET /users/9999 returns 404")
    public void verifyInvalidUser() {
        given().spec(requestSpec)
                .when().get("/users/9999")
                .then()
                .statusCode(404);
    }
}
