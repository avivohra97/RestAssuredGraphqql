import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.hamcrest.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GraphqlTest {

    @Test
    public void testGetRequest() {
        // Set the base URI, using JSONPlaceholder as an example
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        // Send a GET request and save the response
        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();
        // Print the JSON content of the response
        System.out.println("Response JSON: " + response.asString()); // Verify that the status code is 200.
        // Validate that the status code is 200
        response.then().statusCode(200); // validate that the response has a status code of 200.
        // Validate a specific field value in the response
        response.then().body("userId", equalTo(1));
        response.then().body("id", equalTo(1));
        response.then().body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
        response.then().body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    public void testGraphql(){
        // Set the base URI, using JSONPlaceholder as an example
        RestAssured.baseURI = "https://rickandmortyapi.graphcdn.app/";
        // Send a GET request and save the response
        String query = "{\n" +
                "    \"query\": \"{\\n  characters(page: 2, filter: { name: \\\"rick\\\" }) {\\n    info {\\n      count\\n    }\\n    results {\\n      name\\n    }\\n  }\\n}\"\n" +
                "}";
        Response response = given().log().all()
                .contentType("application/json")
                .header(new Header("Accept","application/json"))
                .when().log().all()
                .body(query)
                .post()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();


        // Print the JSON content of the response
        System.out.println("Response JSON: " + response.asString()); // Verify that the status code is 200.
        // Validate that the status code is 200
        response.then().statusCode(200); // validate that the response has a status code of 200.

    }


}
