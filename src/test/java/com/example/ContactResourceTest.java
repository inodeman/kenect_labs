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

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.registerParser;
import static io.restassured.parsing.Parser.JSON;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Unit tests for the ContactResource class, which provides the REST API for fetching contacts.
 * Uses QuarkusTest framework to enable testing in a Quarkus environment.
 */
@QuarkusTest
public class ContactResourceTest {

    @InjectMock
    ContactService contactService;

    /**
     * Test to ensure that the /contacts endpoint returns a list of contacts correctly.
     * It mocks the ContactService to return a list with a single contact and validates the response.
     */
    @Test
    public void testGetAllContactsEndpoint() {
        // Set up a mock contact list to simulate what the service will return
        List<Contact> mockContacts = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setName("Jaime Enriquez");
        contact1.setEmail("jaime.a.enriquez@gmail.com");
        contact1.setSource("KENNECT_LABS");
        contact1.setCreated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));
        contact1.setUpdated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));

        // Further assertions or operations with contact1
        assertNotNull(contact1.getCreated_at());
        assertNotNull(contact1.getUpdated_at());
        mockContacts.add(contact1);

        // Tell the mocked service to return this list when called
        when(contactService.getAllContacts()).thenReturn(mockContacts);

        // Test the API endpoint and make sure it returns the right data
        given()
                .when().get("/contacts")
                .then()
                .statusCode(200)  // Make sure the status code is 200 OK
                .body("$.size()", is(1))  // Expecting 1 contact in the list
                .body("[0].name", is("Jaime Enriquez"));  // The name should be "Jaime Enriquez"
    }

    /**
     * Test to ensure that the /contacts endpoint returns an empty list when no contacts are available.
     * It mocks the ContactService to return an empty list and verifies that the response is correct.
     */
    @Test
    public void testGetAllContactsEndpoint_EmptyList() {
        // Mock the service to return an empty list
        when(contactService.getAllContacts()).thenReturn(new ArrayList<>());

        // Call the API and make sure it returns an empty list with a 200 status
        given()
                .when().get("/contacts")
                .then()
                .statusCode(200)  // Still expect a 200 OK
                .body("$.size()", is(0));  // But with 0 contacts
    }

    /**
     * Test to ensure that the /contacts endpoint handles API errors properly.
     * It mocks the ContactService to throw a RuntimeException and verifies that a 500 Internal Server Error is returned.
     */
    @Test
    public void testGetAllContactsEndpoint_ApiError() {
        // Simulate an error when calling the service
        when(contactService.getAllContacts()).thenThrow(new RuntimeException("API error"));

        // Register a parser so that even if the content type is weird, we treat it as JSON
        registerParser("application/octet-stream", JSON);

        // Test that the API returns a 500 error and a proper error message
        given()
                .when().get("/contacts")
                .then()
                .statusCode(500)  // Expect a 500 Internal Server Error
                .body("error", is("Internal Server Error"));  // The error message should be correct
    }
}
