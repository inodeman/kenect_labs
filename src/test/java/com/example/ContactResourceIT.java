/*
 * Copyright (c) 2024 Wonderful Kenect Systems
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the {@link ContactResource} in native mode.
 * Extends {@link ContactResourceTest} to reuse the common test scenarios,
 * but runs them in a native Quarkus environment.
 */
@QuarkusIntegrationTest
public class ContactResourceIT extends ContactResourceTest {

    /**
     * Test to verify that fetching all contacts works in native mode.
     * Ensures the HTTP status is 200 and that at least one contact is returned.
     */
    @Test
    public void testGetAllContactsInNativeMode() {
        Response response = RestAssured.given()
                .when().get("/contacts")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.statusCode());
        response.then().body("$.size()", greaterThan(0))
                .body("[0].name", notNullValue());
    }

    /**
     * Test to verify pagination logic in native mode by passing page query parameters.
     * Ensures that the current page is properly returned in the headers.
     */
    @Test
    public void testPaginationInNativeMode() {
        Response response = RestAssured.given()
                .queryParam("page", 1)
                .when().get("/contacts")
                .then()
                .statusCode(200)
                .extract().response();

        int currentPage = Integer.parseInt(response.getHeader("Current-Page"));
        assertEquals(1, currentPage);
    }

    /**
     * Test to check error handling when an invalid token is provided in the request.
     * Ensures that a 401 Unauthorized error is returned with the correct error message.
     */
    @Test
    public void testErrorHandlingInNativeMode() {
        RestAssured.given()
                .header("Authorization", "Bearer invalid_token")
                .when().get("/contacts")
                .then()
                .statusCode(401)
                .body("error", is("Unauthorized"));
    }
}
