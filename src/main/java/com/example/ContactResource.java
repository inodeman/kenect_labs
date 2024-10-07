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

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import java.util.List;

/**
 * REST resource that handles requests to the /contacts endpoint.
 * Provides functionality to retrieve a list of contacts from the service.
 */
@Path("/contacts")
public class ContactResource {

    @Inject
    ContactService contactService;

    private static final Logger LOG = Logger.getLogger(ContactResource.class);

    /**
     * Retrieves all contacts from the service.
     *
     * @return a {@link Response} containing the list of {@link Contact} objects,
     * or an error message if an exception occurs.
     */
    @GET
    public Response getAllContacts() {
        try {
            // Get all the contacts using the service
            List<Contact> contacts = contactService.getAllContacts();
            return Response.ok(contacts).build();  // Return the list of contacts as a response
        } catch (RuntimeException e) {
            LOG.error("Something went wrong with the API call", e);
            // If an error occurs, return a 500 Internal Server Error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal Server Error\"}")
                    .build();
        }
    }
}
