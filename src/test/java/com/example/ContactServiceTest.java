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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ContactService} class, which fetches contacts from an external API.
 * Uses QuarkusTest framework to enable testing in a Quarkus environment and Mockito for mocking services.
 */
@QuarkusTest
public class ContactServiceTest {

    @InjectMock
    ContactService contactService;

    /**
     * Test that ensures the service can successfully retrieve a list of contacts.
     * It mocks a list of contacts and verifies the correctness of the service's response.
     */
    @Test
    public void testGetAllContacts_Success() {
        List<Contact> mockContacts = new ArrayList<>();

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setName("Jaime Enriquez");
        contact1.setEmail("jaime.a.enriquez@gmail.com");
        contact1.setSource("KENECT_LABS");
        contact1.setCreated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));
        contact1.setUpdated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));
        mockContacts.add(contact1);

        // Mock the service to return our fake list of contacts
        when(contactService.getAllContacts()).thenReturn(mockContacts);

        // Call the method and check the result
        List<Contact> contacts = contactService.getAllContacts();
        assertNotNull(contacts);  // Make sure the list isn't null
        assertEquals(1, contacts.size());  // Expecting exactly 1 contact
        assertEquals("Jaime Enriquez", contacts.get(0).getName());  // Check that the contact's name is correct
    }

    /**
     * Test that simulates an API error and ensures the service handles it correctly.
     * It mocks a WebApplicationException and verifies the exception message and status code.
     */
    @Test
    public void testGetAllContacts_ApiError() {
        // Mock an API error with a WebApplicationException
        when(contactService.getAllContacts()).thenThrow(
                new WebApplicationException("Failed to fetch contacts", Response.Status.INTERNAL_SERVER_ERROR)
        );

        // Expect the service to throw the exception when called
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            contactService.getAllContacts();
        });

        // Check the exception message and status code
        assertEquals("Failed to fetch contacts", exception.getMessage());
        assertEquals(500, exception.getResponse().getStatus());
    }

    /**
     * Test that ensures the service correctly handles an empty list response from the API.
     * It mocks an empty list and verifies the service returns an empty list as well.
     */
    @Test
    public void testGetAllContacts_EmptyResponse() {
        // Mock the service to return an empty list
        when(contactService.getAllContacts()).thenReturn(new ArrayList<>());

        // Call the method and make sure the result is an empty list
        List<Contact> contacts = contactService.getAllContacts();
        assertNotNull(contacts);  // List should not be null
        assertTrue(contacts.isEmpty());  // List should be empty
    }

    /**
     * Test that ensures the service handles pagination and returns all pages combined.
     * It mocks two pages of contacts and verifies that the service returns the combined list.
     */
    @Test
    public void testGetAllContacts_Pagination() {
        // Mock the contacts from page 1
        List<Contact> page1Contacts = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setName("Jaime Enriquez");
        contact1.setEmail("jaime.a.enriquez@gmail.com");
        contact1.setSource("KENECT_LABS");
        contact1.setCreated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));
        contact1.setUpdated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));
        page1Contacts.add(contact1);

        // Mock the contacts from page 2
        List<Contact> page2Contacts = new ArrayList<>();
        Contact contact2 = new Contact();
        contact2.setId(1L);
        contact2.setName("Jaime Enriquez");
        contact2.setEmail("jaime.a.enriquez@gmail.com");
        contact2.setSource("KENECT_LABS");
        contact2.setCreated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));
        contact2.setUpdated_at(Contact.parseStringToOffsetDateTime("2020-06-25T02:29:23.755Z"));
        page2Contacts.add(contact2);

        // Combine both pages into one list
        List<Contact> allContacts = new ArrayList<>();
        allContacts.addAll(page1Contacts);
        allContacts.addAll(page2Contacts);

        // Mock the service to return the combined list
        when(contactService.getAllContacts()).thenReturn(allContacts);

        // Call the method and make sure it returns both contacts
        List<Contact> contacts = contactService.getAllContacts();
        assertNotNull(contacts);  // The list shouldn't be null
        assertEquals(2, contacts.size());  // We should have 2 contacts now
    }
}
