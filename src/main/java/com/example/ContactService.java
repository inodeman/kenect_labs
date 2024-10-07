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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for fetching contacts from the Kenect API.
 * It handles pagination and error handling while making the API requests.
 */
@ApplicationScoped
public class ContactService {

    private static final Logger LOG = Logger.getLogger(ContactService.class);

    @ConfigProperty(name = "kenect.api.url")
    String apiUrl;

    @ConfigProperty(name = "kenect.api.token")
    String apiToken;

    /**
     * Fetches all contacts from the API. This method handles pagination,
     * combines contacts from multiple pages, and logs progress along the way.
     *
     * @return a list of {@link Contact} objects fetched from the API.
     * @throws RuntimeException if the API returns an error or if contacts cannot be fetched.
     */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        int page = 1;
        boolean morePages = true;
        Client client = ClientBuilder.newClient();

        // Logging the API URL and Token for troubleshooting (be cautious with the token in logs)
        LOG.info("Starting API request to: " + apiUrl);
        LOG.debug("API Token: " + apiToken);  // Avoid logging tokens in production

        while (morePages) {
            WebTarget target = client.target(apiUrl).queryParam("page", page);
            Response response = target.request()
                    .header("Authorization", "Bearer " + apiToken)
                    .get();

            // Log and throw an error if the API request fails
            if (response.getStatus() != 200) {
                String responseBody = response.readEntity(String.class);  // Retrieve the response body for debugging
                String errorMessage = "API error: status " + response.getStatus() + ". Response: " + responseBody;
                LOG.error(errorMessage);  // Log the error
                throw new RuntimeException("Couldn't get contacts: " + errorMessage);
            }

            // Parse and add the contacts from the current page to the list
            List<Contact> currentContacts = response.readEntity(ContactListResponse.class).getContacts();
            LOG.info("Contacts are: " + currentContacts);

            contacts.addAll(currentContacts);

            LOG.info("Got " + currentContacts.size() + " contacts from page " + page);

            // Determine if there are more pages to fetch
            String totalPagesHeader = response.getHeaderString("Total-Pages");
            int totalPages = totalPagesHeader != null ? Integer.parseInt(totalPagesHeader) : 1;
            morePages = page < totalPages;

            // Log pagination progress
            LOG.info("Processed page " + page + " of " + totalPages);
            page++;
        }

        // Log the total number of contacts retrieved
        LOG.info("Fetched a total of " + contacts.size() + " contacts.");
        return contacts;
    }
}
