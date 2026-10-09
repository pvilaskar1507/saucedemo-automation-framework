package com.qa.framework.tests.api;

import com.qa.framework.base.ApiBaseTest;
import com.qa.framework.utils.ExcelUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

/** API automation with REST Assured against the free JSONPlaceholder service. */
public class PostsApiTests extends ApiBaseTest {

    @DataProvider(name = "postData")
    public Object[][] postData() {
        return ExcelUtils.getSheetData("/testdata/TestData.xlsx", "PostData");
    }

    @Test(description = "GET /posts returns 100 posts")
    public void verifyGetAllPosts() {
        given().spec(requestSpec)
                .when().get("/posts")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .time(lessThan(5000L))
                .body("size()", equalTo(100))
                .body("[0].id", equalTo(1));
    }

    @Test(description = "GET /posts/1 returns the correct post")
    public void verifyGetSinglePost() {
        given().spec(requestSpec)
                .when().get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", not(emptyOrNullString()))
                .body("body", not(emptyOrNullString()));
    }

    @Test(description = "GET /posts/9999 returns 404")
    public void verifyGetInvalidPost() {
        given().spec(requestSpec)
                .when().get("/posts/9999")
                .then()
                .statusCode(404);
    }

    @Test(description = "Query parameter userId=1 filters posts")
    public void verifyFilterPostsByUser() {
        given().spec(requestSpec)
                .queryParam("userId", 1)
                .when().get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("userId", everyItem(equalTo(1)));
    }

    @Test(dataProvider = "postData", description = "POST /posts creates a post (data from Excel)")
    public void verifyCreatePost(String title, String body, String userId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", title);
        payload.put("body", body);
        payload.put("userId", Integer.parseInt(userId));

        given().spec(requestSpec)
                .body(payload)
                .when().post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo(title))
                .body("body", equalTo(body))
                .body("userId", equalTo(Integer.parseInt(userId)))
                .body("id", equalTo(101));
    }

    @Test(description = "PUT /posts/1 updates the whole post")
    public void verifyUpdatePost() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 1);
        payload.put("title", "Updated title");
        payload.put("body", "Updated body");
        payload.put("userId", 1);

        given().spec(requestSpec)
                .body(payload)
                .when().put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated title"))
                .body("body", equalTo("Updated body"));
    }

    @Test(description = "PATCH /posts/1 updates only the title")
    public void verifyPatchPost() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "Patched title");

        given().spec(requestSpec)
                .body(payload)
                .when().patch("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Patched title"))
                .body("id", equalTo(1));
    }

    @Test(description = "DELETE /posts/1 returns 200")
    public void verifyDeletePost() {
        given().spec(requestSpec)
                .when().delete("/posts/1")
                .then()
                .statusCode(200);
    }
}
